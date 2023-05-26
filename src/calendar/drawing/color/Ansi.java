package calendar.drawing.color;

public class Ansi {
    public static final String ESC = "\033";
    public static final String FOREGROUND = ESC + "[38;2;%d;%d;%dm";
    public static final String FOREGROUND_RESET = ESC + "[39m";
    public static final String BACKGROUND = ESC + "[48;2;%d;%d;%dm";
    public static final String BACKGROUND_RESET = ESC + "[49m";
    public static final String RESET = ESC + "[0m";

    public static void color(StringBuilder builder, char character, Color foreground, Color background) {
        if(foreground != null)
            builder.append(String.format(FOREGROUND, foreground.r, foreground.g, foreground.b));
        else builder.append(FOREGROUND_RESET);

        if(background != null)
            builder.append(String.format(BACKGROUND, background.r, background.g, background.b));
        else builder.append(BACKGROUND_RESET);

        builder.append(character);
    }
}
