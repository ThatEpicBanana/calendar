package calendar.drawing.layer;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.Widgets;
import calendar.drawing.color.Theme;
import calendar.state.State;

// a generic popup
// generally a tall rectangle with rows of text
public class PopupDrawer implements Drawable {
    private final int width;
    private final int height = 18;

    protected State state;
    protected Widgets wid;

    public PopupDrawer(int width, State state) {
        this(width, state, new Widgets(state, null));
    }

    protected PopupDrawer(int width, State state, Widgets widgets) {
        this.width = width;
        this.state = state;
        this.wid = widgets;
    }

    public Theme colors() { return state.colors(); }

    public Canvas draw() {
        return Canvas.fromRectangle(width, height, true, colors().text(), colors().background());
    }

    public int width() { return this.width; }
    public int height() { return this.height; }
}
