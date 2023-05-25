package calendar.state;

import java.time.LocalDate;

import calendar.storage.Calendar;

// handles the state of the app
// what screen is showing, actually storing a Calendar object, etc
// plays nicely with Input

public class State {
    public Calendar calendar;
    private LocalDate date;

    public State(LocalDate date) {
        this.calendar = new Calendar(this);
        this.date = date;
    }

    public void updateScreen() {
        // TODO: implement later
    }

    public LocalDate date() { return date; }
    public void setDate(LocalDate date) { this.date = date; updateScreen(); }
}
