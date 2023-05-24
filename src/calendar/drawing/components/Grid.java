package calendar.drawing.components;

import calendar.drawing.Drawable;
import calendar.drawing.Shapes;

public class Grid implements Drawable {
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;

    private int rows;
    private int columns;

    private String[][] grid;
    private Justification justification;

    public Grid(int cellWidth, int cellHeight, int columns, int rows, Justification justification) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        this.width = Shapes.cellLengthToGrid(cellWidth, columns);
        this.height = Shapes.cellLengthToGrid(cellHeight, columns);

        this.rows = rows;
        this.columns = columns;

        this.grid = new String[columns][rows];
        this.justification = justification;
    }


    public void setValue(int x, int y, String value) {
        if (isValidPosition(x, y)) {
            grid[x][y] = value;
        } else {
            System.out.println("Invalid position!");
        }
    }

    public String getValue(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[x][y];
        } else {
            System.out.println("Invalid position!");
            return null; // or any other suitable default value
        }
    }

    private boolean isValidPosition(int x, int y) {
        return y >= 0 && y < rows && x >= 0 && x < columns;
    }
    
    public int width() { return width; }
    public int height() { return height; }

    public char[][] draw() {
        return Shapes.grid(cellWidth, cellHeight, columns, rows, false);
    }

    // TODO: replace in Canvas
    public static enum Justification {
        BottomRight,
        Middle;

        public char[][] write(String string, int width, int height) {
            switch(this) {
                case BottomRight:
                    return bottomRight(string, width, height);
                case Middle:
                    return middle(string, width, height);
                default:
                    // wtf how
                    return null;
            }
        }

        private char[][] bottomRight(String string, int width, int height) {
            char[][] canvas = Shapes.empty(width, height);

            int length = string.length();
            int bottom = height - 1;
            int right = width - 1;

            if(length < width - 2) {
                for(int i = 0; i < length; i++) {
                    char letter = string.charAt(i);
                    int offset = length - i;

                    canvas[right - 1 - offset][bottom] = letter;
                }
            } else {
                // TODO: implement
            }

            return canvas;
        }

        private char[][] middle(String string, int width, int height) {
            char[][] canvas = Shapes.empty(width, height);

            return canvas;
        }
    }
}
