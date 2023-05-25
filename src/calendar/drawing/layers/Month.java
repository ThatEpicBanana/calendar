package calendar.drawing.layers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import calendar.drawing.Canvas;
import calendar.drawing.Color;
import calendar.drawing.Drawable;
import calendar.drawing.Justification;
import calendar.drawing.components.Box;
import calendar.drawing.components.Grid;
import calendar.drawing.components.MultiBox;
import calendar.state.State;

public class Month extends MultiBox {
    private int cellWidth;
    private int cellHeight;

    private State state;

    private Box title;
    private Grid weekdays;
    private Grid month;

    private static final int WEEKS = 5;

    public Month(State state, int cellWidth, int cellHeight) {
        super(
            new Drawable[3],
            // the first box is the title, in the middle of the third day
            new int[3],
            new int[]{ 0, 2, 4 },
            Canvas.cellDimToFull(cellWidth, 7), 
            Canvas.cellDimToFull(cellHeight, WEEKS) + 4
        );

        this.state = state;

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        this.initTitle();
        this.initWeek();
        this.initMonth();
    }

    private LocalDate date() { return state.date(); }

    private void initTitle() {
        // in the middle of the third day
        int x = Canvas.cellDimToFull(cellWidth, 2) + cellWidth / 2;
        this.boxx[0] = x;

        int width = cellWidth * 2 + 3;
        int height = 3;

        String name = date().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date().getYear();

        this.boxes[0] = title = new Box(width, height, name, false, Justification.Middle);
    }

    private void initWeek() {
        // far to the left
        int x = 0;
        this.boxx[1] = x;
        
        // 1-height single row
        int weekCellHeight = 1;
        int columns = 7, rows = 1;

        this.boxes[1] = weekdays = new Grid(cellWidth, weekCellHeight, columns, rows, Justification.Middle);

        // add the day names
        for(int day = 0; day < 7; day++)
            weekdays.grid[day][0] = DayOfWeek.of(day + 1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    private void initMonth() {
        // far to the left
        int x = 0;
        this.boxx[2] = x;
        
        // 1-height single row
        int columns = 7, rows = WEEKS;

        this.boxes[2] = month = new Grid(cellWidth, cellHeight, columns, rows, Justification.Right);

        // add the day numbers
        this.initDays();
    }

    private void initDays() {
        // current month
        LocalDate current = date().withDayOfMonth(1);
        int length = current.lengthOfMonth();
        int startDay = current.getDayOfWeek().getValue(); // Sunday is 1 so subtract

        // previous month
        LocalDate previous = current.minusMonths(1);
        int previousLength = previous.lengthOfMonth();

        // this is wacky, it's the offset from the start day of the month going in the past
        for(int offset = 1; startDay - offset >= 0; offset++) {
            int day = previousLength - (offset - 1) - 1;
            int gridDay = startDay - offset;
            setOffDay(day, gridDay);
        }

        // current month
        for(int day = 0; day < length; day++) {
            int dayAdjusted = day + startDay;
            setDay(day, dayAdjusted);
        }

        int nextStartDay = startDay + length;

        // next month
        for(int day = 0; day + nextStartDay < 7 * WEEKS; day++) {
            int dayAdjusted = day + nextStartDay;
            setOffDay(day, dayAdjusted);
        }
    }

    private void setDay(int day, int gridDay) {
        int week = gridDay / 7;
        int dayOfWeek = gridDay % 7;

        this.month.grid[dayOfWeek][week] = day + 1 + "";
    }

    private void setOffDay(int day, int gridDay) {
        int week = gridDay / 7;
        int dayOfWeek = gridDay % 7;

        this.month.grid[dayOfWeek][week] = day + 1 + "";
        this.month.foreground[dayOfWeek][week] = new Color(108, 111, 133); 
        this.month.background[dayOfWeek][week] = new Color(220, 224, 232);
    }


    public Canvas draw() {
        Canvas canvas = new Canvas(width(), height(), new Color(76, 79, 105), new Color(239, 241, 245));

        canvas.overlay(0, 0, super.draw());

        return canvas;
    }
}
