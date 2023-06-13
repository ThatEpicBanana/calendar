package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.state.Config;
import calendar.state.State;

// the settings dialogue
public class PreferencesPopup extends Popup {
    public PreferencesPopup(int width, State state) {
        super(width, state);
    }

    private Config config() { return state.config; }

    public Canvas draw() {
        Canvas box = super.draw();
        Canvas canvas = box.offsetMargin(2);

        canvas.textCentered(" Preferences ", 0, state.monthColorText(), state.monthColor());

        Canvas themeBox = canvas.offsetCenteredMargin(3, 2, 8);

        themeBox.rectangle(0, 0, themeBox.width(), themeBox.height() - 1, false);
        themeBox.textCentered("   Theme   ", 0, state.monthColorText(), state.monthColor());

        Canvas themeList = themeBox.offsetCenteredMargin(1, 2, 5);

        themeList.textCentered("Latte",       0);
        themeList.textCentered("Frappe",      1);
        themeList.textCentered("Macchiato",   2);
        themeList.textCentered("Mocha",       3);
        themeList.textCentered("Transparent", 4);

        themeList.highlightBox(0, 0, themeList.width(), 1, Theme.Latte.text(),       Theme.Latte.background());
        themeList.highlightBox(0, 1, themeList.width(), 1, Theme.Frappe.text(),      Theme.Frappe.background());
        themeList.highlightBox(0, 2, themeList.width(), 1, Theme.Macchiato.text(),   Theme.Macchiato.background());
        themeList.highlightBox(0, 3, themeList.width(), 1, Theme.Mocha.text(),       Theme.Mocha.background());
        themeList.highlightBox(0, 4, themeList.width(), 1, Theme.Transparent.text(), Theme.Transparent.background());

        if(hover() < 5) {
            int selectedLine = hover();
            themeList.text("→", 1, selectedLine);
            themeList.textRight("←", selectedLine);
        }

        Canvas others = canvas.offsetCenteredMargin(11, 3, 2);

        others.textCentered("Colorful Months " + (config().colorfulMonths() ? '✓' : '✕'), 0);
        others.highlightBox(0, 0, others.width(), 1, selected(5) ? colors().text() : colors().buttonText(), colors().buttonBackground());

        others.textCentered("Day Color", 2);
        others.highlightBox(0, 2, others.width(), 1, colors().highlightText(), config().selectedDayColor());

        if(selected(6)) {
            others.text("←", 1, 2);
            others.textRight("→", 2);
        }

        return box;
    }
}
