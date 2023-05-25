package calendar.drawing;

public interface Drawable {
    public Canvas draw();
    public int width();
    public int height();

    public default void print() {
        Canvas chars = draw();

        for(int y = 0; y < chars.height(); y++) {
            for(int x = 0; x < chars.width(); x++)
                System.out.print(chars.text[x][y]);
            System.out.println();
        }
    }
}
