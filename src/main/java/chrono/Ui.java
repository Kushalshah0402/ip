package chrono;

import chrono.task.Task;
import java.util.List;
import java.util.Scanner;

/**
 * Handles all user interaction including input and output display.
 */
public class Ui {

    private static final String LINE = "------------------------------------------------------------";
    private final Scanner scanner;

    /**
     * Creates a Ui instance for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a command entered by the user.
     * 
     * @return Trimmed user input string
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm Chrono");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Shutting down Chrono systems. Goodbye!");
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println("Sorry!! " + message);
        showLine();
    }

    /**
     * Displays a list of tasks.
     *
     * @param tasks Tasks to display
     */
    public void showList(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("Yayyy!! You have no tasks :)");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        showLine();
    }

    public void showTaskAdded(Task task, int size) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showTaskDeleted(Task task, int size) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showMarked(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("    " + task);
        showLine();
    }

    public void showUnmarked(Task task) {
        showLine();
        System.out.println("Oops! I guess you have yet to do:");
        System.out.println("    " + task);
        showLine();
    }

    private void showLine() {
        System.out.println(LINE);
    }

    public void showMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showFindResults(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        showLine();
    }
}
