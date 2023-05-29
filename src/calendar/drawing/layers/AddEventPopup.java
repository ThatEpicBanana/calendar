package calendar.drawing.layers;

import java.util.Arrays;

import calendar.drawing.Canvas;
import calendar.drawing.color.Color;
import calendar.state.State;
import calendar.storage.EditingEvent;

public class AddEventPopup extends Popup {
    private EditingEvent event;

    public AddEventPopup(int width, EditingEvent event, State state) {
        super(width, state);
        this.event = event;
    }

    private void drawTitle(Canvas canvas, String title, Color highlight, int x, int y, int width) {
        drawText(canvas, title, y);
        canvas.highlightBox(x, line(y), width, 1, colors().highlightText(), highlight);
    }

    private void drawTitled(Canvas canvas, int line, int y, String title, String text, Color highlight) {
        int width = Math.max(title.length(), text.length()) + 2;
        int x = (width() - width) / 2;

        drawTitle(canvas, title, highlight, x, y, width);

        drawText(canvas, text, y + 1);
        canvas.highlightBox(x, line(y + 1), width, 1, textColor(line), textBackground(line));
    }

    private void drawTitled(Canvas canvas, int startLine, int y, String title, String[] text, Color highlight) {
        int textWidth = Arrays.stream(text).map(line -> line.length()).max(Integer::compare).get();
        int width = Math.max(title.length(), textWidth) + 2;
        int x = (width() - width) / 2;

        drawTitle(canvas, title, highlight, x, y, width);

        for(int i = 0; i < text.length; i++) {
            drawText(canvas, text[i], y + i + 1);
            int line = startLine + i;
            canvas.highlightBox(x, line(y + i + 1), width, 1, textColor(line), textBackground(line));
        }
    }

    public Color textBackground(int line) {
        return selected(line) ? colors().editingBackground() : colors().textBackground();
    }

    public Color textColor(int line) {
        return selected(line) && state.editingHover() ? event.section().color() : colors().text();
    }

    public Color buttonTextColor(int line) {
        return selected(line) ? colors().editingForeground() : colors().highlightText();
    }

    public Canvas draw() {
        Canvas canvas = super.draw();

        Color highlight = event.section().color();

        drawText(canvas, "  Add  Task  ", 0, colors().highlightText(), highlight);

        drawTitled(canvas, 0, 3, "Title", event.title(), highlight);
        drawTitled(canvas, 1, 6, "Time Frame", new String[]{ event.start(), event.end() }, highlight);
        drawTitled(canvas, 3, 10, "Section", event.section().title(), highlight);

         drawTextLeft(canvas, " Confirm ", 13, 4, buttonTextColor(4), highlight);
        drawTextRight(canvas, " Abandon ", 13, 4, buttonTextColor(5), highlight);

        return canvas;
    } 
}
