package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.Just;
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

        canvas.text(" Preferences ", Just.centeredOnRow(0), state.monthColorText(), state.monthColor());

        canvas.offsetCenteredMargin(3, 2, 8).draw(this::themeBox);

        Canvas others = canvas.offsetCenteredMargin(11, 3, 2);

        others.draw(wid.toggle("Colorful Months", Just.centeredOnRow(0), config().colorfulMonths(), 5));
        others.draw(wid.rollingSelection("Day Color", Just.centeredOnRow(1), config().selectedDayColor(), 6));
        others.draw(wid.toggle("Event Times", Just.centeredOnRow(2), config().drawEventTimes(), 7));

        return box;
    }

    private void themeBox(Canvas canvas) {
        canvas.rectangle(0, 0, canvas.width(), canvas.height() - 1, false);
        canvas.text("   Theme   ", Just.centeredOnRow(0), state.monthColorText(), state.monthColor());
        canvas.offsetCenteredMargin(1, 2, 5).draw(this::themeList);
    }

    private void themeList(Canvas canvas) {
        canvas.text("Latte",       Just.centeredOnRow(0));
        canvas.text("Frappe",      Just.centeredOnRow(1));
        canvas.text("Macchiato",   Just.centeredOnRow(2));
        canvas.text("Mocha",       Just.centeredOnRow(3));
        canvas.text("Transparent", Just.centeredOnRow(4));

        canvas.highlightBox(0, 0, canvas.width(), 1, Theme.Latte.text(),       Theme.Latte.background());
        canvas.highlightBox(0, 1, canvas.width(), 1, Theme.Frappe.text(),      Theme.Frappe.background());
        canvas.highlightBox(0, 2, canvas.width(), 1, Theme.Macchiato.text(),   Theme.Macchiato.background());
        canvas.highlightBox(0, 3, canvas.width(), 1, Theme.Mocha.text(),       Theme.Mocha.background());
        canvas.highlightBox(0, 4, canvas.width(), 1, Theme.Transparent.text(), Theme.Transparent.background());

        if(hover() < 5) {
            int selectedLine = hover();
            canvas.text("→", Just.leftOfRow(selectedLine));
            canvas.text("←", Just.rightOfRow(selectedLine));
        }
    }
}
