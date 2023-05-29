package calendar.drawing.layers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import calendar.drawing.Canvas;
import calendar.drawing.color.Color;
import calendar.drawing.color.Theme;
import calendar.drawing.Drawable;
import calendar.drawing.Justification;
import calendar.drawing.components.Box;
import calendar.drawing.components.Grid;
import calendar.drawing.components.MultiBox;
import calendar.state.State;
import calendar.storage.Event;
import calendar.storage.Section;
import calendar.util.Vec2;

public class Month extends MultiBox {
    private int cellWidth;
    private int cellHeight;

    private State state;

    private Box title;
    private Grid weekdays;
    private Grid month;

    private LocalDate monthStartDate;
    // which weekday the month starts on
    private int monthStart;
    // length of the month
    private int monthLength;

    private static final int MAX_EVENT_HEIGHT = 2;

    private static final int TITLE_BOTTOM = 2;
    private static final int WEEKDAY_BOTTOM = 4;

    // constructors //

    public Month(State state, int cellWidth, int cellHeight) {
        super(
            new Drawable[3],
            // the first box is the title, in the middle of the third day
            new int[3],
            new int[]{ 0, TITLE_BOTTOM, WEEKDAY_BOTTOM },
            Canvas.cellDimToFull(cellWidth, 7), 
            Canvas.cellDimToFull(cellHeight, weeksFromState(state)) + WEEKDAY_BOTTOM
        );

        this.state = state;

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        this.reinitialize();
    }

    public void reinitialize() {
        // current month
        monthStartDate = date().withDayOfMonth(1);
        monthLength = monthStartDate.lengthOfMonth();
        monthStart = monthStartDate.getDayOfWeek().getValue() % 7;

        this.initTitle();
        this.initWeek();
        this.initMonth();
    }

    // getters //

