package calendar.drawing.color;

// utilities for dealing with ansi escape codes
public class Ansi {
    public static final String ESC = "\033";
    public static final String RESET = ESC + "[0m";
    public static final String HOME = ESC + "[H";

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
    public static String column(int x) { return String.format(COLUMN, x); }

    public static final String TO_TOP = ESC + "[1000A";
    public static final String TO_BOTTOM = ESC + "[1000B";
    public static final String TO_LEFT = ESC + "[0G";
    public static final String TO_RIGHT = ESC + "[1000G";

    public static final String SHOW_CURSOR = ESC + "[?25h";
    public static final String HIDE_CURSOR = ESC + "[?25l";

    public static void color(StringBuilder builder, char character, Color foreground, Color background) {
        if(foreground != null && foreground != Color.FORCE_TRANSPARENT)
            builder.append(String.format(FOREGROUND, foreground.r, foreground.g, foreground.b));
        else builder.append(FOREGROUND_RESET);

        if(background != null && background != Color.FORCE_TRANSPARENT)
            builder.append(String.format(BACKGROUND, background.r, background.g, background.b));
        else builder.append(BACKGROUND_RESET);

        builder.append(character);
    }

    public static void showCursor() { System.out.print(SHOW_CURSOR); }
    public static void hideCursor() {
        System.out.print(HIDE_CURSOR);
        // ensure that the cursor gets shown whenever the process quits,
        // such as with Ctrl-C or an exception getting thrown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> Ansi.showCursor()));
    }
}
