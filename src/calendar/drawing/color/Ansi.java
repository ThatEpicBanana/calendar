package calendar.drawing.color;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Scanner;

import calendar.util.Coord;

public class Ansi {
    public static final String ESC = "\033";
    public static final String RESET = ESC + "[0m";

    public static final String FOREGROUND = ESC + "[38;2;%d;%d;%dm";
    public static final String FOREGROUND_RESET = ESC + "[39m";

    public static final String BACKGROUND = ESC + "[48;2;%d;%d;%dm";
    public static final String BACKGROUND_RESET = ESC + "[49m";

    public static final String CLEAR_SCREEN = ESC + "[2J" + ESC + "[H";

    private static final String MOVE = ESC + "[%d;%dH";
    public static String move(int x, int y) { return String.format(MOVE, x, y); }

    private static final String UP = ESC + "[%dA";
    public static String up(int amt) { return String.format(UP, amt); }

    private static final String COLUMN = ESC + "[%dG";
    public static final String TO_LEFT = ESC + "[0G";
    public static String column(int x) { return String.format(COLUMN, x); }

    public static void color(StringBuilder builder, char character, Color foreground, Color background) {
        if(foreground != null)
            builder.append(String.format(FOREGROUND, foreground.r, foreground.g, foreground.b));
        else builder.append(FOREGROUND_RESET);

        if(background != null)
            builder.append(String.format(BACKGROUND, background.r, background.g, background.b));
        else builder.append(BACKGROUND_RESET);

        builder.append(character);
    }

    private static final String GET_CURSOR = ESC + "[6n";

    // you gotta do some cursed stuff for this
    public static Coord getDimensions() {
        boolean askForInput = true;

        // turn off canonical mode on unix
        File f = new File("/bin/stty");
        if(f.exists() && f.canExecute()) {
            ProcessBuilder pb = new ProcessBuilder("/bin/stty", "-icanon");
            pb.redirectInput(Redirect.from(new File("/dev/tty")));
            try { 
                pb.start(); 
                askForInput = false;
            } catch(IOException e) { e.printStackTrace(); }
        }

        // move the furthest possible and ask for cursor position
        System.out.print(move(1000, 1000) + GET_CURSOR);

        // read the output position
        Scanner scanner = new Scanner(System.in).useDelimiter("R");

        // prompt the user to press enter if needed
        // this is definitely a feature, not a bug 
        try { Thread.sleep(10); } catch(Exception e) {}
        if(askForInput)
            // prompt the user to press enter
            // windows seems to draw the input over everthing else, 
            // so just put it in the top left
            System.out.print(TO_LEFT + "Press Enter to Start..." + move(0, 0));
        else
            // linux, on the other hand, just writes the input to the terminal
            // so just remove the lind
            System.out.print(TO_LEFT + ESC + "[0K");

        // will output like ESC[row;columnR
        String pos = scanner.next();
        int square = pos.indexOf('[');
        int semi = pos.indexOf(';');

        scanner.close();

        // deserialize
        int row = Integer.parseInt(pos.substring(square + 1, semi));
        int column = Integer.parseInt(pos.substring(semi + 1));

        return new Coord(column, row);
    }
}
