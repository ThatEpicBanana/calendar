package calendar.input;

// a layer of input
// manages all the input for a screen, 
// popup, or menu component (such as a text box)
public interface InputLayer {
    // handles an input
    // returns what Input should do after handling,
    // keep the current layer, exit it, or start a new one
    LayerChange handle(Key character);

    // does anything to initialize
    default void start() {}

    // does any cleanup necessary
    default void exit() {}

    LayerType type();
}
