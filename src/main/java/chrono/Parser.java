package chrono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import chrono.exception.ChronoException;
import chrono.task.*;

/**
 * Handles parsing of user input and dispatches commands
 * to the appropriate handlers.
 */
public class Parser {
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete ";
    private static final String COMMAND_FIND = "find ";

    private static final String KEYWORD_BY = "/by";
    private static final String KEYWORD_FROM = "/from";
    private static final String KEYWORD_TO = "/to";

    /**
     * Parses a user input command and executes the corresponding action.
     * 
     * @param input Raw user input
     * @param tasks Task list to operate on
     * @param ui UI used for output
     * @throws ChronoException If the command is invalid
     */
    public static void parse(String input, TaskList tasks, Ui ui) throws ChronoException {
        input = input.trim();
        if (input.equals("list")) {
            ui.showList(tasks.getAll());
            return;
        }

        if (input.startsWith(COMMAND_MARK)) {
            handleMark(input, tasks, ui);
            return;
        }

        if (input.startsWith(COMMAND_UNMARK)) {
            handleUnmark(input, tasks, ui);
            return;
        }

        if (input.startsWith(COMMAND_DELETE)) {
            handleDelete(input, tasks, ui);
            return;
        }

        if (input.equals("todo") || input.startsWith(COMMAND_TODO)) {
            handleTodo(input, tasks, ui);
            return;
        }


        if (input.startsWith(COMMAND_DEADLINE)) {
            handleDeadline(input, tasks, ui);
            return;
        }

        if (input.startsWith(COMMAND_EVENT)) {
            handleEvent(input, tasks, ui);
            return;
        }

        if (input.startsWith("on ")) {
            handleOnCommand(input, tasks, ui);
            return;
        }

        if (input.startsWith(COMMAND_FIND)) {
            handleFind(input, tasks, ui);
            return;
        }


        throw new ChronoException("That's an invalid command :(");
    }

    private static void handleMark(String input, TaskList tasks, Ui ui) throws ChronoException {
        int index = extractIndex(input, COMMAND_MARK, tasks);
        Task task = tasks.get(index);
        task.markAsDone();
        ui.showMarked(task);
    }

    private static void handleUnmark(String input, TaskList tasks, Ui ui) throws ChronoException {
        int index = extractIndex(input, COMMAND_UNMARK, tasks);
        Task task = tasks.get(index);
        task.markAsNotDone();
        ui.showUnmarked(task);
    }

    private static void handleDelete(String input, TaskList tasks, Ui ui) throws ChronoException {
        int index = extractIndex(input, COMMAND_DELETE, tasks);
        Task removed = tasks.remove(index);
        ui.showTaskDeleted(removed, tasks.size());
    }

    private static void handleTodo(String input, TaskList tasks, Ui ui) throws ChronoException {
        String desc = extractDescription(input, COMMAND_TODO);
        Task task = new Todo(desc);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles creation of a deadline task.
     * Expected format: deadline <description> /by <date>
     * 
     * @throws ChronoException If format is invalid or date is missing
     */
    private static void handleDeadline(String input, TaskList tasks, Ui ui) throws ChronoException {
        int byIndex = input.indexOf(KEYWORD_BY);
        if (byIndex == -1) {
            throw new ChronoException("Deadline must have /by.");
        }
        String desc = extractDescription(input.substring(0, byIndex), "deadline ");
        String by = input.substring(byIndex + KEYWORD_BY.length()).trim();

        if (by.isEmpty()) {
            throw new ChronoException("The deadline time cannot be empty.");
        }
        Task task = new Deadline(desc, by);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles creation of an event task.
     * Expected format: event <description> /from <start> /to <end>
     * 
     * @throws ChronoException If format is invalid or fields are missing
     */
    private static void handleEvent(String input, TaskList tasks, Ui ui) throws ChronoException {
        int fromIndex = input.indexOf(KEYWORD_FROM);
        int toIndex = input.indexOf(KEYWORD_TO);
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new ChronoException("Event must have /from and /to.");
        }
        String desc = extractDescription(input.substring(0, fromIndex),"event ");
        String from = input.substring(fromIndex + KEYWORD_FROM.length(), toIndex).trim();
        String to = input.substring(toIndex + KEYWORD_TO.length()).trim();

        if (from.isEmpty() || to.isEmpty()) {
            throw new ChronoException("Event start and end time cannot be empty.");
        }
        Task task = new Event(desc, from, to);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the 'on' command which lists tasks occurring on a given date.
     * Expected format: on dd/MM/yyyy
     * 
     * @throws ChronoException If date format is invalid
     */
    private static void handleOnCommand(String input, TaskList tasks, Ui ui) throws ChronoException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new ChronoException("Please provide a date! Format: dd/MM/yyyy");
        }

        String dateStr = parts[1].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            throw new ChronoException("Invalid date format! Use dd/MM/yyyy");
        }

        List<Task> tasksOnDate = tasks.getAll().stream().filter(t -> {
            if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                return d.getBy().toLocalDate().equals(date);
            } else if (t instanceof Event) {
                Event e = (Event) t;
                LocalDate start = e.getFrom().toLocalDate();
                LocalDate end = e.getTo().toLocalDate();
                return ( !date.isBefore(start) && !date.isAfter(end) ); // within range
            }
            return false;
        }).toList();

        if (tasksOnDate.isEmpty()) {
            ui.showMessage("No tasks found on " + date.format(formatter));
        } else {
            ui.showList(tasksOnDate);
        }
    }

    private static void handleFind(String input, TaskList tasks, Ui ui) throws ChronoException {
        String keyword = input.substring(COMMAND_FIND.length()).trim();
        if (keyword.isEmpty()) {
            throw new ChronoException("Please provide a keyword to search.");
        }
        List<Task> matching = tasks.find(keyword);
        ui.showFindResults(matching);
    }


    /**
     * Extracts a task index from a command string.
     * 
     * @return Zero-based task index
     * @throws ChronoException If index is invalid
     */
    private static int extractIndex(String input, String command, TaskList tasks) throws ChronoException {
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

    /**
     * Extracts the description portion of a command.
     * 
     * @return Trimmed task description
     * @throws ChronoException If description is empty
     */
    private static String extractDescription(String input, String command) throws ChronoException {
        if (input.length() <= command.length()) {
            throw new ChronoException("The description cannot be empty.");
        }
        String desc = input.substring(command.length()).trim();
        if (desc.isEmpty()) {
            throw new ChronoException("The description cannot be empty.");
        }
        return desc;
    }
}
