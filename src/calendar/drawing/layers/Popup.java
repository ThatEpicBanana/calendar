package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.state.State;

public class Popup implements Drawable {
    private final int width;
    private final int height = 18;

    protected State state;

    public Popup(int width, State state) {
        this.width = width;

        this.state = state;
    }

    public Theme colors() { return state.colors(); }

    public Canvas draw() {
        return Canvas.rectangle(width, height, true, colors().text(), colors().background());
    }

    public int width() { return this.width; }
    public int height() { return this.height; }

    protected int line(int y) { return y + 2; }
    protected int maxLines() { return height() - 4; }

    protected void drawText(Canvas canvas, String text, int textline, Color foreground, Color background) {
        int x = (width() - text.length()) / 2;
        int y = line(textline);

        canvas.drawText(text, x, y, foreground, background);
    }

    protected void drawTextLeft(Canvas canvas, String text, int textline, int margin) {
        int x = margin;
        int y = line(textline);
        canvas.drawText(text, x, y);
    }

    protected void drawTextRight(Canvas canvas, String text, int textline, int margin) {
        int x = width() - text.length() - margin;
        int y = line(textline);
        canvas.drawText(text, x, y);
    }
}
