import java.util.Scanner;

public class Chrono {

    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    private static final String LINE = "------------------------------------------------------------";
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";
    private static final String KEYWORD_BY = "/by";
    private static final String KEYWORD_FROM = "/from";
    private static final String KEYWORD_TO = "/to";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printWelcome();
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("bye")) {
                    printGoodbye();
                    break;
                }
                handleCommand(input);
            } catch (ChronoException e) {
                printError(e.getMessage());
            }
        }
        scanner.close();
    }

    private static void handleCommand(String input) throws ChronoException {
        if (input.equals("list")) {
            handleList();
        } else if (input.startsWith(COMMAND_MARK)) {
            handleMark(input);
        } else if (input.startsWith(COMMAND_UNMARK)) {
            handleUnmark(input);
        } else if (input.startsWith("todo")) {
            addTodo(input);
        } else if (input.startsWith("deadline")) {
            addDeadline(input);
        } else if (input.startsWith("event")) {
            addEvent(input);
        } else {
            throw new ChronoException("That's an invalid command :(");
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
        int index = Integer.parseInt(
                input.substring(COMMAND_MARK.length()).trim()
        ) - 1;

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
        int index = Integer.parseInt(
                input.substring(COMMAND_UNMARK.length()).trim()
        ) - 1;

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
        String description =
                input.substring(COMMAND_TODO.length()).trim();
        tasks[taskCount++] = new Todo(description);
        printTaskAdded();
    }

    private static void addDeadline(String input) {
        int byIndex = input.indexOf(KEYWORD_BY);
        if (byIndex == -1) {
            printInvalidFormat();
            return;
        }

        String description =
                input.substring(COMMAND_DEADLINE.length(), byIndex).trim();
        String by =
                input.substring(byIndex + KEYWORD_BY.length()).trim();

        tasks[taskCount++] = new Deadline(description, by);
        printTaskAdded();
    }

    private static void addEvent(String input) {
    int fromIndex = input.indexOf(KEYWORD_FROM);
    int toIndex = input.indexOf(KEYWORD_TO);

    if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
        printInvalidFormat();
        return;
    }

    String description =
        input.substring(COMMAND_EVENT.length(), fromIndex).trim();
    String from =
        input.substring(
            fromIndex + KEYWORD_FROM.length(),
            toIndex
        ).trim();

    String to =
        input.substring(
            toIndex + KEYWORD_TO.length()
        ).trim();

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

    private static void printError(String message) {
        printLine();
        System.out.println("Sorry!! " + message);
        printLine();
    }

    private static void printLine() {
        System.out.println(LINE);
    }
}