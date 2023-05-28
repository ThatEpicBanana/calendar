package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.state.State;

public class MonthLayer implements InputLayer {
    private State state;

    public MonthLayer(State state) {
        this.state = state;
    }

    public LayerChange handle(Key character) {
        return LayerChange.keep(); // for now
    }
}
