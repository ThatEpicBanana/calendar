package calendar.drawing;

public interface Drawable {
    public Canvas draw();
    public int width();
    public int height();

    public default void print() {
        draw().print();
    }
}
