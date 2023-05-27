package calendar.input;

public interface InputLayer {
    // handles an input
    // returns a new InputLayer to switch to
    // or null to not switch
    InputLayer handle(char character);
}
