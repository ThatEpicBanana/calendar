package calendar.state.layer;

import calendar.state.State;
import calendar.storage.EditingEvent;

public class AddEventLayer extends SelectionsLayer {
    public EditingEvent event;

    public AddEventLayer(State state) {
        super(state);
        this.event = state.calendar.createDefaultEvent();
    }
}
