package calendar.drawing.layers;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.Justification;
import calendar.drawing.components.Box;
import calendar.drawing.components.Grid;
import calendar.drawing.components.MultiBox;

public class Month extends MultiBox {
    private Box title;
    private Grid weekdays;
    private Grid month;

    private static final String[][] weekdaysFull = 
        { { "Sunday" }, { "Monday" }, { "Tuesday" }, { "Wednesday" }, { "Thursday" }, { "Friday" }, { "Saturday" } };
    private static final String[][] weekdaysSemi = 
        { { "Sun" }, { "Mon" }, { "Tue" }, { "Wed" }, { "Thu" }, { "Fri" }, { "Sat" } };
    private static final String[][] weekdaysShort = 
        { { "S" }, { "M" }, { "T" }, { "W" }, { "T" }, { "F" }, { "S" } };

    public Month(int cellWidth, int cellHeight, String header) {
        super(
            new Drawable[3],
            new int[]{
                // puts it in the middle of the third day
                (cellWidth + 1) * 2 + 1 + cellWidth / 2,
                0,
                0
            },
            new int[]{ 0, 2, 4 },
            Canvas.cellDimToFull(cellWidth, 7), 
            Canvas.cellDimToFull(cellHeight, 5) + 4
        );

        title = new Box(cellWidth * 2 + 3, 3, header, false, Justification.Middle);
        weekdays = new Grid(cellWidth, 1, 7, 1, Justification.Middle);
        month = new Grid(cellWidth, cellHeight, 7, 5, Justification.Right);

        weekdays.grid = sizedWeekdays(cellWidth);

        this.boxes[0] = title; this.boxes[1] = weekdays; this.boxes[2] = month;
    }

    private String[][] sizedWeekdays(int width) {
        if(width >= 11)
            return weekdaysFull;
        else if(width >= 5)
            return weekdaysSemi;
        else
            return weekdaysShort;
    }

//     public Canvas draw() {

//         return canvas;
//     }
}
