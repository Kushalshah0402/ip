import java.util.Scanner;

public class Chrono {

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
            System.out.println("------------------------------------------------------------");
            System.out.println(" " + input);
            System.out.println("------------------------------------------------------------");
        }

        scanner.close();
    }
}
