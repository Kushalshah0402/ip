import java.util.Scanner;

public class Chrono {

    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("------------------------------------------------------------");
        System.out.println(" Hello! I'm Chrono");
        System.out.println(" What can I do for you?");
        System.out.println("------------------------------------------------------------");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("------------------------------------------------------------");
                System.out.println("Shutting down Chrono systems. Goodbye!");
                System.out.println("------------------------------------------------------------");
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                System.out.println("------------------------------------------------------------");
                if (taskCount == 0) {
                    System.out.println(" You have no tasks :).");
                } else {
                    System.out.println("Here are your tasks:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                }
                System.out.println("------------------------------------------------------------");
                continue;
            }

            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markAsDone();
                    System.out.println("------------------------------------------------------------");
                    System.out.println(" Nice! I have marked this task as done:");
                    System.out.println("   " + tasks[index]);
                    System.out.println("------------------------------------------------------------");
                }
                continue;
            }

            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                if (index >= 0 && index < taskCount) {
                    tasks[index].markAsNotDone();
                    System.out.println("------------------------------------------------------------");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + tasks[index]);
                    System.out.println("------------------------------------------------------------");
                }
                continue;
            }

            if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                tasks[taskCount++] = new Todo(desc);
                System.out.println("------------------------------------------------------------");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println("------------------------------------------------------------");
                continue;
            }

            if (input.startsWith("deadline ")) {
                int byIndex = input.indexOf("/by");
                if (byIndex != -1) {
                    String desc = input.substring(9, byIndex).trim();
                    String by = input.substring(byIndex + 3).trim();
                    tasks[taskCount++] = new Deadline(desc, by);
                    System.out.println("------------------------------------------------------------");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("------------------------------------------------------------");
                }
                continue;
            }

            if (input.startsWith("event ")) {
                // format: event <desc> /from <start> /to <end>
                int fromIndex = input.indexOf("/from");
                int toIndex = input.indexOf("/to");
                if (fromIndex != -1 && toIndex != -1) {
                    String desc = input.substring(6, fromIndex).trim();
                    String from = input.substring(fromIndex + 5, toIndex).trim();
                    String to = input.substring(toIndex + 3).trim();
                    tasks[taskCount++] = new Event(desc, from, to);
                    System.out.println("------------------------------------------------------------");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("------------------------------------------------------------");
                }
                continue;
            }
            System.out.println("------------------------------------------------------------");
            System.out.println(" Sorry, I don't understand that command.");
            System.out.println("------------------------------------------------------------");
        }
        scanner.close();
    }
}
