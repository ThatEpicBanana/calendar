package calendar.drawing;

public class Ansi {
    public static final String ESC = "\033";
    public static final String FOREGROUND = ESC + "[38;2;%d;%d;%dm";
    public static final String BACKGROUND = ESC + "[48;2;%d;%d;%dm";
    public static final String RESET = ESC + "[0m";
    private static final String COLOR = FOREGROUND + BACKGROUND + "%s";

    public static String color(char character, Color foreground, Color background) {
        return String.format(
            COLOR,
            foreground.r, foreground.g, foreground.b, 
            background.r, background.g, background.b, 
            character
        );
    }
}
