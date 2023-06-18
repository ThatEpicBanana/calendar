package calendar.drawing.canvas;

import calendar.drawing.color.Color;

// TODO: a similar class that handles a scrollable area
public class OffsetCanvas extends Canvas {
    protected int offx;
    protected int offy;

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

    // for ScrollableCanvas
    // it respects the original offset, 
    // but doesn't do anything extra
    protected OffsetCanvas(Canvas other) {
        super(other, other.width(), other.height());

        this.offx = 0;
        this.offy = 0;

        if(other instanceof OffsetCanvas) {
            OffsetCanvas others = (OffsetCanvas) other;
            this.offx += others.offx;
            this.offy += others.offy;
        }
    }

    public Canvas set(int x, int y, char val) {
        this.text[x + offx][y + offy] = val;
        return this;
    }

    public Canvas highlight(int x, int y, Color foreground, Color background) {
        if(foreground != null)
            this.foreground[x + offx][y + offy] = foreground;
        if(background != null)
            this.background[x + offx][y + offy] = background;
        return this;
    }

    // overrides //
    
    protected Canvas overlayWith(int x, int y, Canvas other, Overlayer overlayer) {
        return super.overlayWith(x + offx, y + offy, other, overlayer);
    }
}
