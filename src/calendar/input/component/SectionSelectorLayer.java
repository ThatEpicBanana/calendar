package calendar.input.component;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;

public class SectionSelectorLayer implements InputLayer {
    private int index;
    private Updater<Integer> callback;

    public SectionSelectorLayer(Updater<Integer> callback) {
        this.index = 0;
        this.callback = callback;
    }

    public LayerChange handle(Key character) {
        return LayerChange.keep(); // for now
    }

    public void exit() {
        this.callback.update(index);
    }
}
