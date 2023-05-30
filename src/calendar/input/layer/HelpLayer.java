package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;

// an input layer for any help layer
// very simple, just exits when q or ? is pressed
public class HelpLayer implements InputLayer {
    public LayerChange handle(Key key) {
        char character = key.toChar();
        if(character == 'q' || character == '?') return LayerChange.exit();

        return LayerChange.keep();
    }

    public LayerType type() { return LayerType.Popup; }
}
