package calendar.storage;

import java.util.ArrayList;
import java.util.List;

import calendar.drawing.Color;

public class Section {
    private Calendar calendar;
    // it's fine to mutate these because the drawing changes
    private String title;
    private Color color; 

    private ArrayList<Event> events = new ArrayList<>();

    public Section(Calendar calendar, String title, Color color) {
        this.calendar = calendar;
        this.title = title;
        this.color = color;
    }

    public String title() { return this.title; }
    public void setTitle(String title) { this.title = title; calendar.state().updateScreen(); }

    public Color color() { return this.color; }
    public void setColor(Color color) { this.color = color; calendar.state().updateScreen(); }

    protected void add(Event event) { this.events.add(event); }
    protected void remove(Event event) { this.events.remove(event); }

    public List<Event> events() { return this.events; }
}
