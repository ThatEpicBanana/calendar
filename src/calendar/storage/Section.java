package calendar.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.state.State;

// represents a section of events in the calendar
// it holds its own set of events, allowing the user to sort them
// (although that isn't implemented just yet)
public class Section implements Serializable {
    private transient State state;
    private transient Calendar calendar;

    private String title;
    private int color; 

    private ArrayList<Event> events = new ArrayList<>();

    public Section(Calendar calendar, String title, int color, State state) {
        this.calendar = calendar;
        this.title = title;
        this.color = color;
        this.state = state;
    }

    public String title() { return this.title; }
    public void setTitle(String title) { this.title = title; calendar.state().updateScreen(); }

    public int colorIndex() { return color; }
    public Color color() { return state.colors().highlights()[color]; }
    public void setColor(int newColor) { this.color = newColor; calendar.state().updateScreen(); }

    public void addColor(int i) {
        color += i;

        int max = Theme.HIGHLIGHT_COUNT;
        color = (color % max + max) % max; // positive modulo 

        calendar.state().updateScreen();
    }

    protected void add(Event event) { this.events.add(event); }
    protected void remove(Event event) { this.events.remove(event); }

    public List<Event> events() { return this.events; }

    // populate after deserialization
    protected void populate(Calendar calendar, State state) {
        this.calendar = calendar;
        this.state = state;

        for(Event event : events)
            event.populate(calendar, this);
    }
}
