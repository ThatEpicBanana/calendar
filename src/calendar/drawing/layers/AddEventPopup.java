package calendar.drawing.layers;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import calendar.drawing.Canvas;
import calendar.drawing.color.Color;
import calendar.state.State;
import calendar.storage.Event;

public class AddEventPopup extends Popup {
    private Event event;

    public AddEventPopup(int width, Event event, State state) {
        super(width, state);
        this.event = event;
    }

    private void drawTitled(Canvas canvas, String text, String title, int y, Color highlight, boolean highlightedText)
        { drawTitled(canvas, new String[]{text}, text.length(), title, y, highlight, highlightedText); }
    private void drawTitled(Canvas canvas, String[] text, int width, String title, int y, Color highlight, boolean highlightedText) {
        // get full width of the box
        int textWidth = Math.max(title.length(), width) + 2;

        // colors of text
        Color titledTextFore = highlightedText ? highlight : colors().text();
        Color titleTextFore = colors().highlightText();
        Color background = colors().textBackground();

        // draw each line of text
        drawText(canvas, title, y);
        for(int i = 0; i < text.length; i++)
            drawText(canvas, text[i], y + i + 1);

        // left of the box
        int x = (width() - textWidth) / 2;

        // highlight the top and bottom
        canvas.highlightBox(x, line(y),     textWidth,           1,  titleTextFore, highlight);
        canvas.highlightBox(x, line(y + 1), textWidth, text.length, titledTextFore, background);
    }

    public Canvas draw() {
        Canvas canvas = super.draw();

        Color highlight = event.section().color();
        Color hightext = colors().highlightText();
        Color background = colors().textBackground();
        Color text = colors().text();

        drawText(canvas, "  Add  Task  ", 0, hightext, highlight);

        drawTitled(canvas, event.title(), "Title", 3, highlight, false);

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String start = event.start().format(formatter);
        String end = event.end().format(formatter);
        int length = Math.max(start.length(), end.length());
        drawTitled(canvas, new String[]{ start, end }, length, "Time Frame", 6, highlight, false);
        
        drawTitled(canvas, event.section().title(), "Section", 10, highlight, true);

         drawTextLeft(canvas, " Confirm ", 13, 4, hightext, highlight);
        drawTextRight(canvas, " Abandon ", 13, 4, hightext, highlight);

        return canvas;
    } 
}
