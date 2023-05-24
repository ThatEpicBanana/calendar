package calendar.drawing;

public class Shapes {
    // creates an empty canvas filled with ' '
    public static char[][] empty(int width, int height) {
        char[][] canvas = new char[width][height];

        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                canvas[x][y] = ' ';

        return canvas;
    }

    // creates a rectangle filling a canvas of width and height
    public static char[][] rectangle(int width, int height, boolean heavy) {
        char[][] canvas = empty(width, height);
        char[][][][] chars = heavy ? BoxChars.heavy : BoxChars.light;

        int left = 0;
        int right = width - 1;
        int top = 0;
        int bottom = height - 1;

        canvas[left][top] = chars[0][1][0][1]; // ┌
        canvas[right][top] = chars[0][1][1][0]; // ┐
        canvas[left][bottom] = chars[1][0][0][1]; // └
        canvas[right][bottom] = chars[1][0][1][0]; // ┘
        
        vertical(canvas, left,  top + 1, bottom - 1, chars);
        vertical(canvas, right, top + 1, bottom - 1, chars);

        horizontal(canvas, top, left + 1, right - 1, chars);
        horizontal(canvas, bottom, left + 1, right - 1, chars);

        return canvas;
    }

    private static void vertical(char[][] canvas, int x, int starty, int endy, char[][][][] chars) {
        char vertical   = chars[1][1][0][0]; // │
         
        for(int y = starty; y <= endy; y++) {
            canvas[x][y] = vertical;
        }
    }

    private static void horizontal(char[][] canvas, int y, int startx, int endx, char[][][][] chars) {
        char horizontal = chars[0][0][1][1]; // ─
         
        for(int x = startx; x <= endx; x++) {
            canvas[x][y] = horizontal;
        }
    }

    public static int cellLengthToGrid(int cell, int amount) {
        return (cell + 1) * amount + 1;
    }

    public static char[][] grid(int cellWidth, int cellHeight, int columns, int rows, boolean heavy) {
        char[][][][] chars = heavy ? BoxChars.heavy : BoxChars.light;

        int width = cellLengthToGrid(cellWidth, columns);
        int height = cellLengthToGrid(cellHeight, rows);

        int left = 0;
        int right = width - 1;
        int top = 0;
        int bottom = height - 1;

        int xdiff = cellWidth + 1;
        int ydiff = cellHeight + 1;

        char[][] canvas = rectangle(width, height, heavy);

        
        for(int x = xdiff; x < width - 1; x += xdiff) {
            vertical(canvas, x, 1, height - 2, chars); // ─
            canvas[x][top] = chars[0][1][1][1]; // ┬
            canvas[x][bottom] = chars[1][0][1][1]; // ┴
        }
        
        for(int y = ydiff; y < height - 1; y += ydiff) {
            horizontal(canvas, y, 1, width - 2, chars); // │
            canvas[left][y] = chars[1][1][0][1]; // ├
            canvas[right][y] = chars[1][1][1][0]; // ┤
        }

        for(int x = xdiff; x < width - 1; x += xdiff)
            for(int y = ydiff; y < height - 1; y += ydiff)
                canvas[x][y] = chars[1][1][1][1]; // ┼

        return canvas;
    }

}
