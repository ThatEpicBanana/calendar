package calendar.state;

import java.util.Stack;

public class Input {
    private Stack<InputLayer> layers;

    public Input() {
        this.layers = new Stack<>();
        layers.push(new MonthInput());
    }

    private InputLayer current() {
        return layers.peek();
    }

    public void handleInput(KeyEvent event) {
        InputLayer newLayer = current().handleInput(command);
        if (newLayer != null) {
            layers.push(newLayer);
        }
    }
}

interface InputLayer {
    InputLayer handleInput(KeyEvent event);
}

public class MonthInput implements InputLayer {
    public InputLayer handleInput(char command) {
        switch (command) {
            case 'a':
                return new AddInput();
            case 's':
                return new SectionInput();
            case 'j':
                return null; // j vim moment //
            case 'k':
                return null; // k vim moment //
            default:
                return null;
        }
    }
}

