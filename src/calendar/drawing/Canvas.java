package calendar.drawing;

import calendar.drawing.color.Ansi;
import calendar.drawing.color.Color;
import calendar.util.Vec2;

// This is the backbone of the drawing, 
// representing a canvas of text and its colors.
// Each component draws on its own canvas, which 
// get overlayed on to each other to draw the whole object.
// This class also provides methods for drawing objects on it,
// such as boxes, text, or colors
public class Canvas {
    protected final char[][] text;

    private Vec2 dims;

    public Color[][] foreground;
    public Color[][] background;
    
    // constructors //

    public Canvas(int width, int height, Color foreground, Color background) {
        this(width, height);

        this.fill(foreground, background);
    }

    public Canvas(int width, int height) {
        this.dims = new Vec2(width, height);

        text = new char[width][height];
        foreground = new Color[width][height];
        background = new Color[width][height];
        this.fill(' ');
    }

    // offset //

    protected Canvas(Canvas other, int width, int height) {
        this.dims = new Vec2(width, height);

        this.text = other.text;
        this.foreground = other.foreground;
        this.background = other.background;
    }

    public Canvas offset(int x, int y, int width, int height) {
        return new OffsetCanvas(this, x, y, width, height);
    }

    public Canvas offsetMargin(int margin) {
        return this.offset(margin, margin, width() - margin * 2, height() - margin * 2);
    }

    public Canvas offsetCentered(int y, int width, int height) {
        int x = (this.width() - width) / 2;
        return new OffsetCanvas(this, x, y, width, height);
    }

    public Canvas offsetCenteredMargin(int y, int margin, int height) {
        int width = this.width() - margin * 2;

        int x = (this.width() - width) / 2;

        return new OffsetCanvas(this, x, y, width, height);
    }

    // drawer //

    public interface Drawer {
        void draw(Canvas canvas);
    }

    public Canvas draw(Drawer drawer) {
        drawer.draw(this);
        return this;
    }

    // getters //

    public int width() { return dims.x; }
    public int height() { return dims.y; }

    public Vec2 dims() { return dims; }

    // basic //
    
    public Canvas set(int x, int y, char val, Color foreground, Color background) {
        this.set(x, y, val);
        this.highlight(x, y, foreground, background);
        return this;
    }

    // text //

    public Canvas set(int x, int y, char val) {
        this.text[x][y] = val;
        return this;
    }

    public Canvas fill(char val) {
        for(int x = 0; x < width(); x++)
            for(int y = 0; y < height(); y++)
                this.text[x][y] = ' ';
        return this;
    }

    // colors //

    public Canvas highlight(int x, int y, Color foreground, Color background) {
        if(foreground != null)
            this.foreground[x][y] = foreground;
        if(background != null)
            this.background[x][y] = background;
        return this;
    }

    public Canvas fill(Color foreground, Color background) {
        for(int x = 0; x < width(); x++)
            for(int y = 0; y < height(); y++)
                this.highlight(x, y, foreground, background);
        return this;
    }

    public Canvas highlightBox(int startx, int starty, int width, int height, Color foreground, Color background) {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.highlight(x + startx, y + starty, foreground, background);
        return this;
    }

    // printing //

    public void printMonochrome() {
        for(int y = 0; y < height(); y++) {
            for(int x = 0; x < width(); x++)
                System.out.print(this.text[x][y]);
            System.out.println();
        }
    }

    public void print() {
        StringBuilder builder = new StringBuilder();

        for(int y = 0; y < height(); y++) {
            for(int x = 0; x < width(); x++)
                print(builder, x, y);
            builder.append('\n');
        }

        builder.deleteCharAt(builder.length() - 1); // remove the last newline

        builder.append(Ansi.RESET + Ansi.HOME);

        System.out.print(builder.toString());
    }
    
    private void print(StringBuilder builder, int x, int y) {
        Ansi.color(builder, this.text[x][y], this.foreground[x][y], this.background[x][y]);
    }


    // text //

    public Canvas text(String string, int x, int y, Color foreground, Color background) {
        return this
            .text(string, x, y)
            .highlightBox(x, y, string.length(), 1, foreground, background);
    }

    public Canvas text(String string, int x, int y) {
        int length = string.length();
        int minchar = x < 0 ? -x : 0;
        int maxchar = Math.min(width() - x, length);

        for(int i = minchar; i < maxchar; i++)
            set(x + i, y, string.charAt(i));

        // return self for chaining
        return this;
    }

    public Canvas text(String string, Just justification) {
        Vec2 pos = justification.get(dims(), new Vec2(string.length(), 1));
        return text(string, pos.x, pos.y);
    }

    public Canvas text(String string, Just justification, Color foreground, Color background) {
        Vec2 pos = justification.get(dims(), new Vec2(string.length(), 1));
        return text(string, pos.x, pos.y, foreground, background);
    }


    // overlaying //

    private void copyColors(int x, int y, Canvas other, int otherx, int othery) {
        Color otherForeground = other.foreground[otherx][othery];
        if(otherForeground != null) this.foreground[x][y] = otherForeground;

        Color otherBackground = other.background[otherx][othery];
        if(otherBackground != null) this.background[x][y] = otherBackground;
    }

    interface Overlayer {
        void overlay(int x, int y, int otherx, int othery);
    }

    // overlays another canvas with the given function
    protected Canvas overlayWith(int offx, int offy, Canvas other, Overlayer overlayer) {
        // get bounds of the inserted canvas
        int xmin = Math.max(0, offx);
        int ymin = Math.max(0, offy);
        int xmax = Math.min(width(), offx + other.width()) - 1;
        int ymax = Math.min(height(), offy + other.height()) - 1;

        // overwrite the chars on this canvas with the ones on the inserted canvas
        for(int x = xmin; x <= xmax; x++) {
            for(int y = ymin; y <= ymax; y++) {
                int otherx = x - offx, othery = y - offy;

                overlayer.overlay(x, y, otherx, othery);
                copyColors(x, y, other, otherx, othery);
            }
        }

        // return self for chaining
        return this;

    }

