package calendar.state;

import java.time.LocalDate;

import calendar.drawing.color.Theme;
import calendar.input.layer.AddEventInputLayer;
import calendar.input.InputLayer;
import calendar.input.layer.SectionInputLayer;
import calendar.storage.Calendar;
import calendar.storage.EditingEvent;
import calendar.storage.Event;
import calendar.util.Vec2;

public class State {
    public Calendar calendar;
    public Screen screen;
    private LocalDate date;
    private Theme colors;
    private int popupHover = 0;
    private boolean editingHover;

    public boolean updating = true;

    public State(LocalDate date, Theme colors, Vec2 dimensions, Vec2 cell) {
        this.date = date;
        this.colors = colors;

        this.calendar = new Calendar(this);
        this.screen = new Screen(dimensions.x, dimensions.y, cell.x, cell.y, this);

        // TODO: readd this update
        // updateScreen();
    }

    public void updateScreen() {
        if(updating)
            screen.print();
    }

    public LocalDate date() { return date; }
    public Theme colors() { return colors; }
    public int popupHover() { return popupHover; }

    public void setDate(LocalDate date) {
        this.date = date;
        updateScreen();
    }

    public void setColors(Theme colors) {
        this.colors = colors;
        updateScreen();
    }

    // reset the popup line number
    // note: doesn't update the screen
    public void resetPopupHover() { this.popupHover = 0; }

    public void setPopupHover(int i) { this.popupHover = i; updateScreen(); }
    public void movePopupHover(int i, int min, int max) { 
        this.popupHover = Math.min(max, Math.max(this.popupHover + i, min));
        updateScreen();
    }

    // might want to consider using an int and incrementing or decrementing it
    // to handle multiple layers of editing
    // currently, though, that's impossible, so this is fine
    public boolean editingHover() { return this.editingHover; }
    public void startEditingHover() { this.editingHover = true; updateScreen(); }
    public void endEditingHover() { this.editingHover = false; updateScreen(); }

    public InputLayer showSectionPopup() {
        if(screen.addSectionPopup())
            return new SectionInputLayer(this);
        else
            return null;
    }

    public InputLayer showEventPopup() {
        EditingEvent event = calendar.createDefaultEvent();
        if (screen.addAddEventPopup(event))
            return new AddEventInputLayer(this, event);
        else 
            return null;
    }
}
