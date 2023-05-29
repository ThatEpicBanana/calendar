package calendar.input.layer;

import calendar.drawing.color.Theme;
import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.state.Settings;
import calendar.state.State;

public class PreferencesLayer implements InputLayer {
    private State state;

    public PreferencesLayer(State state) {
        this.state = state;
    }

    private int line() { return state.popupHover(); }
    private Settings settings() { return state.settings; }

    public LayerChange handle(Key character) {
        if(character.toChar() == 'q' || character.toChar() == 'p') return LayerChange.exit();

        // (q) exit
        if(character.toChar() == 'q') 
            return LayerChange.exit();

        // (enter) edit
        if(character.isEnter()) {
            switch(line()) {
                case 0: switchTheme(Theme.Latte); break;
                case 1: switchTheme(Theme.Frappe); break;
                case 2: switchTheme(Theme.Macchiato); break;
                case 3: switchTheme(Theme.Mocha); break;
                case 4: switchTheme(Theme.Transparent); break;
                case 5: toggleColorfulMonths(); break;
            }
        }

        if(character.isUp()) 
            state.movePopupHover(-1, 0, 6);
        else if(character.isDown()) 
            state.movePopupHover(1, 0, 6);
        else if(character.isLeft())
            settings().changeSelectedDayColor(-1);
        else if(character.isRight())
            settings().changeSelectedDayColor(1);

        return LayerChange.keep();
    }

    private void toggleColorfulMonths() {
        settings().toggleColorfulMonths();
    }

    private void switchTheme(Theme theme) {
        settings().setTheme(theme);
    }

    public LayerType type() { return LayerType.Popup; }
}
