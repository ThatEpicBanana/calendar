package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.state.State;

// a generic popup
// generally a tall rectangle with rows of text
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
        return Canvas.fromRectangle(width, height, true, colors().text(), colors().background());
    }

    public int width() { return this.width; }
    public int height() { return this.height; }

    protected int line(int y) { return y + 2; }
    protected int maxLines() { return height() - 4; }

    protected int hover() { return state.popupHover(); }
    protected boolean selected(int y) { return hover() == y; }

    public String sanitize(String text, int maxWidth, int line) {
        int len = text.length();
        if(selected(line) && state.editingHover())
            // from the end
            return text.substring(len - Math.min(len, maxWidth));
        else
            // from the beginning
            return text.substring(0, Math.min(len, maxWidth));
    }

    protected void drawText(Canvas canvas, String text, int textline) 
        { drawText(canvas, text, textline, null, null); }
    protected void drawText(Canvas canvas, String text, int textline, Color foreground, Color background) {
        int x = (width() - text.length()) / 2;
        int y = line(textline);

        canvas.text(text, x, y, foreground, background);
    }

    protected void drawTextLeft(Canvas canvas, String text, int textline, int margin)
        { drawTextLeft(canvas, text, textline, margin, null, null); }
    protected void drawTextLeft(Canvas canvas, String text, int textline, int margin, Color foreground, Color background) {
        int x = margin;
        int y = line(textline);
        canvas.text(text, x, y, foreground, background);
    }

    protected void drawTextRight(Canvas canvas, String text, int textline, int margin)
        { drawTextRight(canvas, text, textline, margin, null, null); }
    protected void drawTextRight(Canvas canvas, String text, int textline, int margin, Color foreground, Color background) {
        int x = width() - text.length() - margin;
        int y = line(textline);
        canvas.text(text, x, y, foreground, background);
    }
}
