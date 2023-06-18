package calendar.drawing;

import calendar.drawing.canvas.Canvas;
import calendar.util.Vec2;

// A layer that can create and draw itself onto a canvas.
// All layers implement this, who then get drawn on top of each other to fabricate the whole screen.
// To see the more general case, see [Canvas.Drawer], it represents any object that can be drawn onto a canvas
public interface Drawable {
    public Canvas draw();
    public int width();
    public int height();

    public default void print() {
        draw().print();
    }

    public default Vec2 dims() { return new Vec2(width(), height()); }
}
