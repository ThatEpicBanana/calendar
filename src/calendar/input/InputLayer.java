package calendar.input;

public interface InputLayer {
    // handles an input
    // returns a new InputLayer to switch to
    // or null to not switch
    LayerChange handle(Key character);

    // does anything to initialize
    default void start() {}

    // does any cleanup necessary
    default void exit() {}
}
