package calendar.state;

import java.time.LocalDate;

import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.input.layer.AddEventInputLayer;
import calendar.input.layer.HelpInputLayer;
import calendar.input.layer.PreferencesInputLayer;
import calendar.input.InputLayer;
import calendar.input.layer.SectionInputLayer;
import calendar.state.layer.AddEventLayer;
import calendar.state.layer.ScrollableLayer;
import calendar.state.layer.SelectionsLayer;
import calendar.storage.Calendar;
import calendar.storage.EditingEvent;
import calendar.util.Vec2;

// represents the entire state of the application
// it acts as a communicator between the 
// drawing (screen), storage (calendar), settings, and input
public class State {
    public Calendar calendar;
    public Screen screen;
    public Config config;

    private LocalDate date;

    private int popupHover = 0;
    private boolean editingHover;
    
    private String errorCode = "";

    public boolean updating = true;
    public boolean supressNextUpdate = false;

    private String calendarFile;
    private String configFile;

    public State(LocalDate date, Vec2 dimensions, Vec2 cell) {
        this.date = date;
        this.screen = new Screen(dimensions.x, dimensions.y, cell.x, cell.y, this);

        this.config = new Config(this);
        this.calendar = new Calendar(this);
    }

    public State(LocalDate date, Vec2 dimensions, Vec2 cell, String calendarFile, String configFile) {
        this.date = date;
        this.screen = new Screen(dimensions.x, dimensions.y, cell.x, cell.y, this);

        this.calendarFile = calendarFile;
        this.configFile = configFile;

        this.calendar = Calendar.deserialize(calendarFile, this);
        this.config = Config.deserialize(configFile, this);
    }

    public void updateScreen() {
        if(updating)
            screen.print();
    }

    public LocalDate date() { return date; }
    public Theme colors() { return config.colors(); }

    public Color monthColor() {
        if(config.colorfulMonths())
            return colors().monthToColor(date().getMonthValue());
        else
            return colors().textBackground();
    }

    public Color monthColorText() {
        if(config.colorfulMonths())
            return colors().highlightText();
        else
            return colors().text();
    }

    public void setDate(LocalDate date) { this.date = date; updateScreen(); }


    public String errorCode() { return errorCode; }
    public void displayError(String errorCode) { this.errorCode = errorCode; updateScreen(); }
    protected void displayErrorWithoutUpdate(String errorCode) { this.errorCode = errorCode; }
    public void resetError() { this.errorCode = ""; updateScreen(); }


    public void changeMonth(int by) {
        this.date = this.date.withDayOfMonth(1).plusMonths(by);
        screen.reinitializeMonth();
    }


    public InputLayer showSectionPopup() {
        ScrollableLayer selector = new ScrollableLayer(this);
        if(screen.addSectionPopup(selector))
            return new SectionInputLayer(this, selector);
        else
            return null;
    }

    public InputLayer showEventPopup() {
        if(calendar.sections().isEmpty()) {
            displayError("Failed to create event: please create a section (s) first");
            return null;
        }

        AddEventLayer layer = new AddEventLayer(this);
        if (screen.addAddEventPopup(layer))
            return new AddEventInputLayer(this, layer);
        else 
            return null;
    }

    public InputLayer showStandaloneHelpPopup(String[] text) {
        if(screen.addHelpPopup(text, true))
            return new HelpInputLayer();
        else
            return null;
    }

    public void showHelpPopup(String[] text) {
        screen.addHelpPopup(text, false);
    }

    public InputLayer showPreferencesPopup() {
        SelectionsLayer selector = new SelectionsLayer(this);
        if(screen.addPreferencesPopup(selector))
            return new PreferencesInputLayer(this, selector);
        else
            return null;
    }

    public void updateFiles() {
        this.config.serialize(configFile);
        this.calendar.serialize(calendarFile);
    }
}
