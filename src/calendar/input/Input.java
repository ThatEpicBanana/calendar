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

    private boolean handle(Key character) {
        if(character.isEscape() || character.toChar() == 'q')
            if(exit())
                return true;

        InputLayer newLayer = current().handle(character);
        if (newLayer != null)
            layers.push(newLayer);

        return false;
    }

    public void push(InputLayer layer) {
        layers.add(layer);
    }

    // exits the current layer
    // returns if the inputLoop should be exited
    private boolean exit() {
        this.layers.pop().exit();
        return this.layers.isEmpty();
    }

    public void inputLoop() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            while(true) {
                int integer = reader.read();

                if(handle(new Key(integer))) 
                    break;
            }
        } catch(IOException e) { e.printStackTrace(); }
    }
}
