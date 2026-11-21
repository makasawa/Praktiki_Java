package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Logger {
    private String[] entries = new String[20];
    private int count;
    private String path;

    public Logger(String path) {
        this.path = path;
    }

    public void log(String message) {
        String line = java.time.LocalDateTime.now() + " | " + message;
        if (count == entries.length) {
            entries = Arrays.copyOf(entries, entries.length * 2);
        }
        entries[count++] = line;
        try (PrintWriter out = new PrintWriter(new FileWriter(path, true))) {
            out.println(line);
        } catch (IOException exception) {
            System.out.println("Не удалось записать журнал: " + exception.getMessage());
        }
    }

    public String[] getEntries() {
        return Arrays.copyOf(entries, count);
    }
}
