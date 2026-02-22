package chrono.task;

import chrono.exception.ChronoException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime by;
    public Deadline(String description, String byInput) throws ChronoException {
        super(description);
        this.by = parseDateTime(byInput);
    }

    private LocalDateTime parseDateTime(String input) throws ChronoException {
        try {
            input = input.trim();

            if (input.startsWith("today")) {
                String timePart = input.substring(5).trim();
                LocalDate today = LocalDate.now();
                LocalTime time = parseTime(timePart);
                return LocalDateTime.of(today, time);
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw new ChronoException("Invalid date format! Use dd/MM/yyyy HHmm");
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(parts[0], dateFormatter);
            LocalTime time = parseTime(parts[1]);
            return LocalDateTime.of(date, time);
        } catch (Exception e) {
            throw new ChronoException("Invalid date format! Use dd/MM/yyyy HHmm or 'today HHmm'");
        }
    }

    private LocalTime parseTime(String timeStr) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(timeStr, timeFormatter);
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy h:mm a");
        return "[" + getTaskType() + "]" + "[" + getStatusIcon() + "] "
                + description + " (by: " + by.format(outputFormatter) + ")";
    }
}
