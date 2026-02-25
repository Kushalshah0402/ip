package chrono;

import chrono.task.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages a list of tasks and provides operations on them.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList initialized with existing tasks.
     * 
     * @param tasks Initial tasks to populate the list
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getAll() {
        return tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns tasks whose descriptions contain the given keyword.
     * 
     * @param keyword Keyword to search for
     * @return List of matching tasks
     */

    public List<Task> find(String keyword) {
        return tasks.stream()
            .filter(t -> t.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
    }

}
