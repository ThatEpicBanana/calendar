package calendar;

import calendar.drawing.BoxChars;
import calendar.drawing.components.Grid;
import calendar.drawing.Justification;
import calendar.drawing.layers.Layer;
import calendar.drawing.layers.Popup;

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

        System.out.println("its so over");

        // notice: layers will probably just get replaced with Drawable
        // but this is good for a bit of testing
        // Layer layer = new Layer(16, 16);

        // layer.chars[0][1] = Text.LIGHT_HORIZONTAL;
        // layer.chars[0][2] = Text.LIGHT_HORIZONTAL;
        // layer.chars[0][3] = Text.LIGHT_HORIZONTAL;

        // layer.chars[0][0] = Text.lightBoxChars[0][1][0][1];

        // layer.chars[1][0] = Text.LIGHT_VERTICAL;
        // layer.chars[2][0] = Text.LIGHT_VERTICAL;
        // layer.chars[3][0] = Text.LIGHT_VERTICAL;

        // layer.print();
        
        // light
        // String background = "\033[48;2;239;241;245m";
        // String foreground = "\033[38;2;230;69;83m";
        // String foreground = "\033[38;2;76;79;105m";
        // dark
        // String background = "\033[48;2;30;30;46m";
        // String foreground = "\033[38;2;243;139;168m";
        // String reset = "\033[0m";

        char up = BoxChars.light[1][0][0][0];
        char right = BoxChars.light[0][0][0][1];

        System.out.println("up char: '" + up + "', right char: '" + right + "'");
        System.out.println("combined: '" + BoxChars.combine(up, right, false) + "'");

        // Popup popup = new Popup(10, 5);
        // System.out.print(background + foreground);
        // popup.print();
        // System.out.print(reset);
        
        // System.out.print(background + foreground);
        Grid grid = new Grid(11, 4, 5, 5, Justification.Middle);
        grid.setValue(1, 1, "Bello");
        grid.print();
        // System.out.print(reset);
    }
}
