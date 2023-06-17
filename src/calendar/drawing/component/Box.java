package calendar.drawing.component;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.util.Vec2;

// a rectangle with some text in it
public class Box implements Drawable {
    private Vec2 dims;
    private boolean heavy;

    private Drawer drawer;

    public interface Drawer {
        public void draw(Canvas canvas, Vec2 dims);
    }

    public Box(Vec2 dims, boolean heavy, Drawer drawer) {
        this.dims = dims;
        this.heavy = heavy;
        this.drawer = drawer;
    }

    public Box(int width, int height, boolean heavy, Drawer drawer) {
        this(new Vec2(width, height), heavy, drawer);
    }

    public Canvas draw() {
        Canvas canvas = Canvas.fromRectangle(width(), height(), heavy);

        drawer.draw(canvas.offsetMargin(1), dims);

        return canvas;
    }

    public int width() { return dims.x; }
    public int height() { return dims.y; }
}
