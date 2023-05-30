package calendar.storage;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import calendar.state.State;

// Represents an event before editing is finished
// This is necessary as the dates may or may not be correct while editing
// This can be morphed into an event using toEvent
public class EditingEvent {
    private String title;

    private String start;
    private String end;

    private Section section;
    private Calendar calendar;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    private EditingEvent(Calendar calendar, String time) {
        this(calendar, calendar.sections().get(0), "Add Event", time, time);
    }

    public EditingEvent(Calendar calendar) {
        this(calendar, formatter.format(calendar.state().date().atTime(12, 0)));
    }

    public EditingEvent(Calendar calendar, Section section, String title, String start, String end) {
        this.title = title;

        this.start = start;
        this.end = end;

        this.calendar = calendar;
        this.section = section;
    }

    public String title() { return title; }
    public void setTitle(String title) { this.title = title; calendar.state().updateScreen(); }

    public String start() { return start; }
    public void setStart(String start) { this.start = start; calendar.state().updateScreen(); }
    public void setStartChecked(String start, State state) {
        LocalDateTime date = parse(start);

        if(date == null) {
            state.displayError(PARSE_ERROR);
            this.start = start;
        } else
            this.start = formatter.format(date); 

        calendar.state().updateScreen();
    }

    public String end() { return end; }
    public void setEnd(String end) { this.end = end; calendar.state().updateScreen(); }
    public void setEndChecked(String end, State state) {
        LocalDateTime date = parse(end);

        if(date == null) {
            state.displayError(PARSE_ERROR);
            this.end = end;
        } else
            this.end = formatter.format(date); 

        calendar.state().updateScreen();
    }

    public Section section() { return section; }
    public void setSection(Section section) { this.section = section; calendar.state().updateScreen(); }
    public void setSection(int i) { setSection(calendar.sections().get(i)); }

    public Event toEvent() {
        LocalDateTime start = parse(this.start);
        LocalDateTime end = parse(this.end);

        if(start == null || end == null) return null;

        return new Event(calendar, section, title, start, end);
    }

    public static final String PARSE_ERROR = "failed to parse start/end of event";
    public static final String EVENT_MAKE_ERROR = "failed to make event: " + PARSE_ERROR;

    private static Pattern NUMBER = Pattern.compile("\\d+");
    private static Pattern WORD = Pattern.compile("[a-zA-Z]+");
    private LocalDateTime parse(String string) {
        // parse each number
        Matcher numMatcher = NUMBER.matcher(string);
        ArrayList<String> numberMatches = new ArrayList<>();

        while(numMatcher.find())
            numberMatches.add(numMatcher.group());

        if(numberMatches.size() < 4) return null;
        
        Iterator<Integer> numbers = numberMatches.stream().map(num -> Integer.decode(num)).iterator();

        int month = numbers.next();
        int day = numbers.next();
        int year = numbers.next();
        int hour = numbers.next();

        // minute defaults to 0
        int minute;
        if(numbers.hasNext()) 
            minute = numbers.next();
        else
            minute = 0;

        // if they put in the shorthand form,
        // put it to this century
        if(year < 100)
            year += 2000;

        // parse if it's am or pm
        // get the last word
        Matcher wordMatcher = WORD.matcher(string);
        String lastWord = null;
        while(wordMatcher.find()) 
            lastWord = wordMatcher.group();

        // it only checks if there's a word that starts with p
        // if there is, then it assumes it's pm
        // if there isn't, then am
        boolean pm = lastWord != null && lastWord.toLowerCase().startsWith("p");

        // only add 12 if it's not already 12
        if(pm && hour != 12) hour += 12;

        try {
            return LocalDateTime.of(year, month, day, hour, minute);
        } catch(DateTimeException e) {
            return null;
        }
    }
}
