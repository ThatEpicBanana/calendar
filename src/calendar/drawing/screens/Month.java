package calendar.drawing.screens;

import calendar.drawing.Canvas;
import calendar.drawing.Justification;
import calendar.drawing.components.Grid;
import calendar.drawing.components.MultiGrid;

public class Month extends MultiGrid {
    public Month(int cellWidth, int cellHeight) {
        super(
            new Grid[3],
            new int[]{
                (cellWidth + 1) * 2 + 1 + cellWidth / 2,
                0,
                0
            },
            new int[]{ 0, 2, 4 },
            Canvas.cellDimToFull(cellWidth, 7), 
            Canvas.cellDimToFull(cellHeight, 5) + 4
        );

        this.grids[0] = new Grid(cellWidth * 2 + 1, 1, 1, 1, Justification.Middle);
        this.grids[1] = new Grid(cellWidth, 1, 7, 1, Justification.Middle);
        this.grids[2] = new Grid(cellWidth, cellHeight, 7, 5, Justification.Right);
    }

//     public Canvas draw() {

//         return canvas;
//     }
}
