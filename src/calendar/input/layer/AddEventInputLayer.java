package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.input.component.SectionSelectorLayer;
import calendar.input.component.TextBoxLayer;
import calendar.state.State;
import calendar.storage.EditingEvent;

// the input layer for the add event popup
// allows the user to add an event, nothing else
public class AddEventInputLayer implements InputLayer {
    private State state;
    private EditingEvent event;

    private boolean setTitle = false;

    // hovers:
    // - 0 title
    // - 1 start
    // - 2 end
    // - 3 section
    // - 4 confirm (left)
    // - 5 abandon (right)

    private int hover() { return state.popupHover(); }

    public AddEventInputLayer(State state, EditingEvent event) {
        this.state = state;
        this.event = event;
    }

    public LayerChange handle(Key character) {
        if(character.toChar() == 'q') return LayerChange.exit();

        if(character.isUp()) up();
        else if(character.isDown()) down();
        else if(character.isLeft()) left();
        else if(character.isRight()) right();

        if(character.isEnter()) {
            switch(hover()) {
                case 0: return editTitle();
                case 1: return editStart();
                case 2: return editEnd();
                case 3: return editSection();
                case 4: return saveSection();
                case 5: return LayerChange.exit();
            }
        }

        return LayerChange.keep(); // for now
    }

    private void up() {
        if(hover() > 3) state.setPopupHover(3);
        else state.movePopupHover(-1, 0, 4);
    }

    private void down() {
        state.movePopupHover(1, 0, 4);
    }

    private void left() { state.setPopupHover(4); }
    private void right() { state.setPopupHover(5); }

    private LayerChange editTitle() {
        // title for the first edit is empty
        InputLayer newLayer = new TextBoxLayer(state, setTitle ? event.title() : "", value -> event.setTitle(value));
        setTitle = true;
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange editStart() {
        InputLayer newLayer = new TextBoxLayer(
            state, event.start(),
            value -> event.setStart(value),
            value -> event.setStartChecked(value, state)
        );
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange editEnd() {
        InputLayer newLayer = new TextBoxLayer(
            state, event.end(),
            value -> event.setEnd(value),
            value -> event.setEndChecked(value, state)
        );
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange editSection() {
        InputLayer newLayer = new SectionSelectorLayer(state, index -> event.setSection(index));
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange saveSection() {
        state.calendar.addEvent(event);
        return LayerChange.exit();
    }

    public LayerType type() { return LayerType.Popup; }
}
