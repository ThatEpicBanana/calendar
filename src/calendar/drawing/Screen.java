package calendar.drawing;

import calendar.drawing.layers.Month;
import calendar.drawing.layers.Popup;

public class Screen implements Drawable {
    public Month month;
    public Popup popup;

    public int width;
    public int height;

    public Screen(int width, int height, Month month, Popup popup) {
        this.month = month;
        this.popup = popup;
    }

    public int width() { return this.width; }
    public int height() { return this.height; }

    public Canvas draw() {
        return new Canvas(width, height);
    }
}
