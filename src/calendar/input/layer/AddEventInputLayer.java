package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.state.State;
import calendar.storage.Event;

public class AddEventInputLayer implements InputLayer {
    private State state;
    private Event event;

    public AddEventInputLayer(State state, Event event) {
        this.state = state;
        this.event = event;
    }

    public InputLayer handle(Key character) {
        return null; // for now
    }
}
