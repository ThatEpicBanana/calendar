package calendar.storage;

import java.util.ArrayList;
import java.util.List;

import calendar.drawing.color.Color;
import calendar.state.State;

public class Section {
    private Calendar calendar;
    // it's fine to mutate these because the drawing changes
    private String title;
    private int color; 

    private State state;

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

    protected void add(Event event) { this.events.add(event); }
    protected void remove(Event event) { this.events.remove(event); }

    public List<Event> events() { return this.events; }
}
