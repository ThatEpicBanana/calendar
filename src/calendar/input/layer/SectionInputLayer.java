package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.input.component.TextBoxLayer;
import calendar.state.State;
import calendar.state.layer.SelectionsLayer;
import calendar.storage.Section;

// the input layer for the section popup
// allows for the user to add, remove, and change sections
public class SectionInputLayer implements InputLayer {
    private State state;
    private SelectionsLayer selector;

    public SectionInputLayer(State state, SelectionsLayer selector) {
        this.state = state;
        this.selector = selector;

        updateBounds();
    }

    private boolean hasSections() { return state.calendar.sections().size() > 0; }
    private int line() { return selector.selection(); }
    private int maxSession() { return Math.max(state.calendar.sections().size() - 1, 0); }
    private Section section() {
        if(!state.calendar.sections().isEmpty())
            return state.calendar.sections().get(line());
        else
            return null;
    }

    private void updateBounds() {
        this.selector.setBounds(0, maxSession());
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

        InputLayer newLayer = new TextBoxLayer(selector, section().title(), value -> section.setTitle(value));
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange remove() {
        if(hasSections()) {
            state.calendar.removeSection(line());
            if(line() > maxSession()) selector.setSelection(maxSession());
        }

        return LayerChange.keep();
    }

    private LayerChange add() {
        Section section = state.calendar.addSection("New Section", 0);
        updateBounds();
        selector.setSelection(maxSession());
        InputLayer newLayer = new TextBoxLayer(selector, "", value -> section.setTitle(value));
        return LayerChange.switchTo(newLayer);
    }

    private void up() {
        selector.prevSelection();
    }

    private void down() {
        selector.nextSelection();
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
