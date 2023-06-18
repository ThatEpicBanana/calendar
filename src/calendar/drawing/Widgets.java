package calendar.drawing;

import java.util.Arrays;

import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.state.State;
import calendar.state.layer.SelectionsLayer;
import calendar.util.Vec2;

public class Widgets {
    private State state;
    private SelectionsLayer selector;

    public Widgets(State state, SelectionsLayer selector) {
        this.state = state;
        this.selector = selector;
    }

    public Theme colors() { return state.colors(); }

    private boolean selected(int y) { return selector != null ? selector.selected(y) : false; }
    private boolean editingSelection() { return selector != null ? selector.editing() : false; }

    public Canvas.Drawer toggle(String title, Just justifification, boolean predicate, int selection) {
        return (canvas) -> {
            Canvas inset = justifification.getCanvas(canvas, new Vec2(canvas.width(), 1));

            inset.text(title + " " + (predicate ? '✓' : '✕'), Just.centeredOnRow(0))
                .highlightBox(0, 0, inset.width(), 1, selected(selection) ? colors().text() : colors().buttonText(), colors().buttonBackground());
        };
    }

    public Canvas.Drawer rollingSelection(String title, Just justification, Color color, int selection) {
        return (canvas) -> {
            Canvas inset = justification.getCanvas(canvas, new Vec2(canvas.width(), 1));

            Color foreground = selected(selection) && editingSelection() ? colors().editingForeground() : colors().highlightText();

            inset.text(title, Just.centeredOnRow(0))
                 .highlightBox(0, 0, inset.width(), 1, foreground, color);

            if(selected(selection)) {
                inset.text("←", Just.leftOfRow(0));
                inset.text("→", Just.rightOfRow(0));
            }
        };
    }

    public Canvas.Drawer button(String title, Just justification, Color highlight, int selection) {
        return (canvas) -> {
            int width = title.length() + 2;
            Vec2 pos = justification.get(canvas.dims(), new Vec2(width, 1));

            Color foreground = selected(selection) ? colors().editingForeground() : colors().highlightText();

            canvas.text(title, pos.x + 1, pos.y)
                .highlightBox(pos.x, pos.y, width, 1, foreground, highlight);
        };
    }

    private String sanitize(String text, int maxWidth, int selection) {
        int len = text.length();
        if(selected(selection) && selector.editing())
            // from the end
            return text.substring(len - Math.min(len, maxWidth));
        else
            // from the beginning
            return text.substring(0, Math.min(len, maxWidth));
    }

    private Color titledTextBackground(int line) {
        return selected(line) ? colors().editingBackground() : colors().textBackground();
    }

    private Color titledTextForeground(int line, Color highlight) {
        return selected(line) && editingSelection() ? highlight : colors().text();
    }

    public Canvas.Drawer titledText(String title, Just justification, String text, Color highlight, int selection) {
        return (canvas) -> {
            // limited to the width of the canvas - 2
            int maxWidth = canvas.width() - 2;
            int textWidth = Math.max(title.length(), text.length());
            int width = Math.min(textWidth, maxWidth - 2) + 2; // text width has a margin of 1 on each side

            // get an inset canvas of the bounds of the widget
            // think of a box of the bounds of the widget
            Canvas inset = justification.getCanvas(canvas, new Vec2(width, 2));

            // the title is expected to already be sanitized
            String sanitizedText = sanitize(text, maxWidth - 2, selection);
            
            inset.text(title, Just.centeredOnRow(0))
                .highlightBox(0, 0, width, 1, colors().highlightText(), highlight);
            inset.text(sanitizedText, Just.centeredOnRow(1))
                .highlightBox(0, 1, width, 1, titledTextForeground(selection, highlight), titledTextBackground(selection));
        };
    }

    public Canvas.Drawer titledText(String title, Just justification, String[] text, Color highlight, int selection) {
        return (canvas) -> {
            // limited to the width of the canvas - 2
            int maxWidth = canvas.width() - 2;
            int textWidth = Arrays.stream(text).map(line -> line.length()).max(Integer::compare).get();
            int width = Math.min(Math.max(title.length(), textWidth), maxWidth - 2) + 2; // text width has a margin of 1 on each side

            Canvas inset = justification.getCanvas(canvas, new Vec2(width, 1 + text.length));

            inset.text(title, Just.centeredOnRow(0))
                .highlightBox(0, 0, width, 1, colors().highlightText(), highlight);

            for(int i = 0; i < text.length; i++) {
                String sanitizedText = sanitize(text[i], maxWidth - 2, selection);

                inset.text(sanitizedText, Just.centeredOnRow(i + 1))
                    .highlightBox(0, i + 1, width, 1, titledTextForeground(selection + i, highlight), titledTextBackground(selection + i));
            }
        };
    }
}
