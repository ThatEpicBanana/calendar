package calendar.state;

import java.time.LocalDate;

import calendar.state.Screen;
import calendar.drawing.color.Theme;
import calendar.input.InputLayer;
import calendar.input.SectionInputLayer;
import calendar.storage.Calendar;
import calendar.storage.Event;
import calendar.util.Vec2;

public class State {
    public Calendar calendar;
    public Screen screen;
    private LocalDate date;
    private Theme colors;
    public int popupLine;

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

    public InputLayer showSectionPopup() {
        if(screen.addSectionPopup())
            return new SectionInputLayer(this);
        else
            return null;
    }

    // add back once EventInputLayer is done
    // public InputLayer showEventPopup() {
    //     Event event = calendar.createDefaultEvent();
    //     if (screen.addAddEventPopup(event))
    //         return new EventInputLayer(this, event);
    //     else 
    //         return null;
    // }
}
