package calendar.drawing;

import calendar.drawing.canvas.Canvas;

// An object that can be drawn onto a Canvas.
// All components and layers implement this,
// who then get drawn on top of each other
// to fabricate the whole screen
public interface Drawable {
    public Canvas draw();
    public int width();
    public int height();

    public default void print() {
        draw().print();
    }
}
