package calendar.drawing.layers;

import calendar.drawing.Drawable;
import calendar.drawing.Shapes;

public class Popup implements Drawable {
    private final int width;
    private final int height;

    public Popup(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public char[][] draw() {
        return Shapes.rectangle(width, height, true);
    }

    public int width() { return this.width; }
    public int height() { return this.height; }
}
