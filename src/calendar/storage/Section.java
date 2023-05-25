package calendar.storage;

import calendar.drawing.Color;

public class Section {
    // it's fine to mutate these because the drawing changes
    private Calendar calendar;
    private String title;
    private Color color; 

    public Section(Calendar calendar, String title, Color color) {
        this.calendar = calendar;
        this.title = title;
        this.color = color;
    }

    public String title() { return this.title; }
    public void setTitle(String title) { this.title = title; calendar.state().updateScreen(); }

    public Color color() { return this.color; }
    public void setColor(Color color) { this.color = color; calendar.state().updateScreen(); }
}
