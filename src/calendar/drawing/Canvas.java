package calendar.drawing;

import calendar.drawing.color.Ansi;
import calendar.drawing.color.Color;

public class Canvas {
    public final char[][] text;

    private int width;
    private int height;

    public Color[][] foreground;
    public Color[][] background;
    
    // constructors //

    public Canvas(int width, int height, Color foreground, Color background) {
        this(width, height);

        this.fillColor(foreground, background);
    }

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;

        text = new char[width][height];
        foreground = new Color[width][height];
        background = new Color[width][height];
        this.fillWith(' ');
    }

    // getters //

    public int height() { return height; }
    public int width() { return width; }

    // fil //

    public void fillWith(char val) {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.text[x][y] = ' ';
    }

    // colors //

    public void fillColor(Color foreground, Color background) {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.highlight(x, y, foreground, background);
    }

    public void highlight(int x, int y, Color foreground, Color background) {
        if(foreground != null)
            this.foreground[x][y] = foreground;
        if(background != null)
            this.background[x][y] = background;
    }

    public Canvas highlightBox(int startx, int starty, int width, int height, Color foreground, Color background) {
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                this.highlight(x + startx, y + starty, foreground, background);
        return this;
    }

    // printing //

    public void print_monochrome() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++)
                System.out.print(this.text[x][y]);
            System.out.println();
        }
    }

    public void print() {
        StringBuilder builder = new StringBuilder(Ansi.CLEAR_SCREEN);

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++)
                print(builder, x, y);
            builder.append('\n');
        }

        builder.deleteCharAt(builder.length() - 1);

        builder.append(Ansi.RESET);

        System.out.print(builder.toString());
    }
    
    private void print(StringBuilder builder, int x, int y) {
        Ansi.color(builder, this.text[x][y], this.foreground[x][y], this.background[x][y]);
    }


    // text //

    public Canvas drawText(String string, int x, int y, Color foreground, Color background) {
        return this
            .drawText(string, x, y)
            .highlightBox(x, y, string.length(), 1, foreground, background);
    }

    public Canvas drawText(String string, int x, int y) {
        int length = string.length();
        int minchar = x < 0 ? -x : 0;
        int maxchar = Math.min(width - x, length);

        for(int i = minchar; i < maxchar; i++)
            text[x + i][y] = string.charAt(i);

        // return self for chaining
        return this;
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
    private Canvas overlayWith(int offx, int offy, Canvas other, Overlayer overlayer) {
        // get bounds of the inserted canvas
        int xmin = Math.max(0, offx);
        int ymin = Math.max(0, offy);
        int xmax = Math.min(width, offx + other.width) - 1;
        int ymax = Math.min(height, offy + other.height) - 1;

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

    public Canvas drawVertical(int x, int start, int end, boolean heavy)
        { return this.drawVertical(x, start, end, heavy ? BoxChars.heavy : BoxChars.light); }

    // writes a vertical line at x from start to end, inclusive
    private Canvas drawVertical(int x, int start, int end, char[][][][] chars) {
        char vertical = chars[1][1][0][0]; // │

        for(int y = start; y <= end; y++)
            text[x][y] = vertical;

        // return self for chaining
        return this;
    }

    public Canvas drawHorizontal(int y, int start, int end, boolean heavy)
        { return this.drawHorizontal(y, start, end, heavy ? BoxChars.heavy : BoxChars.light); }

    // writes a horizontal line at y from start to end, inclusive
    private Canvas drawHorizontal(int y, int start, int end, char[][][][] chars) {
        char horizontal = chars[0][0][1][1]; // ─
        
        for(int x = start; x <= end; x++)
            text[x][y] = horizontal;

        // return self for chaining
        return this;
    }


    // box drawing //

    // returns a Canvas with the specified rectangle to its edges
    public static Canvas rectangle(int width, int height, boolean heavy, Color foreground, Color background) 
        { return new Canvas(width, height, foreground, background).drawRectangle(width, height, heavy); }
    public static Canvas rectangle(int width, int height, boolean heavy) 
        { return rectangle(width, height, heavy, null, null); }

    // creates a rectangle filling a canvas of width and height
    public Canvas drawRectangle(int width, int height, boolean heavy) {
        char[][][][] chars = heavy ? BoxChars.heavy : BoxChars.light;

        final int left = 0;
        final int right = width - 1;
        final int top = 0;
        final int bottom = height - 1;

        text[left][top] = chars[0][1][0][1]; // ┌
        text[right][top] = chars[0][1][1][0]; // ┐
        text[left][bottom] = chars[1][0][0][1]; // └
        text[right][bottom] = chars[1][0][1][0]; // ┘
        
        drawVertical(left,  top + 1, bottom - 1, chars);
        drawVertical(right, top + 1, bottom - 1, chars);

        drawHorizontal(top,    left + 1, right - 1, chars);
        drawHorizontal(bottom, left + 1, right - 1, chars);

        return this;
    }


    // converts a cell width or height to what the full width or height should be
    public static int cellDimToFull(int cell, int amount) 
        { return (cell + 1) * amount + 1; }

    public static int cellToChar(int cell, int cellDim) 
        { return cell * (cellDim + 1) + 1; }

    // returns a new Canvas with the specified grid in the top left
    public static Canvas grid(int cellWidth, int cellHeight, int columns, int rows, boolean heavy) {
        int width = cellDimToFull(cellWidth, columns);
        int height = cellDimToFull(cellHeight, rows);

        Canvas canvas = new Canvas(width, height);
        canvas.drawGrid(cellWidth, cellHeight, columns, rows, heavy);
        return canvas;
    }

    // creates a grid of columns and rows with cells of cellWidth and cellheight
    public Canvas drawGrid(int cellWidth, int cellHeight, int columns, int rows, boolean heavy) {
        char[][][][] chars = heavy ? BoxChars.heavy : BoxChars.light;

        // as of now, it just draws the grid based on the cell width and height
        int width = cellDimToFull(cellWidth, columns);
        int height = cellDimToFull(cellHeight, rows);

        // various dimensions
        final int left = 0;
        final int right = width - 1;
        final int top = 0;
        final int bottom = height - 1;

        final int xdiff = cellWidth + 1;
        final int ydiff = cellHeight + 1;

        // outer rectangle
        this.drawRectangle(width, height, heavy);

        // columns
        for(int x = xdiff; x < width - 1; x += xdiff) {
            text[x][top] = chars[0][1][1][1]; // ┬
            this.drawVertical(x, top + 1, bottom - 1, chars); // │
            text[x][bottom] = chars[1][0][1][1]; // ┴
        }
        
        // rows
        for(int y = ydiff; y < height - 1; y += ydiff) {
            text[left][y] = chars[1][1][0][1]; // ├
            this.drawHorizontal(y, left + 1, right - 1, chars); // ─
            text[right][y] = chars[1][1][1][0]; // ┤
        }

        // intersections
        for(int x = xdiff; x < width - 1; x += xdiff)
            for(int y = ydiff; y < height - 1; y += ydiff)
                text[x][y] = chars[1][1][1][1]; // ┼
        
        return this;
    }
}
