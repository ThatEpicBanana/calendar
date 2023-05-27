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

    public void handle(char character) {
        InputLayer newLayer = current().handle(character);
        if (newLayer != null)
            layers.push(newLayer);
    }

    public void push(InputLayer layer) {
        layers.add(layer);
    }

    public void inputLoop() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            while(true) {
                char character = (char) reader.read();
                if(character == 'q') break;
                handle(character);
            }
        } catch(IOException e) { e.printStackTrace(); }
    }
}
