package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;

public class Popup implements Drawable {
    private final int width;
    private final int height;

    public Popup(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Canvas draw() {
        return Canvas.rectangle(width, height, true);
    }

    public int width() { return this.width; }
    public int height() { return this.height; }
}
