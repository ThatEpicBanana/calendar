package calendar.state;

import java.time.LocalDate;

import calendar.drawing.color.Theme;
import calendar.storage.Calendar;

// handles the state of the app
// what screen is showing, actually storing a Calendar object, etc
// plays nicely with Input

public class State {
    public Calendar calendar;
    private LocalDate date;
    private Theme colors;

    public State(LocalDate date, Theme colors) {
        this.calendar = new Calendar(this);
        this.date = date;
        this.colors = colors;
    }

    public void updateScreen() {
        // TODO: implement later
    }

    public LocalDate date() { return date; }
    public void setDate(LocalDate date) { this.date = date; updateScreen(); }

    public Theme colors() { return colors; }
    public void setColors(Theme colors) { this.colors = colors; updateScreen(); }
}
