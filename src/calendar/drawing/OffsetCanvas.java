package calendar.drawing;

import calendar.drawing.color.Color;

public class OffsetCanvas extends Canvas {
    private int offx;
    private int offy;

    protected OffsetCanvas(Canvas other, int offx, int offy, int width, int height) {
        super(other, width, height);

        this.offx = offx;
        this.offy = offy;

        if(other instanceof OffsetCanvas) {
            OffsetCanvas others = (OffsetCanvas) other;
            this.offx += others.offx;
            this.offy += others.offy;
        }
    }

    public void set(int x, int y, char val) {
        this.text[x + offx][y + offy] = val;
    }

    public void highlight(int x, int y, Color foreground, Color background) {
        if(foreground != null)
            this.foreground[x + offx][y + offy] = foreground;
        if(background != null)
            this.background[x + offx][y + offy] = background;
    }

    // overrides //
    
    // uses set()
    // public Canvas text(String string, int x, int y)

    protected Canvas overlayWith(int x, int y, Canvas other, Overlayer overlayer) {
        return super.overlayWith(x + offx, y + offy, other, overlayer);
    }

    protected Canvas verticalLine(int x, int start, int end, char[][][][] chars) { 
        return super.verticalLine(x + offx, start + offy, end + offy, chars);
    }

    protected Canvas horizontalLine(int y, int start, int end, char[][][][] chars) { 
        return super.horizontalLine(y + offy, start + offx, end + offx, chars);
    }

    // uses set() because it also uses verticalLine
    // public Canvas rectangle(int x, int y, int width, int height, boolean heavy)

    public Canvas grid(int offx, int offy, int cellWidth, int cellHeight, int columns, int rows, boolean heavy) {
        return super.grid(offx + this.offx, offy + this.offy, cellWidth, cellHeight, columns, rows, heavy);
    }        
}
