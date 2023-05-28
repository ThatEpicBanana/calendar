package calendar.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import calendar.state.State;

public class Calendar {
    private ArrayList<Section> sections = new ArrayList<>();

    private State state;

    public Calendar(State state) {
        this.state = state;
    }

    public State state() { return this.state; }
    public List<Section> sections() { return this.sections; }

    public Section createDefaultSection() {
        return new Section(this, "Add Section", 0, state);
    }

    public Section addSection(String title, int color) {
        Section section = new Section(this, title, color, state);
        sections.add(section);
        return section;
    }

    public void removeSection(int index) {
        this.sections.remove(index);
        state.updateScreen();
    }


    public EditingEvent createDefaultEvent() {
        return new EditingEvent(this);
    }

    public Event addEvent(Section section, String name, LocalDateTime start, LocalDateTime end) {
        Event event = new Event(this, section, name, start, end);
        section.add(event);
        state().updateScreen();
        return event;
    }

    public Event addEvent(Section section, Event event) {
        event.moveTo(section);
        section.add(event);
        state().updateScreen();
        return event;
    }

    public void addEvent(EditingEvent event) {
        Event toAdd = event.toEvent();
        toAdd.section().add(toAdd);
    }
    
    public void moveEvent(Event event, Section to) {
        event.section().remove(event);
        addEvent(to, event);
    }


    public Stream<Event> eventsStream() {
        // java iterators
        // thought i'd never see it
        return sections.stream()
            .map(section -> section.events())
            .flatMap(Collection::stream)
            .sorted();
    }

    public List<Event> events() {
        return eventsStream()
            .collect(Collectors.toList());
    }

    public List<Event> eventsInMonth(LocalDate date) {
        return eventsStream()
            .filter(event -> event.inMonth(date))
            .collect(Collectors.toList());
    }

    public List<Event> eventsInCurrentMonth() { return eventsInMonth(state().date()); }
} 
