package calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;

import calendar.drawing.color.Ansi;
import calendar.drawing.color.Theme;
import calendar.input.Input;
import calendar.state.State;
import calendar.storage.Calendar;
import calendar.storage.Event;
import calendar.storage.Section;
import calendar.util.Vec2;

public class Main {
    public static void main(String[] args) {
        // GENERAL IDEAS:
        //
        // calendar will be comprised of a set of passes,
        // one grid for the month
        // another for the days of the month
        // and another for the day grid
        // these grids will be combined in the MultiGrid using Text.combine
        //
        // then, there'll be passes for the numbers, seperate month shading, and tasks
        //
        // this will all be in its own layer - the Month layer
        //
        // then, popups are seperate layers that could go on top
        // and all the layers are packaged in a screen??? not so sure here
        // nah there'll probably be a seperate class that manages the month and all the popups
        // each screen is year-month-day (although the others may be too time-consuming)
        
        LocalDate date = LocalDate.now().withMonth(java.time.Month.AUGUST.getValue()).withDayOfMonth(8);
        Theme theme = Theme.Latte;

        State state = new State(date, theme, Ansi.getDimensions(), new Vec2(11, 4));

        state.updating = false;

        Calendar calendar = state.calendar;
        Section school = calendar.addSection("schol", 0);
        Section birthdays = calendar.addSection("birthdays", 3);
        Section holidays = calendar.addSection("holidays", 9);

        LocalDate july4 = date.withDayOfMonth(10);
        calendar.addEvent(holidays, "4th of July", july4.atTime(6, 30), july4.atTime(7, 30));

        LocalDate fourteenth = date.withDayOfMonth(14);
        calendar.addEvent(birthdays, "Billey Birthday", fourteenth.atTime(6, 30), fourteenth.atTime(7, 30));
        calendar.addEvent(holidays, "New Years", fourteenth.atTime(12, 30), fourteenth.atTime(13, 30));
        // for overflow
        calendar.addEvent(holidays, "Placeholder", fourteenth.atTime(13, 30), fourteenth.atTime(14, 30));

        LocalDate finals = date.withDayOfMonth(23);
        calendar.addEvent(school, "Study for Finals", finals.atTime(6, 30), finals.plusDays(2).atTime(7, 30));

        state.updating = true;

        // state.screen.addSectionPopup();
        state.screen.addAddEventPopup(state.calendar.createDefaultEvent());

        // Input input = new Input();

        // try { Thread.sleep(2000); } catch(Exception e) { e.printStackTrace(); }

        // for(int i = 10; i >= 0; i--) {
        //     school.setColor(i);
        //     try { Thread.sleep(200); } catch(Exception e) { e.printStackTrace(); }
        // }

        // checking fps
        // long start = System.nanoTime();
        // for(int i = 1000; i >= 0; i--)
        //     state.updateScreen();
        // long end = System.nanoTime();

        // double seconds = (end - start) / 1000000000.0;
        // System.out.println(1000 / seconds);
    }
}
