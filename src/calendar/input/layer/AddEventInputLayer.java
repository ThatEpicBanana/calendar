package calendar.input.layer;

import calendar.input.InputLayer;
import calendar.input.Key;
import calendar.input.LayerChange;
import calendar.input.LayerType;
import calendar.input.component.SectionSelectorLayer;
import calendar.input.component.TextBoxLayer;
import calendar.state.State;
import calendar.state.layer.AddEventLayer;
import calendar.storage.EditingEvent;

// the input layer for the add event popup
// allows the user to add an event, nothing else
public class AddEventInputLayer implements InputLayer {
    private State state;
    private AddEventLayer layer;

    private boolean setTitle = false;

    // hovers:
    // - 0 title
    // - 1 start
    // - 2 end
    // - 3 section
    // - 4 confirm (left)
    // - 5 abandon (right)

    private int selection() { return layer.selection(); }

    public AddEventInputLayer(State state, AddEventLayer layer) {
        this.state = state;
        this.layer = layer;
        // actually goes from 0-5,
        // but these are the bounds of the middle lines
        layer.setBounds(0, 4);
    }

    private EditingEvent event() { return layer.event; }

    public LayerChange handle(Key character) {
        if(character.toLowerCase() == 'q' || character.toLowerCase() == 'a') 
            return LayerChange.exit();

        if(character.isUp()) up();
        else if(character.isDown()) down();
        else if(character.isLeft()) left();
        else if(character.isRight()) right();

        if(character.isEnter()) {
            switch(selection()) {
                case 0: return editTitle();
                case 1: return editStart();
                case 2: return editEnd();
                case 3: return editSection();
                case 4: return saveSection();
                case 5: return LayerChange.exit();
            }
        }

        if(character.toLowerCase() == '?')
            return toggleHelp();

        return LayerChange.keep(); // for now
    }

    private void up() {
        if(selection() > 3) layer.setSelection(3);
        else layer.prevSelection();;
    }

    private void down() {
        layer.nextSelection();
    }

    private void left() { layer.setSelection(4); }
    private void right() { layer.setSelection(5); }

    private LayerChange editTitle() {
        EditingEvent event = event();
        InputLayer newLayer = new TextBoxLayer(layer, setTitle ? event().title() : "", value -> event.setTitle(value));
        setTitle = true; // title for the first edit is empty
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange editStart() {
        EditingEvent event = event();
        InputLayer newLayer = new TextBoxLayer(
            layer, event.start(),
            value -> event.setStart(value),
            value -> event.setStartChecked(value, state)
        );
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange editEnd() {
        EditingEvent event = event();
        InputLayer newLayer = new TextBoxLayer(
            layer, event.end(),
            value -> event.setEnd(value),
            value -> event.setEndChecked(value, state)
        );
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange editSection() {
        EditingEvent event = event();
        InputLayer newLayer = new SectionSelectorLayer(state, layer, index -> event.setSection(index));
        return LayerChange.switchTo(newLayer);
    }

    private LayerChange saveSection() {
        state.calendar.addEvent(event());
        return LayerChange.exit();
    }

    private static String[] help = new String[]{
        "Add an event within",
        "  the specified",
        "  timeframe and",
        "  section",
        "",
        "Time Frame:",
        "  Currently, only",
        "  text is supported",
        "  ex:",
        "    1/1/23 12p",
        "    7.1.23 6:01",
    };

    private LayerChange toggleHelp() {
        state.toggleHelp(help);
        return LayerChange.keep();
    }


    public LayerType type() { return LayerType.Popup; }
}
