package calendar.state;

import calendar.drawing.canvas.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import calendar.drawing.Drawable;
import calendar.drawing.Just;
import calendar.drawing.JustOffset;
import calendar.drawing.JustOffset.DrawableWithOffset;
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
    public ArrayList<PopupDrawer> popups;

    private Vec2 dims;

    private int cellWidth;
    private int cellHeight;

    private State state;

    public Screen(int width, int height, int cellWidth, int cellHeight, State state) {
        this.month = new MonthDrawer(state, cellWidth, cellHeight);
        this.popups = new ArrayList<>();

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

    public PopupDrawer peekPopup() { return popups.get(popups.size() - 1); }

    private boolean addPopup(PopupDrawer popup) {
        this.popups.add(popup);
        state.updateScreen();
        return true;
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
    public boolean addHelpPopup(String[] text, boolean standalone) {
        return addPopup(new HelpDrawer(popupWidth(), state, text, standalone));
    }

    // adds the preferences popup
    // returns if it succeeds
    public boolean addPreferencesPopup(SelectionsLayer selector) {
        return addPopup(new PreferencesDrawer(popupWidth(), state, selector));
    }


    // removes the current popup
    // returns if it succeeds
    public boolean removePopup() {
        if(popups.size() < 1) return false;

        // remove child popups
        while(!peekPopup().isStandalone())
            popups.remove(popups.size() - 1);

        // remove parent popup
        popups.remove(popups.size() - 1);

        state.updateScreen();
        return true;
    }

    // removes a dependant popup,
    // one whose input layer is shared with the parent
    // returns if it succeeds
    public boolean removeDependantPopup() {
        if(popups.size() < 1 || peekPopup().isStandalone()) return false;

        popups.remove(popups.size() - 1);

        state.updateScreen();
        return true;
    }

    // returns if the top level popup is a help popup
    public boolean showingHelp() {
        return popups.size() > 0 && this.peekPopup() instanceof HelpDrawer;
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

        canvas.overlay(month.draw(), Just.centered());

        @SuppressWarnings("unchecked") // java is too dumb to realize that PopupDrawer implements DrawableOffset
        List<DrawableWithOffset> drawablePoupus = (List) popups;
        canvas.draw(JustOffset.drawAll(drawablePoupus, Just.offsetFrom(Just.centered(), new Vec2(0, 2))));

        return canvas;
    }

    public void reinitializeMonth() {
        this.month = new MonthDrawer(state, cellWidth, cellHeight);
        this.state.updateScreen();
    }
}
