package calendar.drawing.components;

import calendar.drawing.Canvas;
import calendar.drawing.color.Color;
import calendar.drawing.Drawable;
import calendar.drawing.Justification;

public class Grid implements Drawable {
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;

    private int rows;
    private int columns;

    public String[][] grid;
    public Color[][] foreground;
    public Color[][] background;
    private Justification justification;

    public Grid(int cellWidth, int cellHeight, int columns, int rows, Justification justification) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        this.width = Canvas.cellDimToFull(cellWidth, columns);
        this.height = Canvas.cellDimToFull(cellHeight, rows);

        this.rows = rows;
        this.columns = columns;

        this.grid = new String[columns][rows];
        this.justification = justification;

        this.foreground = new Color[columns][rows];
        this.background = new Color[columns][rows];
    }
    
    public int width() { return width; }
    public int height() { return height; }
    public int columns() { return columns; }
    public int rows() { return rows; }

    public Canvas draw() {
        Canvas canvas = Canvas.grid(cellWidth, cellHeight, columns, rows, false);

        for(int column = 0; column < columns; column++)
            for(int row = 0; row < rows; row++)
                if(grid[column][row] != null)
                    drawCell(canvas, column, row);

        return canvas;
    }

    private void drawCell(Canvas canvas, int column, int row) {
        int x = Canvas.cellToChar(column, cellWidth);
        int y = Canvas.cellToChar(row, cellHeight);

        int top = cellHeight - 1;

        this.justification.write(canvas, 
            grid[column][row], 
            x, y, 
            cellWidth, cellHeight, 
            top
        );

        canvas.highlightBox(x, y, cellWidth, cellHeight, foreground[column][row], background[column][row]);
    }
}
