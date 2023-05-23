package calendar.drawing;

public interface Drawable {
    // 2D array of characters
    // [x][y]
    public char[][] draw();
    public int width();
    public int height();

    public default void print() {
        char[][] chars = draw();

        for(int y = 0; y < height(); y++) {
            for(int x = 0; x < width(); x++)
                System.out.print(chars[x][y]);
            System.out.println();
        }
    }
}
