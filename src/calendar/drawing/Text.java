package calendar.drawing;

public class Text {
    // map of the basic light unicode box chars
    // each axis represents if the line is present in that direction
    //   up down left right
    // for example, a vertical line is [1][1][0][0] because
    //   only up and down are present
    public static final char[][][][] lightBoxChars;

    public static final char VERTICAL;
    public static final char HORIZONTAL;

    private static void add(
        int up,   int down, 
        int left, int right, 
        char val
    ) {
        lightBoxChars[up][down][left][right] = val;
    }

    static {
        lightBoxChars = new char[2][2][2][2];

        add(1,1,0,0, '│');
        add(0,0,1,1, '─');
        add(1,0,0,1, '┌');

        VERTICAL = lightBoxChars[1][1][0][0];
        HORIZONTAL = lightBoxChars[0][0][1][1];
    }
}
