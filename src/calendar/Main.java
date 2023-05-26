package calendar;

import java.time.LocalDate;

import calendar.drawing.BoxChars;
import calendar.drawing.Color;
import calendar.drawing.layers.Month;
import calendar.state.State;
import calendar.storage.Calendar;
import calendar.storage.Section;

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
        
        // light
        // String background = "\033[48;2;239;241;245m";
        // String foreground = "\033[38;2;230;69;83m";
        // String foreground = "\033[38;2;76;79;105m";
        // dark
        // String background = "\033[48;2;30;30;46m";
        // String foreground = "\033[38;2;243;139;168m";
        // String reset = "\033[0m";

        // Popup popup = new Popup(10, 5);
        // System.out.print(background + foreground);
        // popup.print();
        // System.out.print(reset);
        
        // System.out.print(background + foreground);
        // Grid grid = new Grid(11, 4, 5, 5, Justification.Middle);
        // grid.setValue(1, 1, "Bello");
        // grid.draw()
        //     .merge(30, 7, Canvas.rectangle(13, 7, false).drawText("Bello", 4, 3))
        //     .print();
        // System.out.print(reset);


        LocalDate date = LocalDate.now().withMonth(java.time.Month.AUGUST.getValue());

        State state = new State(date);

        Calendar calendar = state.calendar;
        Section school = calendar.addSection("school", new Color(220, 138, 120));
        Section birthdays = calendar.addSection("birthdays", new Color(136, 57, 239));
        Section holidays = calendar.addSection("holidays", new Color(23, 146, 153));

        LocalDate july4 = date.withDayOfMonth(10);
        calendar.addEvent(holidays, "4th of July", july4.atTime(6, 30), july4.atTime(7, 30));

        LocalDate billey = date.withDayOfMonth(14);
        calendar.addEvent(birthdays, "Billey Birthday", billey.atTime(6, 30), billey.atTime(7, 30));

        LocalDate newYears = date.withDayOfMonth(14);
        calendar.addEvent(holidays, "New Years", newYears.atTime(12, 30), newYears.atTime(13, 30));

        LocalDate placeholder = date.withDayOfMonth(14);
        calendar.addEvent(holidays, "Placeholder", placeholder.atTime(12, 30), placeholder.atTime(13, 30));

        LocalDate finals = date.withDayOfMonth(23);
        calendar.addEvent(school, "Study for Finals", finals.atTime(6, 30), finals.plusDays(2).atTime(7, 30));

        Month month = new Month(state, 11, 4);
        month.print();
    }
}
