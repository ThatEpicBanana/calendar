package calendar.input;

import calendar.state.State;
import calendar.storage.Section;

public class SectionInputLayer implements InputLayer {
    private State state;

    public SectionInputLayer(State state) {
        this.state = state;
    }

    public int line() { return state.popupLine(); }
    public int maxSession() { return state.calendar.sections().size() - 1; }
    public Section section() { return state.calendar.sections().get(line()); }

    public InputLayer handle(Key character) {
        if(!character.isAlphaNumeric()) return null;

        switch(character.toLowerCase()) {
            case 'j':
                down(); return null;
            case 'k':
                up(); return null;
            case 'h':
                previousHighlight(); return null;
            case 'l':
                nextHighlight(); return null;
            default:
                return null;
        }
    }

    private void up() {
        state.movePopupLineBounded(-1, 0, maxSession());
    }

    private void down() {
        state.movePopupLineBounded(1, 0, maxSession());
    }

    private void nextHighlight() { section().addColor(1); }
    private void previousHighlight() { section().addColor(-1); }
}
