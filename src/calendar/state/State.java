package calendar.state;

import java.time.LocalDate;

import calendar.state.Screen;
import calendar.drawing.color.Theme;
import calendar.storage.Calendar;
import calendar.util.Vec2;

public class State {
    public Calendar calendar;
    public Screen screen;
    private LocalDate date;
    private Theme colors;
    public int popupSelection;

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

    public LocalDate date() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        updateScreen();
    }

    public Theme colors() {
        return colors;
    }

    public void setColors(Theme colors) {
        this.colors = colors;
        updateScreen();
    }

    // public InputState createPopupAndInputState(Event event) {
    //     Popup popup = new Popup(event);
    //     InputState inputState = new InputState(event);

    //     popup.setInputState(inputState);
    //     inputState.setPopup(popup);

    //     return inputState;
    // }
}
