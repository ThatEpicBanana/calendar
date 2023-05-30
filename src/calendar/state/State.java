package calendar.state;

import java.time.LocalDate;

import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.input.layer.AddEventInputLayer;
import calendar.input.layer.HelpLayer;
import calendar.input.layer.PreferencesLayer;
import calendar.input.InputLayer;
import calendar.input.layer.SectionInputLayer;
import calendar.storage.Calendar;
import calendar.storage.EditingEvent;
import calendar.util.Vec2;

// represents the entire state of the application
// it acts as a communicator between the 
// drawing (screen), storage (calendar), settings, and input
public class State {
    public Calendar calendar;
    public Screen screen;
    public Settings settings;

    private LocalDate date;

    private int popupHover = 0;
    private boolean editingHover;
    
    private String errorCode = "";

    public boolean updating = true;

    public State(LocalDate date, Vec2 dimensions, Vec2 cell) {
        this.date = date;
        this.settings = new Settings(this);

        this.calendar = new Calendar(this);
        this.screen = new Screen(dimensions.x, dimensions.y, cell.x, cell.y, this);
    }

    public void updateScreen() {
        if(updating)
            screen.print();
    }

    public LocalDate date() { return date; }
    public Theme colors() { return settings.colors(); }
    public int popupHover() { return popupHover; }

    public Color monthColor() {
        if(settings.colorfulMonths())
            return colors().monthToColor(date().getMonthValue());
        else
            return colors().textBackground();
    }

    public Color monthColorText() {
        if(settings.colorfulMonths())
            return colors().highlightText();
        else
            return colors().text();
    }

    public void setDate(LocalDate date) { this.date = date; updateScreen(); }

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
    // currently, though, that doesn't happen, so this is fine
    public boolean editingHover() { return this.editingHover; }
    public void startEditingHover() { this.editingHover = true; updateScreen(); }
    public void endEditingHover() { this.editingHover = false; updateScreen(); }


    public String errorCode() { return errorCode; }
    public void displayError(String errorCode) { this.errorCode = errorCode; updateScreen(); }
    public void resetError() { this.errorCode = ""; updateScreen(); }


    public void changeMonth(int by) {
        this.date = this.date.withDayOfMonth(1).plusMonths(by);
        screen.reinitializeMonth();
    }


    public InputLayer showSectionPopup() {
        if(screen.addSectionPopup())
            return new SectionInputLayer(this);
        else
            return null;
    }

    public InputLayer showEventPopup() {
        if(calendar.sections().isEmpty()) {
            displayError("Failed to create event: please create a section (s) first");
            return null;
        }

        EditingEvent event = calendar.createDefaultEvent();
        if (screen.addAddEventPopup(event))
            return new AddEventInputLayer(this, event);
        else 
            return null;
    }

    public InputLayer showHelpPopup(String[] text) {
        if(screen.addHelpPopup(text))
            return new HelpLayer();
        else
            return null;
    }

    public InputLayer showPreferencesPopup() {
        if(screen.addPreferencesPopup())
            return new PreferencesLayer(this);
        else
            return null;
    }
}
