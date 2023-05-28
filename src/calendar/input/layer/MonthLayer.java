package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.state.State;

public class MonthLayer implements InputLayer {
    private State state;

    public MonthLayer(State state) {
        this.state = state;
    }

    public InputLayer handle(Key character) {
        return null; // for now
    }
}