    // overlays another canvas on top of this one, offset by offx and offy
    public Canvas overlay(int offx, int offy, Canvas other) {
        return this.overlayWith(offx, offy, other, 
            (x, y, otherx, othery) -> {
                this.text[x][y] = other.text[otherx][othery];
            }
        );
    }

    // merges box chars between the two canvases, and otherwise uses the other canvas's chars
    public Canvas merge(int offx, int offy, Canvas other) {
        return this.overlayWith(offx, offy, other, 
            (x, y, otherx, othery) -> {
                this.text[x][y] = BoxChars.combine(other.text[otherx][othery], this.text[x][y]);
            }
        );
    }

    // line drawing //

    public Canvas verticalLine(int x, int start, int end, boolean heavy)
        { return this.verticalLine(x, start, end, heavy ? BoxChars.heavy : BoxChars.light); }

    // writes a vertical line at x from start to end, inclusive
    protected Canvas verticalLine(int x, int start, int end, char[][][][] chars) {
        char vertical = chars[1][1][0][0]; // │

        for(int y = start; y <= end; y++)
            text[x][y] = vertical;

        // return self for chaining
        return this;
    }

    public Canvas horizontalLine(int y, int start, int end, boolean heavy)
        { return this.horizontalLine(y, start, end, heavy ? BoxChars.heavy : BoxChars.light); }

    // writes a horizontal line at y from start to end, inclusive
    protected Canvas horizontalLine(int y, int start, int end, char[][][][] chars) {
        char horizontal = chars[0][0][1][1]; // ─
        
        for(int x = start; x <= end; x++)
            text[x][y] = horizontal;

        // return self for chaining
        return this;
    }


    // box drawing //

    // returns a Canvas with the specified rectangle to its edges
    public static Canvas fromRectangle(int width, int height, boolean heavy, Color foreground, Color background) 
        { return new Canvas(width, height, foreground, background).rectangle(width, height, heavy); }
    public static Canvas fromRectangle(int width, int height, boolean heavy) 
        { return fromRectangle(width, height, heavy, null, null); }

    public Canvas rectangle(int width, int height, boolean heavy) { return rectangle(0, 0, width, height, heavy); }

    // creates a rectangle filling a canvas of width and height
    public Canvas rectangle(int x, int y, int width, int height, boolean heavy) {
        char[][][][] chars = heavy ? BoxChars.heavy : BoxChars.light;

        final int left = x;
        final int right = x + width - 1;
        final int top = y;
        final int bottom = y + height - 1;

        set(left,  top,    chars[0][1][0][1]); // ┌
        set(right, top,    chars[0][1][1][0]); // ┐
        set(left,  bottom, chars[1][0][0][1]); // └
        set(right, bottom, chars[1][0][1][0]); // ┘
        
        verticalLine(left,  top + 1, bottom - 1, chars);
        verticalLine(right, top + 1, bottom - 1, chars);

        horizontalLine(top,    left + 1, right - 1, chars);
        horizontalLine(bottom, left + 1, right - 1, chars);

        return this;
    }


    // converts a cell length to what the full length should be
    public static int gridLength(int cell, int amount) 
        { return (cell + 1) * amount + 1; }

    // returns a new Canvas with the specified grid in the top left
    public static Canvas fromGrid(int cellWidth, int cellHeight, int columns, int rows, boolean heavy) {
        int width = gridLength(cellWidth, columns);
        int height = gridLength(cellHeight, rows);

        Canvas canvas = new Canvas(width, height);
        canvas.grid(cellWidth, cellHeight, columns, rows, heavy);
        return canvas;
    }

    public Canvas grid(int cellWidth, int cellHeight, int columns, int rows, boolean heavy) {
        return grid(0, 0, cellWidth, cellHeight, columns, rows, heavy);
    }

    // draws a grid of columns and rows with inner cells of cellWidth and cellheight
    public Canvas grid(int offx, int offy, int cellWidth, int cellHeight, int columns, int rows, boolean heavy) {
        char[][][][] chars = heavy ? BoxChars.heavy : BoxChars.light;

        // as of now, it just draws the grid based on the cell width and height
        int width = gridLength(cellWidth, columns);
        int height = gridLength(cellHeight, rows);

        // various dimensions
        final int left = offx;
        final int right = offx + width - 1;
        final int top = offy;
        final int bottom = offy + height - 1;

        final int xdiff = cellWidth + 1;
        final int ydiff = cellHeight + 1;

        // outer rectangle
        this.rectangle(width, height, heavy);

        // columns
        for(int x = xdiff; x < width - 1; x += xdiff) {
            text[x][top] = chars[0][1][1][1]; // ┬
            this.verticalLine(x, top + 1, bottom - 1, chars); // │
            text[x][bottom] = chars[1][0][1][1]; // ┴
        }
        
        // rows
        for(int y = ydiff; y < height - 1; y += ydiff) {
            text[left][y] = chars[1][1][0][1]; // ├
            this.horizontalLine(y, left + 1, right - 1, chars); // ─
            text[right][y] = chars[1][1][1][0]; // ┤
        }

        // intersections
        for(int x = xdiff; x < width - 1; x += xdiff)
            for(int y = ydiff; y < height - 1; y += ydiff)
                text[x][y] = chars[1][1][1][1]; // ┼
        
        return this;
    }
}
