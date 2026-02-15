package chrono;

import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import chrono.exception.ChronoException;
import chrono.task.Deadline;
import chrono.task.Event;
import chrono.task.Task;
import chrono.task.Todo;

public class Chrono {

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String DATA_FILE = "./data/chrono.txt";
    private static final String LINE = "------------------------------------------------------------";
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";
    private static final String COMMAND_DELETE = "delete ";
    private static final String KEYWORD_BY = "/by";
    private static final String KEYWORD_FROM = "/from";
    private static final String KEYWORD_TO = "/to";

    public static void main(String[] args) {
        loadTasks();
        Scanner scanner = new Scanner(System.in);
        printWelcome();

        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("bye")) {
                    saveTasks();
                    printGoodbye();
                    break;
                }
                handleCommand(input);
                saveTasks();
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
        } else if (input.startsWith(COMMAND_DELETE)) {
            handleDelete(input);
        } else if (input.startsWith(COMMAND_TODO)) {
            addTodo(input);
        } else if (input.startsWith(COMMAND_DEADLINE)) {
            addDeadline(input);
        } else if (input.startsWith(COMMAND_EVENT)) {
            addEvent(input);
        } else {
            throw new ChronoException("That's an invalid command :(");
        }
    }

    private static void handleList() {
        printLine();
        if (tasks.isEmpty()) {
            System.out.println("Yayyy!! You have no tasks :)");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        printLine();
    }

    private static void handleMark(String input) throws ChronoException {
        Task task = getTaskFromIndex(input, COMMAND_MARK);
        task.markAsDone();
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("    " + task);
        printLine();
    }

    private static void handleUnmark(String input) throws ChronoException {
        Task task = getTaskFromIndex(input, COMMAND_UNMARK);
        task.markAsNotDone();
        printLine();
        System.out.println("Oops! I guess you have yet to do:");
        System.out.println("    " + task);
        printLine();
    }

    private static void handleDelete(String input) throws ChronoException {
        int index = extractIndex(input, COMMAND_DELETE);

        Task removedTask = tasks.remove(index);

        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("    " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    private static void addTodo(String input) throws ChronoException {
        String description = extractDescription(input, COMMAND_TODO);
        tasks.add(new Todo(description));
        printTaskAdded();
    }

    private static void addDeadline(String input) throws ChronoException {
        int byIndex = input.indexOf(KEYWORD_BY);
        if (byIndex == -1) {
            throw new ChronoException("Deadline must have /by.");
        }

        String description = extractDescription(input.substring(0, byIndex), COMMAND_DEADLINE);
        String by = input.substring(byIndex + KEYWORD_BY.length()).trim();
        if (by.isEmpty()) {
            throw new ChronoException("The deadline time cannot be empty.");
        }
        tasks.add(new Deadline(description, by));
        printTaskAdded();
    }

    private static void addEvent(String input) throws ChronoException {
        int fromIndex = input.indexOf(KEYWORD_FROM);
        int toIndex = input.indexOf(KEYWORD_TO);
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new ChronoException("Event must have /from and /to.");
        }

        String description = extractDescription(input.substring(0, fromIndex), COMMAND_EVENT);
        String from = input.substring(fromIndex + KEYWORD_FROM.length(), toIndex).trim();
        String to = input.substring(toIndex + KEYWORD_TO.length()).trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new ChronoException("Event start and end time cannot be empty.");
        }
        tasks.add(new Event(description, from, to));
        printTaskAdded();
    }

    private static Task getTaskFromIndex(String input, String command) throws ChronoException {
        int index = extractIndex(input, command);
        return tasks.get(index);
    }

    private static int extractIndex(String input, String command) throws ChronoException {
        try {
            int index = Integer.parseInt(input.substring(command.length()).trim()) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new ChronoException("That task number does not exist.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new ChronoException("Please provide a valid task number.");
        }
    }

    private static String extractDescription(String input, String command) throws ChronoException {
        if (input.length() <= command.length()) {
            throw new ChronoException("The description cannot be empty.");
        }

        String description = input.substring(command.length()).trim();
        if (description.isEmpty()) {
            throw new ChronoException("The description cannot be empty.");
        }
        return description;
    }

    private static void printTaskAdded() {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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

    private static void saveTasks() {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            for (Task t : tasks) {
                String line = "";
                if (t instanceof Todo) {
                    line = "T | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription();
                } else if (t instanceof Deadline) {
                    line = "D | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription() +
                        " | " + ((Deadline) t).getBy();
                } else if (t instanceof Event) {
                    line = "E | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription() +
                        " | " + ((Event) t).getFrom() + " | " + ((Event) t).getTo();
                }
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    private static void loadTasks() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue; 
                String[] parts = line.split("\\|");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                if (parts.length < 3) continue;

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];

                if (type.equals("T")) {
                    Task t = new Todo(desc);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                } else if (type.equals("D")) {
                    if (parts.length < 4) continue;
                    Task t = new Deadline(desc, parts[3]);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                } else if (type.equals("E")) {
                    if (parts.length < 5) continue;
                    Task t = new Event(desc, parts[3], parts[4]);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading tasks.");
        }
    }
}
