package calendar.drawing;

import static calendar.drawing.Text.heavyBoxChars;
import static calendar.drawing.Text.lightBoxChars;

public class Shapes {
    public static char[][] empty(int width, int height) {
        char[][] canvas = new char[width][height];
        for(int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                canvas[x][y] = ' ';
        return canvas;
    }

    public static char[][] rectangle(int width, int height, boolean heavy) {
        char[][] canvas = empty(width, height);
        char[][][][] chars = heavy ? heavyBoxChars : lightBoxChars;

        int left = 0;
        int right = width - 1;
        int top = 0;
        int bottom = height - 1;

        canvas[left][top] = chars[0][1][0][1]; // ┌
        canvas[right][top] = chars[0][1][1][0]; // ┐
        canvas[left][bottom] = chars[1][0][0][1]; // └
        canvas[right][bottom] = chars[1][0][1][0]; // ┘
        
        char vertical   = chars[1][1][0][0]; // │
        char horizontal = chars[0][0][1][1]; // ─

        for(int x = 1; x < width - 1; x++) {
            canvas[x][top] = horizontal;
            canvas[x][bottom] = horizontal;
        }

        for(int y = 1; y < height - 1; y++) {
            canvas[left][y] = vertical;
            canvas[right][y] = vertical;
        }

        return canvas;
    }

}
