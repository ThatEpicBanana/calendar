package calendar.drawing.canvas;

import calendar.drawing.color.Color;
import calendar.state.layer.ScrollableLayer;

public class ScrollableCanvas extends OffsetCanvas {
    protected ScrollableLayer layer;
    private int scrollOffset = 0;
    private int windowHeight;

    protected ScrollableCanvas(Canvas other, ScrollableLayer layer) {
        super(other);
        this.layer = layer;
        this.windowHeight = other.height();
        layer.setWindowHeight(windowHeight);
    }

    // an offset on a ScrollableCanvas
    // has to also be scrollable so the offset scrolls as well
    protected ScrollableCanvas(ScrollableCanvas other, int x, int y, int width, int height, ScrollableLayer layer) {
        super(other, x, y, width, height);
        this.layer = layer;
        this.scrollOffset = y;
        this.windowHeight = other.windowHeight;
        layer.setWindowHeight(windowHeight);
    }

    // drawers use the height to figure out where things are,
    // but the other methods restrain where it can actually draw
    public int height() { return Math.max(layer.fullHeight(), layer.scroll() + windowHeight); }

    private boolean inBounds(int y) {
        y += scrollOffset;
        return y >= 0 && y < windowHeight;
    }

    public Canvas set(int x, int oldy, char val) {
        int y = oldy - layer.scroll();

        if(inBounds(y))
            this.text[x + offx][y + offy] = val;

        return this;
    }

    public Canvas highlight(int x, int oldy, Color foreground, Color background) {
        int y = oldy - layer.scroll();

        if(inBounds(y)) {
            if(foreground != null)
                this.foreground[x + offx][y + offy] = foreground;
            if(background != null)
                this.background[x + offx][y + offy] = background;
        }

        return this;
    }

    // mainly used with widgets
    public Canvas offset(int x, int y, int width, int height) {
        return new ScrollableCanvas(this, x, y, width, height, layer);
    }

    // stops scrolling the view
    // usually to draw a scrollbar on top
    public Canvas unscroll() {
        return new OffsetCanvas(this);
    }
}
