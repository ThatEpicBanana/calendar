package calendar.drawing.layers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import calendar.drawing.Canvas;
import calendar.drawing.Drawable;
import calendar.drawing.Justification;
import calendar.drawing.components.Box;
import calendar.drawing.components.Grid;
import calendar.drawing.components.MultiBox;

public class Month extends MultiBox {
    private int cellWidth;
    private int cellHeight;

    private Box title;
    private Grid weekdays;
    private Grid month;

    private GregorianCalendar calendar;

    private static final int WEEKS = 5;

    private static final String[][] weekdaysFull = 
        { { "Sunday" }, { "Monday" }, { "Tuesday" }, { "Wednesday" }, { "Thursday" }, { "Friday" }, { "Saturday" } };
    private static final String[][] weekdaysSemi = 
        { { "Sun" }, { "Mon" }, { "Tue" }, { "Wed" }, { "Thu" }, { "Fri" }, { "Sat" } };
    private static final String[][] weekdaysShort = 
        { { "S" }, { "M" }, { "T" }, { "W" }, { "T" }, { "F" }, { "S" } };

    private static final HashMap<Integer, String> monthNames = new HashMap<>();

    public Month(int cellWidth, int cellHeight, GregorianCalendar calendar) {
        super(
            new Drawable[3],
            // the first box is the title, in the middle of the third day
            new int[3],
            new int[]{ 0, 2, 4 },
            Canvas.cellDimToFull(cellWidth, 7), 
            Canvas.cellDimToFull(cellHeight, WEEKS) + 4
        );

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.calendar = calendar;

        this.initTitle();
        this.initWeek();
        this.initMonth();
    }

    private void initTitle() {
        // in the middle of the third day
        int x = Canvas.cellDimToFull(cellWidth, 2) + cellWidth / 2;
        this.boxx[0] = x;

        int width = cellWidth * 2 + 3;
        int height = 3;

        String name = monthNames.get(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.YEAR);

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
        weekdays.grid = sizedWeekdays(cellWidth);
    }

    private String[][] sizedWeekdays(int width) {
        if(width >= 11)
            return weekdaysFull;
        else if(width >= 5)
            return weekdaysSemi;
        else
            return weekdaysShort;
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
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int length = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Sunday is 1 so subtract

        // previous month
        Calendar previousMonth = (Calendar) calendar.clone();
        previousMonth.add(Calendar.MONTH, -1);
        int previousLength = previousMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int offset = 1; startDay - offset >= 0; offset++) {
            int day = previousLength - (offset - 1);
            int dayOfWeek = startDay - offset;

            this.month.grid[dayOfWeek][0] = day + "";
        }

        // current month
        for(int day = 0; day < length; day++) {
            int dayAdjusted = day + startDay;
            int week = dayAdjusted / 7;
            int dayOfWeek = dayAdjusted % 7;

            this.month.grid[dayOfWeek][week] = day + 1 + "";
        }

        int nextStartDay = startDay + length;

        // next month
        for(int day = 0; day + nextStartDay < 7 * WEEKS; day++) {
            int dayAdjusted = day + nextStartDay;
            int week = dayAdjusted / 7;
            int dayOfWeek = dayAdjusted % 7;

            this.month.grid[dayOfWeek][week] = day + 1 + "";
        }
    }

    public Canvas draw() {
        Canvas canvas = super.draw();

        // TODO: tasks

        return canvas;
    }

    static {
        monthNames.put(Calendar.JANUARY,   "January");
        monthNames.put(Calendar.FEBRUARY,  "Feburary");
        monthNames.put(Calendar.MARCH,     "March");
        monthNames.put(Calendar.APRIL,     "April");
        monthNames.put(Calendar.MAY,       "May");
        monthNames.put(Calendar.JUNE,      "June");
        monthNames.put(Calendar.JULY,      "July");
        monthNames.put(Calendar.AUGUST,    "August");
        monthNames.put(Calendar.SEPTEMBER, "September");
        monthNames.put(Calendar.OCTOBER,   "October");
        monthNames.put(Calendar.NOVEMBER,  "November");
        monthNames.put(Calendar.DECEMBER,  "December");
    }
}
