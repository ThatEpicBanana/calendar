package calendar.drawing.components;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.Justification;

public class Box implements Drawable {
    private int width;
    private int height;
    public String title;
    private Justification justification;
    private boolean heavy;

    public Box(int width, int height, String title, boolean heavy, Justification justification) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.justification = justification;
        this.heavy = heavy;
    }

    public Canvas draw() {
        Canvas canvas = Canvas.rectangle(width, height, heavy);

        this.justification.write(canvas, title, 1, 1, width - 1, height - 1, 0);

        return canvas;
    }

    public int width() { return width; }
    public int height() { return height; }
}
