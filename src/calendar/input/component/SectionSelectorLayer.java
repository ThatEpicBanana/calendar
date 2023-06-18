package calendar.input.component;

import java.util.List;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.state.State;
import calendar.state.layer.SelectionsLayer;
import calendar.storage.Calendar;
import calendar.storage.Section;

// An input layer that handles picking a certain section
// Generally, popups will create this layer to choose it
public class SectionSelectorLayer implements InputLayer {
    private int index;
    private State state;
    private SelectionsLayer selector;
    // a callback that gets run any time the layer gets changed
    // for example: in order to update the selection and the screen
    private Updater<Integer> callback;

    private List<Section> sections() { return state.calendar.sections(); }
    private int maxIndex() { return sections().size(); }

    public SectionSelectorLayer(State state, SelectionsLayer selector, Updater<Integer> callback) {
        this.index = 0;
        this.state = state;
        this.selector = selector;
        this.callback = callback;
    }

    public LayerChange handle(Key character) {
        if(character.isEnter()) return LayerChange.exit();

        if(character.isLeft())
            this.index = trueMod(index - 1, maxIndex());
        else if(character.isRight())
            this.index = trueMod(index + 1, maxIndex());
        else
            return LayerChange.exit();

        this.callback.update(index);

        return LayerChange.keep();
    }

    // java mod can return negative values
    // this fixes that
    public int trueMod(int a, int by) {
        return ((a % by) + by) % by;
    }

    public void start() {
        selector.startEditing();
    }

    public void exit() {
        this.callback.update(index);
        selector.endEditing();
    }

    public LayerType type() { return LayerType.Component; }
}
