package calendar.input.component;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.state.State;

public class TextBoxLayer implements InputLayer {
    private StringBuilder builder;
    private Updater<String> callback;
    private State state;

    public TextBoxLayer(State state, String start, Updater<String> callback) {
        this.builder = new StringBuilder(start);
        this.callback = callback;
        this.state = state;
    }

    public LayerChange handle(Key character) {
        if(character.isEnter()) return LayerChange.exit();

        // todo: accept all ascii
        if(character.isAscii())
            builder.append(character.toChar());

        if(character.isBackspace() && builder.length() > 0)
            builder.deleteCharAt(builder.length() - 1);

        if(character.isSpace())
            builder.append(' ');

        update();

        return LayerChange.keep(); // for now
    }

    private void update() {
        this.callback.update(builder.toString());
    }

    public void start() {
        state.startEditingHover();
    }

    public void exit() {
        update();
        state.endEditingHover();
    }
}
