package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.state.Settings;
import calendar.state.State;

public class PreferencesPopup extends Popup {
    public PreferencesPopup(int width, State state) {
        super(width, state);
    }

    private Settings settings() { return state.settings; }

    public Canvas draw() {
        Canvas canvas = super.draw();

        int margin = 4;
        int width = width() - margin * 2;

        drawText(canvas, " Preferences ", 0, state.monthColorText(), state.monthColor());

        drawText(canvas, "Theme",        3);
        canvas.highlightBox(margin + 2, line(3), width - 4, 1, colors().text(), colors().textBackground());

        drawText(canvas, "Latte",       5);
        drawText(canvas, "Frappe",      6);
        drawText(canvas, "Macchiato",   7);
        drawText(canvas, "Mocha",       8);
        drawText(canvas, "Transparent", 9);

        canvas.highlightBox(margin, line(5), width, 1, Theme.Latte.text(),       Theme.Latte.background());
        canvas.highlightBox(margin, line(6), width, 1, Theme.Frappe.text(),      Theme.Frappe.background());
        canvas.highlightBox(margin, line(7), width, 1, Theme.Macchiato.text(),   Theme.Macchiato.background());
        canvas.highlightBox(margin, line(8), width, 1, Theme.Mocha.text(),       Theme.Mocha.background());
        canvas.highlightBox(margin, line(9), width, 1, Theme.Transparent.text(), Theme.Transparent.background());

        if(hover() < 5) {
            int selectedLine = 5 + hover();
            drawTextLeft(canvas, "→", selectedLine, margin + 1);
            drawTextRight(canvas, "←", selectedLine, margin + 1);
        }

        drawText(canvas, "Colorful Months ✕", 11);
        canvas.highlightBox(margin, line(11), width, 1, selected(5) ? colors().text() : colors().buttonText(), colors().buttonBackground());

        drawText(canvas, "Day Color", 13);
        canvas.highlightBox(margin, line(13), width, 1, colors().highlightText(), settings().selectedDayColor());

        if(selected(6)) {
            drawTextLeft(canvas, "←", 13, margin + 1);
            drawTextRight(canvas, "→", 13, margin + 1);
        }

        return canvas;
    }

    private Color background(int line, Theme theme) {
        return selected(line) ? theme.editingBackground() : theme.textBackground();
    }

    private int themeIndex(Theme theme) {
        switch(theme) {
            case Latte: return 0;
            case Frappe: return 1;
            case Macchiato: return 2;
            case Mocha: return 3;
            case Transparent: return 4;
            default: return -1;
        }
    }
}
