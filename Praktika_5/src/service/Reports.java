package service;

import model.Genre;
import model.MediaItem;
import storage.MediaLibrary;

public class Reports {
    private MediaService service;

    public Reports(MediaService service) {
        this.service = service;
    }

    public void summary() {
        MediaLibrary library = service.getLibrary();
        int duration = 0;
        MediaItem best = null;
        System.out.println(String.format("%-4s %-12s %-25s %-6s %-10s", "ID", "Тип", "Название", "Год", "Рейтинг"));
        for (int i = 0; i < library.getItems().size(); i++) {
            MediaItem item = (MediaItem) library.getItems().get(i);
            duration += item.getDurationMinutes();
            double average = library.average(item.getId());
            System.out.println(String.format("%-4d %-12s %-25s %-6d %-10.2f", item.getId(), item.getType().getTitle(), item.getTitle(), item.getYear(), average));
            if (best == null || average > library.average(best.getId())) {
                best = item;
            }
        }
        System.out.println("Всего элементов: " + library.getItems().size() + "; длительность: " + duration + " минут");
        System.out.println("Самая высокая оценка: " + (best == null ? "нет данных" : best.getTitle() + " (" + library.average(best.getId()) + ")"));
    }

    public void genreReport() {
        MediaLibrary library = service.getLibrary();
        for (Genre genre : Genre.values()) {
            int count = 0;
            for (int i = 0; i < library.getItems().size(); i++) {
                if (((MediaItem) library.getItems().get(i)).getGenre() == genre) {
                    count++;
                }
            }
            if (count > 0) {
                System.out.println(String.format("%-16s %d", genre, count));
            }
        }
    }

    public void duplicates() {
        MediaLibrary library = service.getLibrary();
        boolean found = false;
        for (int i = 0; i < library.getItems().size(); i++) {
            for (int j = i + 1; j < library.getItems().size(); j++) {
                MediaItem first = (MediaItem) library.getItems().get(i);
                MediaItem second = (MediaItem) library.getItems().get(j);
                if (first.getTitle().equalsIgnoreCase(second.getTitle()) && first.getYear() == second.getYear()) {
                    System.out.println("Возможный дубликат: " + first + " <-> " + second);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("Дубликаты не обнаружены");
        }
    }
}
