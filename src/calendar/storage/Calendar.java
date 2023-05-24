import java.time.LocalDate;

public class Calendar {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private double startTime;
    private double endTime;
    private Section section;

    public Calendar(String title, LocalDate startDate, LocalDate endDate, double startTime, double endTime, Section section) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.section = section;
    }

    // Getters and setters for modifying the calendar properties
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    // Method to convert the time from 24-hour format to 12-hour format
    private String convertTo12HourFormat(double time) {
        int hour = (int) time;
        int minute = (int) ((time - hour) * 60);

        String period = "AM";
        if (hour >= 12) {
            period = "PM";
            if (hour > 12) {
                hour -= 12;
            }
        }

        return String.format("%02d:%02d %s", hour, minute, period);
    }

    // Method to display the calendar information
    public void display() {
        String startTimeFormatted = convertTo12HourFormat(startTime);
        String endTimeFormatted = convertTo12HourFormat(endTime);

        System.out.println("Title: " + title);
        System.out.println("Time Frame: " + startDate + " " + startTimeFormatted + " to " +
                endDate + " " + endTimeFormatted);
        System.out.println("Section: " + section.getTitle());
        System.out.println("Section Color: " + section.getColor());
    }

    public static void main(String[] args) {
        Section section = new Section("Red", "\u001B[31m");
        Calendar calendar = new Calendar("Dentist Appointment",
                LocalDate.of(2023, 2, 23),
                LocalDate.of(2023, 2, 24),
                8.0,
                16.0,
                section);

        // Display the calendar information
        calendar.display();

        // Modify the calendar properties
        calendar.setTitle("Haircut Appointment");
        calendar.setEndTime(17.5);

        // Display the modified calendar information
        calendar.display();
    }
}

class Section {
    private String title;
    private String color;

    public Section(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
