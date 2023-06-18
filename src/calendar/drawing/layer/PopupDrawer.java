package calendar.drawing.layer;

import calendar.drawing.canvas.Canvas;
import calendar.drawing.JustOffset;
import calendar.drawing.JustOffset.DrawableWithOffset;
import calendar.drawing.canvas.Widgets;
import calendar.drawing.color.Theme;
import calendar.state.State;
import calendar.util.Vec2;

// a generic popup
// generally a tall rectangle with rows of text
public class PopupDrawer implements DrawableWithOffset {
    private final int width;
    private final int height = 18;

    protected State state;
    protected Widgets wid;

    // is the popup standalone, or part of another popup's input layer
    private boolean standalone = true;

    public PopupDrawer(int width, State state) {
        this(width, state, new Widgets(state, null));
    }

    public PopupDrawer(int width, State state, boolean standalone) {
        this(width, state, new Widgets(state, null));
        this.standalone = standalone;
    }

    protected PopupDrawer(int width, State state, Widgets widgets) {
        this.width = width;
        this.state = state;
        this.wid = widgets;
    }

    public Theme colors() { return state.colors(); }

    public Canvas draw() {
        return Canvas.fromRectangle(width(), height(), true, colors().text(), colors().background());
    }

    public int width() { return this.width; }
    public int height() { return this.height; }

    public Vec2 dims() { return new Vec2(width(), height()); }

    public boolean isStandalone() { return this.standalone; }

    public JustOffset offset() {
        return JustOffset.centered();
    }
}
