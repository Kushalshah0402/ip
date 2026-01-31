import java.util.Scanner;

public class Chrono {

    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;
    private static final String LINE = "------------------------------------------------------------";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printWelcome();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                printGoodbye();
                break;
            }
            handleCommand(input);
        }
        scanner.close();
    }

    private static void handleCommand(String input) {
        if (input.equalsIgnoreCase("list")) {
            handleList();
        } else if (input.startsWith("mark ")) {
            handleMark(input);
        } else if (input.startsWith("unmark ")) {
            handleUnmark(input);
        } else if (input.startsWith("todo ")) {
            addTodo(input);
        } else if (input.startsWith("deadline ")) {
            addDeadline(input);
        } else if (input.startsWith("event ")) {
            addEvent(input);
        } else {
            printLine();
            System.out.println("Sorry, I don't understand that command.");
            printLine();
        }
    }

    private static void handleList() {
        printLine();
        if (taskCount == 0) {
            System.out.println("You have no tasks :).");
        } else {
            System.out.println("Here are your tasks:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
        printLine();
    }

    private static void handleMark(String input) {
        int index = Integer.parseInt(input.substring(5)) - 1;
        if (isValidIndex(index)) {
            tasks[index].markAsDone();
            printLine();
            System.out.println("Nice! I have marked this task as done:");
            System.out.println("    " + tasks[index]);
            printLine();
        } else {
            printInvalidTask();
        }
    }

    private static void handleUnmark(String input) {
        int index = Integer.parseInt(input.substring(7)) - 1;
        if (isValidIndex(index)) {
            tasks[index].markAsNotDone();
            printLine();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("    " + tasks[index]);
            printLine();
        } else {
            printInvalidTask();
        }
    }

    private static void addTodo(String input) {
        String description = input.substring(5).trim();
        tasks[taskCount++] = new Todo(description);
        printTaskAdded();
    }

    private static void addDeadline(String input) {
        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            printInvalidFormat();
            return;
        }

        String description = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();
        tasks[taskCount++] = new Deadline(description, by);
        printTaskAdded();
    }

    private static void addEvent(String input) {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            printInvalidFormat();
            return;
        }

        String description = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        tasks[taskCount++] = new Event(description, from, to);
        printTaskAdded();
    }

    private static void printTaskAdded() {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index < taskCount;
    }

    private static void printInvalidTask() {
        printLine();
        System.out.println("Invalid task number.");
        printLine();
    }

    private static void printInvalidFormat() {
        printLine();
        System.out.println("Invalid command format.");
        printLine();
    }

    private static void printWelcome() {
        printLine();
        System.out.println("Hello! I'm Chrono");
        System.out.println("What can I do for you?");
        printLine();
    }

    private static void printGoodbye() {
        printLine();
        System.out.println("Shutting down Chrono systems. Goodbye!");
        printLine();
    }

    private static void printLine() {
        System.out.println(LINE);
    }
}
