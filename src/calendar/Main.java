package calendar;

import java.time.LocalDate;

import calendar.drawing.color.Ansi;
import calendar.drawing.color.Theme;
import calendar.input.Input;
import calendar.input.InputLayer;
import calendar.state.State;
import calendar.storage.Calendar;
import calendar.storage.Section;
import calendar.util.Vec2;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {
    public static void main(String[] args) {
        // This is a calendar app that allows you to:
        // - define different Sections
        // - that each have different sets of Events
        // - that can all be drawn to the screen
        //
        // The code is seperated into four main different areas:
        // - state
        //   - the global state of the application
        //   - all centered in State.java
        //   - acts as a middle ground between the drawing and the input
        //   - holds the Screen and Input(handler)
        // - storage
        //   - pretty much an extension of state,
        //   - all centered in Calendar.java
        //   - stores all the Sections and Events
        //   - and provides methods to change them
        // - drawing
        //   - drawing the screen based on the state
        //   - all centered in Screen.java
        //   - based on a Canvas, which holds 2d arrays for the text and color
        //   - each seperate component writes to its own Canvas which all get combined
        // - input
        //   - handles input
        //   - all centered in Input.java
        //   - defines a set of InputLayers for each screen
        //   - each InputLayer has its own keybindings
        // 
        // There is one library that we *had* to use to make it all work called Jline
        // It has many functionalities, but we only use it to:
        // - enter the terminal into raw mode
        //   - if this doesn't get done, for each key the user presses, they would have to press enter as well
        //   - this is easy enough to enable separately on unix (linux and mac), but on windows you have to use native code
        //     - so you pretty much have to use a library to enable it for all users
        // - get the dimensions of the screen
        //   - again, this isn't that bad to do on linux, but I couldn't find a good way for windows
        // The only place that uses JLine is right below this comment

        Vec2 dimensions = new Vec2(0, 0);
        try {
            Terminal terminal = TerminalBuilder.builder().system(true).jansi(true).build();
            terminal.enterRawMode();

            dimensions = new Vec2(terminal.getWidth(), terminal.getHeight());
        } catch(Exception e) { e.printStackTrace(); }

        // set up a test state

        LocalDate date = LocalDate.now().withMonth(java.time.Month.AUGUST.getValue()).withDayOfMonth(8);
        Theme theme = Theme.Mocha;

        State state = new State(date, theme, dimensions, new Vec2(11, 4));

        state.updating = false;

        Calendar calendar = state.calendar;
        Section school = calendar.addSection("schol", 1);
        Section birthdays = calendar.addSection("birthdays", 10);
        Section holidays = calendar.addSection("holidays", 5);

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

        Input input = new Input();
        InputLayer layer = state.showSectionPopup();
        input.push(layer);

        input.inputLoop();
        // state.screen.addAddEventPopup(state.calendar.createDefaultEvent());

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
