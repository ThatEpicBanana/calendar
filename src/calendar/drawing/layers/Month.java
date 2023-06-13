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
import calendar.drawing.color.Theme;
import calendar.drawing.Drawable;
import calendar.drawing.components.Box;
import calendar.drawing.components.Grid;
import calendar.state.State;
import calendar.storage.Event;
import calendar.storage.Section;
import calendar.util.Vec2;

// draws the entire month
// - title
// - weekdays
// - days
// - events
public class Month implements Drawable {
    private Vec2 cellDims;
    private Vec2 fullDims;
    private Vec2 monthDims;

    private State state;

    private Box title;
    private Grid weekdays;
    private Grid month;

    private static final int MAX_EVENT_HEIGHT = 2;

    private static final int TITLE_BOTTOM = 2;
    private static final int WEEKDAY_BOTTOM = 4;

    // constructors //

    public Month(State state, int cellWidth, int cellHeight) {
        this.state = state;

        this.monthDims = new Vec2(7, weeks());

        this.cellDims = new Vec2(cellWidth, cellHeight);
        this.fullDims = Grid.gridPosToReal(monthDims, cellDims).addY(WEEKDAY_BOTTOM + 1);

        this.title = new Box(new Vec2(cellWidth * 2 + 3, 3), false, this::drawTitle);
        this.weekdays = new Grid(this.cellDims.withY(1), this.monthDims.withY(1), false, this::drawWeekday);
        this.month = new Grid(cellDims, monthDims, false, this::drawDay);
    }

    // getters //

    private LocalDate date() { return state.date(); }

    private int monthLength() { return date().lengthOfMonth(); }
    private LocalDate startDate() { return date().withDayOfMonth(1); }
    private LocalDate endDate() { return date().withDayOfMonth(monthLength()); }
    private int startDay() { return startDate().getDayOfWeek().getValue() % 7; }

    private Theme colors() { return state.colors(); }

    private int dayToGridDay(int day) 
        { return day - 1 + startDay(); }

    public int weeks() 
        { return dayToGridDay(monthLength()) / 7 + 1; }

    private int cellWidth() { return cellDims.x; }
    private int cellHeight() { return cellDims.y; }

    public int width() { return fullDims.x; }
    public int height() { return fullDims.y; }

    // helper methods //
    
    private int gridToOffset(Vec2 gridCoord) {
        return gridCoord.y * 7 + gridCoord.x;
    }

    private LocalDate gridToDayOfMonth(Vec2 gridCoord) {
        int dayOfMonth = gridToOffset(gridCoord) - startDay();
        return startDate().plusDays(dayOfMonth);
    }

    private Vec2 dayToCoords(int day) {
        return offsetToCoords(dayToGridDay(day));
    }

    private Vec2 offsetToCoords(int gridDay) {
        int day = gridDay % 7;
        int week = gridDay / 7;

        return gridToCoords(day, week);
    }

    public Vec2 gridToCoords(int day, int week) {
        return month.gridPosToReal(new Vec2(day, week)).addY(WEEKDAY_BOTTOM);
    }


    // grid drawers //

    private void drawTitle(Canvas canvas, Vec2 dims) {
        String name = date().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date().getYear();

        canvas.textCentered(name, 1);
    }

    private void drawWeekday(Canvas canvas, Vec2 gridCoord, Vec2 cellDims) {
        int day = gridCoord.x == 0 ? 7 : gridCoord.x;
        String name = DayOfWeek.of(day).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        canvas.textCentered(name, 0);
    }

    private void drawDay(Canvas canvas, Vec2 gridCoord, Vec2 cellDims) {
        LocalDate cellDate = gridToDayOfMonth(gridCoord);
        String name = cellDate.getDayOfMonth() + "";

        canvas.textRight(name, cellDims.y - 1);

        // other month days
        if(!cellDate.getMonth().equals(date().getMonth()))
            canvas.highlightBox(0, 0, cellDims.x, cellDims.y, colors().offDayNum(), colors().offDayBack());
    }

