package calendar.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

import calendar.input.layer.MonthInputLayer;
import calendar.state.State;

// handles all the input in the application
// it keeps track of each layer of the input - from the month to the popups
// then, it forwards all inputs to the top layer
public class Input {
    private Stack<InputLayer> layers;
    private State state;

    public Input(State state) {
        this.layers = new Stack<>();
        this.state = state;
        this.push(new MonthInputLayer(state));
    }

    private InputLayer current() {
        return layers.peek();
    }

    // handles a key
    // returns if the inputLoop should end
    private boolean handle(Key character) {
        // exit current layer if escape or q is pressed
        if(character.isEscape() && exit())
            return true;

        // exit current layer if requested
        LayerChange change = current().handle(character);
        if(change.exits() && exit()) return true;

        // changes the layer if requested
        InputLayer newLayer = change.newLayer();
        if(newLayer != null) {
            layers.push(newLayer);
            newLayer.start();
        }

        // keep handling new inputs
        return false;
    }

    public void push(InputLayer layer) {
        layers.add(layer);
        this.state.updateScreen();
    }

    // exits the current layer
    // returns if the inputLoop should end
    private boolean exit() {
        LayerType type = this.layers.peek().type();

        // make sure the popup removes correctly
        if(type == LayerType.Popup) {
            if(!state.screen.removePopup())
                return false;
            this.state.resetPopupHover();
        }

        this.layers.pop().exit();
        this.state.updateScreen();

        return this.layers.isEmpty();
    }

    public void inputLoop() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            while(true) {
                Key key = Key.next(reader);

                if(key != Key.UNKNOWN) {
                    state.resetError();

                    boolean exit = handle(key);
                    if(exit) break;
                }
            }
        } catch(IOException e) { e.printStackTrace(); }
    }
}
