package calendar.drawing;

import calendar.drawing.color.Theme;
import calendar.drawing.layers.Month;
import calendar.drawing.layers.Popup;
import calendar.state.State;

public class Screen implements Drawable {
    public Month month;
    public Popup popup;

    public int width;
    public int height;

    private State state;

    public Screen(int width, int height, int cellWidth, int cellHeight, State state) {
        this.month = new Month(state, cellWidth, cellHeight);
        this.popup = null;
        this.state = state;

        this.width = width;
        this.height = height;
    }

    private Theme colors() { return state.colors(); }

    public int width() { return this.width; }
    public int height() { return this.height; }

    public Canvas draw() {
        Canvas canvas = new Canvas(width, height, colors().text(), colors().background());

        // center month
        Canvas month = this.month.draw();
        int x = (width() - month.width()) / 2;
        int y = (height() - month.height()) / 2;
        canvas.overlay(x, y, month);

        System.out.println("printing");

        return canvas;
    }
}
