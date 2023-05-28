package calendar.input.component;

// defines some code for a component to call when it exits
public interface Updater<T> {
    void update(T with);
}
