package chrono;

import chrono.exception.ChronoException;
import chrono.task.Task;

import java.util.List;

/**
 * Main controller for the Chrono application.
 * Handles application lifecycle including loading tasks,
 * reads user input, executes commands, and saves all the tasks.
 */
public class Chrono {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes Chrono with a storage file.
     * Attempts to load existing tasks; on failure starts with an empty list.
     * 
     * @param filePath Path to task storage file.
     */
    public Chrono(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            List<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (ChronoException e) {
            ui.showError("Error loading tasks. Starting with empty list.");
            tasks = new TaskList();
        }
    }
    /**
     * Begins user interaction loop until "bye" is entered.
     * Processes input, executes commands, and saves tasks.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                if (input.equalsIgnoreCase("bye")) {
                    storage.save(tasks.getAll());
                    ui.showGoodbye();
                    isExit = true;
                } else {
                    Parser.parse(input, tasks, ui);
                    storage.save(tasks.getAll());
                }
            } catch (ChronoException e) {
                ui.showError(e.getMessage());
            }
        }
    }
    /**
     * Program entry point.
     * 
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        new Chrono("./data/chrono.txt").run();
    }
}
