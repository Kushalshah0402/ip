package chrono.task;


/**
 * Represents a simple to-do task without date constraints.
 */
public class Todo extends Task {
    /**
     * Creates a Todo task.
     * 
     * @param description Task description
     */
    public Todo(String description) {
        super(description);
    }
    
    @Override
    public String getTaskType() {
        return "T";
    }
}