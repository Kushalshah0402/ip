package chrono.task;

import chrono.exception.ChronoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event occurring between a start and end date-time. 
 */ 
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates an Event task with start and end date-time.
     * 
     * @param description Task description
     * @param fromInput Start date-time string
     * @param toInput End date-time string
     * @throws ChronoException If format is invalid or end is before start
     */
    public Event(String description, String fromInput, String toInput) throws ChronoException {
        super(description);
        this.from = parseDateTime(fromInput);
        this.to = parseDateTime(toInput);
        if (to.isBefore(from)) {
            throw new ChronoException("Event end cannot be before start.");
        }
    }

    /**
     * Parses a date-time string into a LocalDateTime.
     * 
     * @param input Date-time string
     * @return Parsed LocalDateTime
     * @throws ChronoException If format is invalid
     */
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
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(parts[0], dateFormatter);
            LocalTime time = parseTime(parts[1]);
            return LocalDateTime.of(date, time);
        } catch (Exception e) {
            throw new ChronoException("Invalid format! Use dd/MM/yyyy HHmm or 'today HHmm'");
        }
    }

    /**
     * Parses a time string in HHmm format.
     * 
     * @param timeStr Time string
     * @return Parsed LocalTime
     */
    private LocalTime parseTime(String timeStr) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        return LocalTime.parse(timeStr, timeFormatter);
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy h:mm a");

        return "[" + getTaskType() + "]"
                + "[" + getStatusIcon() + "] "
                + description
                + " (from: " + from.format(outputFormatter)
                + " to: " + to.format(outputFormatter) + ")";
    }

    public String getFromString() {
        return from.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
    }

    public String getToString() {
        return to.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
    }
    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

}
