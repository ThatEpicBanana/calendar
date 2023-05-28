package calendar.input.component;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.state.State;

public class TextBoxLayer implements InputLayer {
    private StringBuilder builder;
    private Updater<String> callback;
    private Updater<String> finalizer;
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
}
