package chrono;

import chrono.exception.ChronoException;
import chrono.task.Task;

import java.util.List;

public class Chrono {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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
    public static void main(String[] args) {
        new Chrono("./data/chrono.txt").run();
    }
}
