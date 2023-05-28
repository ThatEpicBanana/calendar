package calendar.input.component;

import calendar.input.InputLayer;
import calendar.input.Key;

public class SectionSelectorLayer implements InputLayer {
    private int index;
    private Updater<Integer> callback;

    public SectionSelectorLayer(Updater<Integer> callback) {
        this.index = 0;
        this.callback = callback;
    }

    public InputLayer handle(Key character) {
        return null; // for now
    }

    public void exit() {
        this.callback.update(index);
    }
}
