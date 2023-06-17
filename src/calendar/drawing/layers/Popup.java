package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.Widgets;
import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.state.State;

// a generic popup
// generally a tall rectangle with rows of text
public class Popup implements Drawable {
    private final int width;
    private final int height = 18;

    protected State state;
    protected Widgets wid;

    public Popup(int width, State state) {
        this.width = width;

        this.state = state;
        this.wid = new Widgets(state);
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
}
