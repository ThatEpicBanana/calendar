package calendar.input.component;

import calendar.input.InputLayer;
import calendar.input.Key;

public class TextBoxLayer implements InputLayer {
    private StringBuilder builder;
    private Updater<String> callback;

    public TextBoxLayer(Updater<String> callback) {
        this.builder = new StringBuilder();
        this.callback = callback;
    }

    public InputLayer handle(Key character) {
        return null; // for now
    }

    public void exit() {
        this.callback.update(builder.toString());
    }
}
