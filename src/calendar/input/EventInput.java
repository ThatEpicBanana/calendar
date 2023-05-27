package calendar.input;

import java.util.ArrayList;
import java.util.List;
import java.io.Console;

public class EventInput {
    public static void main(String[] args) {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Cockinba", "1.1.11 11:00p - 1.2.22 1:00a"));
        events.add(new Event("MSG E Number", "1.3.11 3:00p - 1.4.22 5:00p"));
        events.add(new Event("Not Good Dragon Time", "1.5.11 8:00a - 1.6.22 10:00a"));

        Section section = new Section(events);

        // Choose an event from the index
        Event selectedEvent = section.chooseEvent();
        System.out.println("Selected Event: " + selectedEvent.getTitle());

        // Modify the values using invisible text popup
        Console console = System.console();

        System.out.print("Enter new title for the event: ");
        String newTitle = console.readLine();
        selectedEvent.setTitle(newTitle);

        System.out.print("Enter new time frame for the event (e.g., 1.1.23 9:00a - 1.2.23 10:30a): ");
        String newTimeFrame = console.readLine();
        selectedEvent.setTimeFrame(newTimeFrame);

        // Print the updated event
        System.out.println("\nUpdated Event:");
        System.out.println("Title: " + selectedEvent.getTitle());
        System.out.println("Time Frame: " + selectedEvent.getTimeFrame());
    }
}

class Section {
    private List<Event> events;

    public Section(List<Event> events) {
        this.events = events;
    }

    public Event chooseEvent() {
        return events.get(0);
    }
}

class Event {
    private String title;
    private String timeFrame;

    public Event(String title, String timeFrame) {
        this.title = title;
        this.timeFrame = timeFrame;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
}
