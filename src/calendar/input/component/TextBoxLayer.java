package calendar.input.component;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.state.State;

// An InputLayer for inputting some text
public class TextBoxLayer implements InputLayer {
    // a callback that gets run any time the layer gets changed
    // for example: in order to update the selection and the screen
    private Updater<String> callback;
    // a callback that gets run at the end of editing
    private Updater<String> finalizer;

    // iteratively builds the string
    private StringBuilder builder;
    private State state;

    public TextBoxLayer(State state, String start, Updater<String> callback) {
        this(state, start, callback, callback);
    }

    public TextBoxLayer(State state, String start, Updater<String> callback, Updater<String> finalize) {
        this.builder = new StringBuilder(start);
        this.callback = callback;
        this.finalizer = finalize;
        this.state = state;
    }

    public LayerChange handle(Key character) {
        if(character.isEnter()) return LayerChange.exit();

        if(character.isAscii())
            builder.append(character.toChar());

        if(character.isBackspace() && builder.length() > 0)
            builder.deleteCharAt(builder.length() - 1);

        if(character.isSpace())
            builder.append(' ');

        this.callback.update(builder.toString());

        return LayerChange.keep(); // for now
    }

    public void start() {
        state.startEditingHover();
    }

    public void exit() {
        this.finalizer.update(builder.toString());
        state.endEditingHover();
    }

    public LayerType type() { return LayerType.Component; }
}
