import java.util.Scanner;

public class Chrono {

    private static int MAX_TASKS = 100;
    private static String[] tasks = new String[MAX_TASKS];
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
            tasks[taskCount] = input;
            taskCount++;
            System.out.println("------------------------------------------------------------");
            System.out.println(" added: " + input);
            System.out.println("------------------------------------------------------------");
        }

        scanner.close();
    }
}
