package calendar.input.component;

import java.util.List;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.state.State;
import calendar.storage.Calendar;
import calendar.storage.Section;

public class SectionSelectorLayer implements InputLayer {
    private int index;
    private State state;
    private Updater<Integer> callback;

    private List<Section> sections() { return state.calendar.sections(); }
    private int maxIndex() { return sections().size(); }

    public SectionSelectorLayer(State state, Updater<Integer> callback) {
        this.index = 0;
        this.state = state;
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
        state.startEditingHover();
    }

    public void exit() {
        this.callback.update(index);
        state.endEditingHover();
    }

    public LayerType type() { return LayerType.Component; }
}
