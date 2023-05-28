package calendar.input;

public interface InputLayer {
    // handles an input
    // returns a new InputLayer to switch to
    // or null to not switch
    InputLayer handle(Key character);

    // does any cleanup necessary
    default void exit() {}
}
