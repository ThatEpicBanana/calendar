package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.input.component.TextBoxLayer;
import calendar.state.State;
import calendar.storage.Section;

// the input layer for the section popup
// allows for the user to add, remove, and change sections
public class SectionInputLayer implements InputLayer {
    private State state;

    public SectionInputLayer(State state) {
        this.state = state;
    }

    private boolean hasSections() { return state.calendar.sections().size() > 0; }
    private int line() { return state.popupHover(); }
    private int maxSession() { return Math.max(state.calendar.sections().size() - 1, 0); }
    private Section section() {
        if(!state.calendar.sections().isEmpty())
            return state.calendar.sections().get(line());
        else
            return null;
    }

    public LayerChange handle(Key character) {
        // (q) exit
        if(character.toLowerCase() == 'q' || character.toLowerCase() == 's') 
            return LayerChange.exit();

        // (enter) edit
        if(character.isEnter()) 
            return edit();

        switch(character.toLowerCase()) {
            case 'r':
                return remove();
            case 'a':
                return add();
        }

        if(character.isUp()) 
            up();
        else if(character.isDown()) 
            down();
        else if(character.isLeft()) 
            previousHighlight();
        else if(character.isRight()) 
            nextHighlight();

        return LayerChange.keep();
    }

    private LayerChange edit() {
        Section section = section();
        if(section == null) return LayerChange.keep();

        InputLayer newLayer = new TextBoxLayer(state, section().title(), value -> section.setTitle(value));
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange remove() {
        if(hasSections()) {
            state.calendar.removeSection(line());
            if(line() > maxSession()) state.setPopupHover(maxSession());
        }

        return LayerChange.keep();
    }

    private LayerChange add() {
        Section section = state.calendar.addSection("New Section", 0);
        state.setPopupHover(maxSession());
        InputLayer newLayer = new TextBoxLayer(state, "", value -> section.setTitle(value));
        return LayerChange.switchTo(newLayer);
    }

    private void up() {
        state.movePopupHover(-1, 0, maxSession());
    }

    private void down() {
        state.movePopupHover(1, 0, maxSession());
    }

    private void nextHighlight() {
        if(section() != null)
            section().addColor(1);
    }

    private void previousHighlight() {
        if(section() != null)
            section().addColor(-1);
    }

    public LayerType type() { return LayerType.Popup; }
}
