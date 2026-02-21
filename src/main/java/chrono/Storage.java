package chrono;

import chrono.exception.ChronoException;
import chrono.task.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws ChronoException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];
                Task task;
                switch (type) {
                case "T":
                    task = new Todo(desc);
                    break;
                case "D":
                    task = new Deadline(desc, parts[3]);
                    break;
                case "E":
                    task = new Event(desc, parts[3], parts[4]);
                    break;
                default:
                    continue;
                }

                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            throw new ChronoException("Error loading file.");
        }
        return tasks;
    }

    public void save(List<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            for (Task t : tasks) {
                String line = "";
                if (t instanceof Todo) {
                    line = "T | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription();
                } else if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    line = "D | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription() + " | " + d.getBy();
                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    line = "E | " + (t.isDone() ? "1" : "0") + " | " + t.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
                }
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}
