package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.component.TextBoxLayer;
import calendar.state.State;
import calendar.storage.Section;

public class SectionInputLayer implements InputLayer {
    private State state;

    public SectionInputLayer(State state) {
        this.state = state;
    }

    public int line() { return state.popupHover(); }
    public int maxSession() { return Math.max(state.calendar.sections().size() - 1, 0); }
    public Section section() { return state.calendar.sections().get(line()); }

    public LayerChange handle(Key character) {
        if(character.isEnter()) {
            Section section = section();
            InputLayer newLayer = new TextBoxLayer(state, section().title(), value -> section.setTitle(value));
            return LayerChange.switchTo(newLayer);
        }

        if(!character.isAlphaNumeric()) return LayerChange.keep();

        switch(character.toLowerCase()) {
            case 'j':
                down(); break;
            case 'k':
                up(); break;
            case 'h':
                previousHighlight(); break;
            case 'l':
                nextHighlight(); break;
            case 'r':
                remove(); break;
        }

        return LayerChange.keep();
    }

    private void remove() {
        state.calendar.removeSection(line());
        if(line() > maxSession()) state.setPopupHover(maxSession());
    }

    private void up() {
        state.movePopupHover(-1, 0, maxSession());
    }

    private void down() {
        state.movePopupHover(1, 0, maxSession());
    }

    private void nextHighlight() { section().addColor(1); }
    private void previousHighlight() { section().addColor(-1); }
}
