package calendar.drawing.components;

public class Grid {
    private int height;
    private int width;
    private String[][] grid;

    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new String[width][height];
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
        return y >= 0 && y < height && x >= 0 && x < width;
    }
    
    public void printGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
    }
}
