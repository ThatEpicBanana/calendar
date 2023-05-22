package calendar;

public class Grid {
    private int rows;
    private int columns;
    private int[][] grid;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new int[rows][columns];
    }

    public void setValue(int row, int column, int value) {
        if (isValidPosition(row, column)) {
            grid[row][column] = value;
        } else {
            System.out.println("Invalid position!");
        }
    }

    public int getValue(int row, int column) {
        if (isValidPosition(row, column)) {
            return grid[row][column];
        } else {
            System.out.println("Invalid position!");
            return -1; // or any other suitable default value
        }
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }
    
    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
