package calendar.storage;

import java.util.ArrayList;

import calendar.state.State;

public class Calendar {
    private ArrayList<Section> sections = new ArrayList<>();

    private State state;

    public Calendar(State state) {
        this.state = state;
    }

    public State state() { return this.state; }
}
