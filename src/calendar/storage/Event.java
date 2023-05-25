package calendar.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {
    private String title;

    private LocalDateTime start;
    private LocalDateTime end;

    private Section section;
    private Calendar calendar;

    public Event(Calendar calendar, Section section, String title, LocalDateTime start, LocalDateTime end) {
        this.title = title;

        this.start = start;
        this.end = end;

        this.calendar = calendar;
        this.section = section;
    }

    public String title() { return title; }
    public void setTitle(String title) { this.title = title; calendar.state().updateScreen(); }

    public LocalDateTime start() { return start; }
    public void setStart(LocalDateTime start) { this.start = start; calendar.state().updateScreen(); }

    public LocalDateTime end() { return end; }
    public void setEnd(LocalDateTime end) { this.end = end; calendar.state().updateScreen(); }

    public Section section() { return section; }
    // screen will get updated elsewhere
    protected void moveTo(Section section) { this.section = section; }


    private boolean sameMonth(LocalDateTime a, LocalDate b) { return a.getYear() == b.getYear() && a.getMonth() == b.getMonth(); }

    public boolean inMonth(LocalDate time) {
        LocalDate month = time.withDayOfMonth(1);
        return sameMonth(start, month) || sameMonth(end, month);
    }
} 