    // drawing //

    public Canvas draw() {
        Canvas canvas = new Canvas(width(), height(), colors().text(), colors().background());

        int weeks = weeks();

        int titleX = Canvas.gridLength(cellWidth(), 2) + cellWidth() / 2;
        int titleY = 0;

        canvas.rectangle(width(), height() - 1, false);

        canvas.merge(titleX, 0, this.title.draw());
        canvas.merge(0, TITLE_BOTTOM, this.weekdays.draw());
        canvas.merge(0, WEEKDAY_BOTTOM, this.month.draw());


        if(state.config.colorfulMonths())
            canvas.highlightBox(titleX + 2, titleY + 1, title.width() - 4, 1, colors().highlightText(), state.monthColor());

        drawSelected(canvas);

        // events
        // notice that these are sorted
        // TODO: filter events in certain sections
        List<Event> events = state.calendar.eventsInCurrentMonth();

        boolean[][][] alreadyDrawn = new boolean[7][weeks][cellHeight() - 1];
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

    // prevents a string of text from being bigger than the max width
    private String sanitize(String text, int maxWidth) {
        return text.substring(0, Math.max(0, Math.min(text.length(), maxWidth)));
    }

    private void drawSelected(Canvas canvas) {
        Vec2 selected = dayToCoords(date().getDayOfMonth());

        canvas.highlightBox(selected.x, selected.y, cellWidth(), cellHeight(), state.config.selectedDayColor(), colors().selectedDayBack());
    }

    private void drawOverflow(Canvas canvas, int day, int week, int amount) {
        Vec2 cell = gridToCoords(day, week);
        int x = cell.x;
        int y = cell.y + cellHeight() - 1;

        canvas.text(String.format(" +%d ", amount), x, y, colors().overflowText(), colors().overflowHighlight());
    }

    private void drawInfoLine(Canvas canvas) {
        int y = height() - 1;

        canvas.highlightBox(0, y, width(), 1, null, colors().infoLine());

        String helpText = "(?) for help";
        int helpX = width() - helpText.length() - 4;
        canvas.text(helpText, helpX, y, colors().helpText(), null);

        int x = 2;

        for(Section section : state.calendar.sections()) {
            String text = section.title();
            int maxWidth = helpX - x - 1;
            if(maxWidth < 0) return;
            text = " " + sanitize(text, maxWidth - 2) + " ";

            canvas.text(text, x, y, colors().highlightText(), section.color());

            x += text.length() + 1;
        }

        String errorCode = state.errorCode();
        canvas.text(" " + errorCode + " ", helpX - errorCode.length() - 2, y, colors().error(), colors().infoLine());
    }

    // precondition: no events can have already been drawn in front of the currently drawing event
    private void drawEvent(Canvas canvas, Event event, boolean[][][] alreadyDrawn, int[][] overflow) {
        // TODO: add cool time maybe? definitely put it in settings if so
        List<String> text = splitBounded(event.title(), cellWidth() - 2);
        int rows = text.size();

        int start = event.start().getDayOfMonth() - 1 + startDay();

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
            canvas.text(
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
        int end = endDate.getDayOfMonth() - 1 + startDay();


        // color
        // it's done one by one to handle going across weeks
        for(int gridDay = start; gridDay <= end; gridDay++) {
            int day = gridDay % 7;
            int week = gridDay / 7;

            Vec2 cell = gridToCoords(day, week);
            int x = cell.x;
            int y = cell.y + rowOffset;

            int width = cellWidth();

            // draw to the right if it isn't done
            if(gridDay < end) {
                // extend coloring
                width++;
                // remove gridlines
                for(int i = 0; i < rows; i++) canvas.set(x + cellWidth(), y + i, ' ');
            }

            // draw to the left if it's on the left edge
            if(gridDay != start && day == 0) {
                x--;
                width++;
                for(int i = 0; i < rows; i++) canvas.set(x, y + i, ' ');
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
