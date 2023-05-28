package calendar.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Input {
    private Stack<InputLayer> layers;

    public Input() {
        this.layers = new Stack<>();
    }

    private InputLayer current() {
        return layers.peek();
    }

    // handles a key
    // returns if the inputLoop should end
    private boolean handle(Key character) {
        // exit current layer if escape or q is pressed
        if((character.isEscape() || character.toChar() == 'q') && exit())
            return true;

        // exit current layer if requested
        LayerChange change = current().handle(character);
        if(change.exits() && exit()) return true;

        // change layer if requested
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
    }

    // exits the current layer
    // returns if the inputLoop should end
    private boolean exit() {
        this.layers.pop().exit();
        return this.layers.isEmpty();
    }

    public void inputLoop() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            while(true) {
                int integer = reader.read();

                if(integer != -1) {
                    boolean exit = handle(new Key(integer));
                    if(exit) break;
                }
            }
        } catch(IOException e) { e.printStackTrace(); }
    }
}
