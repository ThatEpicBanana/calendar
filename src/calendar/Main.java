package calendar;

import calendar.drawing.Text;
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

        System.out.println("BELLOOOOO yesyes");

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

        char up = Text.lightBoxChars[1][0][0][0];
        char right = Text.lightBoxChars[0][0][0][1];

        System.out.println("up char: '" + up + "', right char: '" + right + "'");
        System.out.println("combined: '" + Text.combine(up, right, false) + "'");

        Popup popup = new Popup(10, 10);
        popup.print();
    }
}
