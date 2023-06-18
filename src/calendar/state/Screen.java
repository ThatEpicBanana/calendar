package calendar.state;

import calendar.drawing.canvas.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.color.Theme;
import calendar.drawing.layer.AddEventDrawer;
import calendar.drawing.layer.HelpDrawer;
import calendar.drawing.layer.MonthDrawer;
import calendar.drawing.layer.PopupDrawer;
import calendar.drawing.layer.PreferencesDrawer;
import calendar.drawing.layer.SectionDrawer;
import calendar.state.layer.AddEventLayer;
import calendar.state.layer.ScrollableLayer;
import calendar.state.layer.SelectionsLayer;
import calendar.util.Vec2;

// represents the entire drawable screen - month and popup
// when drawing, it centers them both onto the terminal screen
// it also handles the creation and destruction of popups
public class Screen implements Drawable {
    public MonthDrawer month;
    public PopupDrawer popup;

    private Vec2 dims;

    private int cellWidth;
    private int cellHeight;

    private State state;

    public Screen(int width, int height, int cellWidth, int cellHeight, State state) {
        this.month = new MonthDrawer(state, cellWidth, cellHeight);
        this.popup = null;

        this.state = state;

        this.dims = new Vec2(width, height);
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public Theme colors() { return state.colors(); }

    public int width() { return this.dims.x; }
    public int height() { return this.dims.y; }

    private int popupWidth() 
        { return cellWidth * 3 - 4; }

    private boolean addPopup(PopupDrawer popup) {
        if(this.popup == null) {
            this.popup = popup;
            state.updateScreen();
            return true;
        } else 
            return false;
    }

    // adds a section popup
    // returns if it succeeds
    public boolean addSectionPopup(ScrollableLayer selector) { 
        return addPopup(new SectionDrawer(popupWidth(), state, selector));
    }

    // adds an event popup
    // returns the event currently being edited
    // or null if the popup couldn't be created
    public boolean addAddEventPopup(AddEventLayer layer) { 
        return addPopup(new AddEventDrawer(popupWidth(), state, layer));
    }

    // adds an info popup
    // returns if it succeeds
    public boolean addHelpPopup(String[] text) {
        return addPopup(new HelpDrawer(popupWidth(), state, text));
    }

    // adds the preferences popup
    // returns if it succeeds
    public boolean addPreferencesPopup(SelectionsLayer selector) {
        return addPopup(new PreferencesDrawer(popupWidth(), state, selector));
    }


    // removes the current popup
    // returns if it succeeds
    public boolean removePopup() {
        if(this.popup != null) {
            this.popup = null;
            return true;
        } else 
            return false;
    }

    // updates the dimensions of the screen,
    // updating the screen if necessary
    public void updateDims(Vec2 dims) {
        if(!this.dims.equals(dims)) {
            this.dims = dims;
            state.updateScreen();
        }
    }

    public Canvas draw() {
        Canvas canvas = new Canvas(dims.x, dims.y, null, colors().background());

        center(canvas, month.draw(), 0);
        if(popup != null)
            center(canvas, popup.draw(), 2);

        return canvas;
    }

    public void reinitializeMonth() {
        this.month = new MonthDrawer(state, cellWidth, cellHeight);
        this.state.updateScreen();
    }

    private void center(Canvas self, Canvas other, int offset) {
        int x = (width() - other.width()) / 2;
        int y = (height() - other.height()) / 2 + offset;
        self.overlay(x, y, other);
    }
}
