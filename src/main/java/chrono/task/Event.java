package chrono.task;

public class Event extends Task {
    private String from;
    private String to;
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    public String getFrom() {
        return from; // needed for save/load
    }

    public String getTo() {
        return to; // needed for save/load
    }
    
    @Override
    public String getTaskType() {
        return "E";
    }
    @Override
    public String toString() {
        return "[" + getTaskType() + "]" + "[" + getStatusIcon() + "] " + description + " (from: " + from + " to: " + to + ")";
    }
}