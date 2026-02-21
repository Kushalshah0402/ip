package chrono;

import chrono.exception.ChronoException;
import chrono.task.*;

public class Parser {
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_DELETE = "delete ";

    private static final String KEYWORD_BY = "/by";
    private static final String KEYWORD_FROM = "/from";
    private static final String KEYWORD_TO = "/to";

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
