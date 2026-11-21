package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import model.Album;
import model.Genre;
import model.MediaItem;
import model.Movie;
import model.Series;
import model.VideoGame;

public class MediaFileStorage {
    private String path;

    public MediaFileStorage(String path) {
        this.path = path;
    }

    public boolean exists() {
        return new File(path).exists();
    }

    public void save(MediaLibrary library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            for (int i = 0; i < library.getItems().size(); i++) {
                MediaItem item = (MediaItem) library.getItems().get(i);
                out.println(item.getType().getCode() + ";" + item.getId() + ";" + clean(item.getTitle()) + ";"
                        + item.getYear() + ";" + item.getGenre().name() + ";" + item.getDurationMinutes() + ";"
                        + clean(item.getDetails()) + ";");
            }
        }
    }

    private String clean(String text) {
        return text.replace("; ", ", ").replace(";", " ,");
    }

    public MediaLibrary load() throws IOException {
        MediaLibrary library = new MediaLibrary();
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                try {
                    String[] fields = line.split(";", -1);
                    if (fields.length < 8) {
                        throw new IllegalArgumentException("Мало полей");
                    }
                    int type = Integer.parseInt(fields[0]);
                    int id = Integer.parseInt(fields[1]);
                    int year = Integer.parseInt(fields[3]);
                    int duration = Integer.parseInt(fields[5]);
                    Genre genre = Genre.valueOf(fields[4]);
                    MediaItem item;
                    if (type == 1) {
                        item = new Movie(id, fields[2], year, genre, duration, fields[6], new String[0]);
                    } else if (type == 2) {
                        item = new Series(id, fields[2], year, genre, duration, 1, 1, new String[0]);
                    } else if (type == 3) {
                        item = new Album(id, fields[2], year, genre, duration, fields[6], 1, new String[0]);
                    } else if (type == 4) {
                        item = new VideoGame(id, fields[2], year, genre, duration, "не указана", fields[6], new String[0]);
                    } else {
                        throw new IllegalArgumentException("Неизвестный тип");
                    }
                    library.addItem(item);
                } catch (RuntimeException exception) {
                    System.out.println("Пропущена повреждённая строка " + lineNumber + ": " + exception.getMessage());
                }
            }
        }
        return library;
    }
}
