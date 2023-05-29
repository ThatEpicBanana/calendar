package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;

public class HelpLayer implements InputLayer {
    public LayerChange handle(Key character) {
        if(character.toChar() == 'q') return LayerChange.exit();

        return LayerChange.keep();
    }

    public LayerType type() { return LayerType.Popup; }
}
