package calendar.state;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.color.Theme;
import calendar.drawing.layers.AddEventPopup;
import calendar.drawing.layers.HelpPopup;
import calendar.drawing.layers.Month;
import calendar.drawing.layers.Popup;
import calendar.drawing.layers.PreferencesPopup;
import calendar.drawing.layers.SectionPopup;
import calendar.storage.EditingEvent;

public class Screen implements Drawable {
    public Month month;
    public Popup popup;

    private int width;
    private int height;

    private int cellWidth;
    private int cellHeight;

    private State state;

    public Screen(int width, int height, int cellWidth, int cellHeight, State state) {
        this.month = new Month(state, cellWidth, cellHeight);
        this.popup = null;

        this.state = state;

        this.width = width;
        this.height = height;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public Theme colors() { return state.colors(); }

    public int width() { return this.width; }
    public int height() { return this.height; }

    private int popupWidth() 
        { return cellWidth * 3 - 4; }

    private boolean addPopup(Popup popup) {
        if(this.popup == null) {
            this.popup = popup;
            state.updateScreen();
            return true;
        } else 
            return false;
    }

    // adds a section popup
    // returns if it succeeds
    public boolean addSectionPopup() { 
        return addPopup(new SectionPopup(popupWidth(), state));
    }

    // adds an event popup
    // returns the event currently being edited
    // or null if the popup couldn't be created
    public boolean addAddEventPopup(EditingEvent event) { 
        return addPopup(new AddEventPopup(popupWidth(), event, state));
    }

    // adds an info popup
    // returns if it succeeds
    public boolean addHelpPopup(String[] text) {
        return addPopup(new HelpPopup(popupWidth(), state, text));
    }

    // adds the preferences popup
    // returns if it succeeds
    public boolean addPreferencesPopup() {
        return addPopup(new PreferencesPopup(popupWidth(), state));
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

    public Canvas draw() {
        Canvas canvas = new Canvas(width, height, null, colors().background());

        center(canvas, month.draw(), 0);
        if(popup != null)
            center(canvas, popup.draw(), 2);

        return canvas;
    }

    public void reinitializeMonth() {
        this.month = new Month(state, cellWidth, cellHeight);
        this.state.updateScreen();
    }

    private void center(Canvas self, Canvas other, int offset) {
        int x = (width() - other.width()) / 2;
        int y = (height() - other.height()) / 2 + offset;
        self.overlay(x, y, other);
    }
}
