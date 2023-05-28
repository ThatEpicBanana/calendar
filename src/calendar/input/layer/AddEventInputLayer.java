package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.state.State;
import calendar.storage.Event;

public class AddEventInputLayer implements InputLayer {
    private State state;
    private Event event;

    // hovers:
    // - 0 title
    // - 1 start
    // - 2 end
    // - 3 section
    // - 4 confirm (left)
    // - 5 abandon (right)

    public int hover() { return state.popupHover(); }

    public AddEventInputLayer(State state, Event event) {
        this.state = state;
        this.event = event;
    }

    public LayerChange handle(Key character) {
        return LayerChange.keep(); // for now
    }
}
