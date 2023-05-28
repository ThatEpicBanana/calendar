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
        // (q) exit
        if(character.toChar() == 'q') 
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
        InputLayer newLayer = new TextBoxLayer(state, section().title(), value -> section.setTitle(value));
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange remove() {
        state.calendar.removeSection(line());
        if(line() > maxSession()) state.setPopupHover(maxSession());
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

    private void nextHighlight() { section().addColor(1); }
    private void previousHighlight() { section().addColor(-1); }
}