    private LocalDate date() { return state.date(); }
    private LocalDate endDate() {
        LocalDate date = date();
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    private Theme colors() { return state.colors(); }

    private static int weeksFromState(State state) {
        LocalDate start = state.date().withDayOfMonth(1);
        int startWeekday = start.getDayOfWeek().getValue() % 7;
        int lengthOfMonth = start.lengthOfMonth();

        int gridDay = lengthOfMonth - 1 + startWeekday;

        return gridDay / 7 + 1;
    }

    public int weeks() {
        return dayToGridDay(monthLength) / 7 + 1;
    }

    // initializing grids //

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
        for(int day = 1; day <= 7; day++)
            weekdays.grid[day % 7][0] = DayOfWeek.of(day).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    private void initMonth() {
        // far to the left
        int x = 0;
        this.boxx[2] = x;
        
        // 1-height single row
        int columns = 7, rows = weeks();

        this.boxes[2] = month = new Grid(cellWidth, cellHeight, columns, rows, Justification.Right);

        // add the day numbers
        this.initDays();
    }

    private void initDays() {
        // previous month
        LocalDate previous = monthStartDate.minusMonths(1);
        int previousLength = previous.lengthOfMonth();

        // this is wacky, it's the offset from the start day of the month going in the past
        for(int offset = 1; monthStart - offset >= 0; offset++) {
            int day = previousLength - (offset - 1) - 1;
            int gridDay = monthStart - offset;
            setOffDay(day, gridDay);
        }

        // current month
        for(int day = 0; day < monthLength; day++) {
            int dayAdjusted = day + monthStart;
            setDay(day, dayAdjusted);
        }

        int nextStartDay = monthStart + monthLength;

        // next month
        for(int day = 0; day + nextStartDay < 7 * weeks(); day++) {
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
        this.month.foreground[dayOfWeek][week] = colors().offDayNum(); 
        this.month.background[dayOfWeek][week] = colors().offDayBack();
    }

    // coordinate helpers //

    private int dayToGridDay(int day) {
        return day - 1 + monthStart;
    }

    private Vec2 dayToCoords(int day) {
        return gridDayToCoords(dayToGridDay(day));
    }

    private Vec2 gridDayToCoords(int gridDay) {
        int day = gridDay % 7;
        int week = gridDay / 7;

        return gridToCoords(day, week);
    }

    public Vec2 gridToCoords(int day, int week) {
        int x = Canvas.cellDimToFull(cellWidth, day);
        int y = Canvas.cellDimToFull(cellHeight, week) + WEEKDAY_BOTTOM;

        return new Vec2(x, y);
    }

    // drawing //

    public Canvas draw() {
        Canvas canvas = new Canvas(width(), height(), colors().text(), colors().background());

        int weeks = weeks();

        // basic grid
        canvas.overlay(0, 0, super.draw());

        if(state.settings.colorfulMonths())
            canvas.highlightBox(boxx[0] + 2, boxy[0] + 1, title.width() - 4, 1, colors().highlightText(), state.monthColor());

        drawSelected(canvas);

        // events
        // notice that these are sorted
        List<Event> events = state.calendar.eventsInCurrentMonth();

        boolean[][][] alreadyDrawn = new boolean[7][weeks][cellHeight - 1];
        int[][] overflow = new int[7][weeks];

        for(Event event : events)
            drawEvent(canvas, event, alreadyDrawn, overflow);
         
        for(int day = 0; day < 7; day++)
            for(int week = 0; week < weeks; week++)
                if(overflow[day][week] > 0)
                    drawOverflow(canvas, day, week, overflow[day][week]);

        drawInfoLine(canvas);

        return canvas;
    }

    // for the info line
    public int height() { return super.height() + 1; }

    private void drawInfoLine(Canvas canvas) {
        int y = height() - 1;

        canvas.highlightBox(0, y, width(), 1, null, colors().infoLine());

        String helpText = "(?) for help";
        int helpX = width() - helpText.length() - 4;
        canvas.drawText(helpText, helpX, y, colors().helpText(), null);

        String errorCode = state.errorCode();
        canvas.drawText(errorCode, helpX - errorCode.length() - 1, y, colors().error(), null);

        int x = 2;

        for(Section section : state.calendar.sections()) {
            String text = section.title();
            int maxWidth = helpX - x - 1;
            if(maxWidth < 0) return;
            text = " " + sanitize(text, maxWidth - 2) + " ";

            canvas.drawText(text, x, y, colors().highlightText(), section.color());

            x += text.length() + 1;
        }
    }

    // prevents a string of text from being bigger than the max width
    private String sanitize(String text, int maxWidth) {
        return text.substring(0, Math.max(0, Math.min(text.length(), maxWidth)));
    }


    private void drawSelected(Canvas canvas) {
        Vec2 selected = dayToCoords(date().getDayOfMonth());

        canvas.highlightBox(selected.x, selected.y, cellWidth, cellHeight, state.settings.selectedDayColor(), colors().selectedDayBack());
    }

    private void drawOverflow(Canvas canvas, int day, int week, int amount) {
        Vec2 cell = gridToCoords(day, week);
        int x = cell.x;
        int y = cell.y + cellHeight - 1;

        canvas.drawText(String.format(" +%d ", amount), x, y, colors().overflowText(), colors().overflowHighlight());
    }

    // precondition: no events can have already been drawn in front of the currently drawing event
    private void drawEvent(Canvas canvas, Event event, boolean[][][] alreadyDrawn, int[][] overflow) {
        List<String> text = splitBounded(event.title(), cellWidth - 2);
        int rows = text.size();

        int start = event.start().getDayOfMonth() - 1 + monthStart;

        int startDay = start % 7;
        int startWeek = start / 7;

        // if it would take too much room, abort
        // it is fine to just check this cell 
        // because nothing else can be ahead that's not already in this cell
        int rowOffset = findSpace(alreadyDrawn[startDay][startWeek], text.size());
        if(rowOffset == -1) 
            { overflow[startDay][startWeek]++; return; }

        // text
        for(int i = 0; i < rows; i++) {
            Vec2 cell = gridToCoords(startDay, startWeek);
            canvas.drawText(
                text.get(i),
                cell.x + 1,
                cell.y + rowOffset + i
            );
        }

        // clamp the end date at the end of the month
        // FIX: this truncates events at the end
        // which would normally be ok, but the drawer only 
        // only takes into account the start date while drawing 
        // so the end will never get drawn
        LocalDateTime endDate = endDate().atTime(0, 0);
        if(event.end().isBefore(endDate))
            endDate = event.end();
        int end = endDate.getDayOfMonth() - 1 + monthStart;


        // color
        // it's done one by one to handle going across weeks
        for(int gridDay = start; gridDay <= end; gridDay++) {
            int day = gridDay % 7;
            int week = gridDay / 7;

            Vec2 cell = gridToCoords(day, week);
            int x = cell.x;
            int y = cell.y + rowOffset;

            int width = cellWidth;

            // draw to the right if it isn't done
            if(gridDay < end) {
                // extend coloring
                width++;
                // remove gridlines
                for(int i = 0; i < rows; i++) canvas.text[x + cellWidth][y + i] = ' ';
            }

            // draw to the left if it's on the left edge
            if(gridDay != start && day == 0) {
                x--;
                width++;
                for(int i = 0; i < rows; i++) canvas.text[x][y + i] = ' ';
            }

            canvas.highlightBox(x, y, width, rows, colors().highlightText(), event.section().color());

            // update already drawn
            for(int i = 0; i < rows; i++) 
                alreadyDrawn[day][week][rowOffset + i] = true;
        }
    }

    private int findSpace(boolean[] taken, int length) {
        int runStart = 0;
        int runLength = 0;

        for(int i = 0; i < taken.length; i++) {
            boolean available = !taken[i];
            if(available) {
                runLength++;
                if(runLength >= length) 
                    return runStart;
            } else {
                runLength = 0;
                runStart = i + 1;
            }
        }

        return -1;
    }

    // splits the string preferably between words to the specified width
    // if the height of the string is more than MAX_EVENT_HEIGHT, 
    //   the event's name is cut off by a "..." to make it that height
    private List<String> splitBounded(String string, int width) {
        ArrayList<String> split = split(string, width);

        if(split.size() <= MAX_EVENT_HEIGHT) return split;

        // get last string
        int last = MAX_EVENT_HEIGHT - 1;
        String lastString = split.get(last);
        
        // append ...
        int newWidth = Math.min(lastString.length(), width - 3);
        String newString = lastString.substring(0, newWidth) + "...";
        split.set(last, newString);

        return split.subList(0, MAX_EVENT_HEIGHT);
    }

    // splits the string preferably between words to the specified width
    private ArrayList<String> split(String string, int width) {
        ArrayList<String> list = new ArrayList<>();

        ArrayList<String> words = new ArrayList<>(Arrays.asList(string.split(" ")));

        int left = width;
        ArrayList<String> current = new ArrayList<>();

        // this is probably the single worst thing i've ever written
        // i profusely apologize
        while(words.size() > 0) {
            String word = words.remove(0);
            int length = word.length();

            // keep trying to fit word until it does
            while(true) {
                // the word is too big for a single row
                // print the rest and keep going
                if(length > width) {
                    // add substring of word to current
                    current.add(word.substring(0, left));
                    // start a new row
                    list.add(String.join(" ", current));
                    current.clear();
                    left = width;
                    // replace word
                    word = word.substring(left);
                    length = word.length();
                // the word is too big for this row, but big enough for a single one
                // start a new row
                } else if(length > left) {
                    // start a new row
                    list.add(String.join(" ", current));
                    current.clear();
                    left = width;
                // the word isn't too big for the row
                // add it
                } else {
                    current.add(word);
                    left -= length + 1;
                    break;
                }
            }
        }

        if(current.size() > 0) list.add(String.join(" ", current));

        return list;
    }
}
