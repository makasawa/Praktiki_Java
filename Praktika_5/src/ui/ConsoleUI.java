package ui;

import exception.MediaLibraryException;
import java.io.IOException;
import model.Album;
import model.Genre;
import model.MediaItem;
import model.Movie;
import model.Playlist;
import model.Series;
import model.User;
import model.VideoGame;
import service.Logger;
import service.MediaService;
import service.MediaSorter;
import service.Reports;
import storage.MediaFileStorage;
import storage.MediaLibrary;

public class ConsoleUI {
    private MediaService service;
    private MediaFileStorage files;
    private Logger logger;
    private Input input = new Input();
    private Reports reports;

    public ConsoleUI(MediaService service, MediaFileStorage files, Logger logger) {
        this.service = service;
        this.files = files;
        this.logger = logger;
        reports = new Reports(service);
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== МЕДИАТЕКА ===\n"
                    + "1. Каталог и поиск\n"
                    + "2. Управление материалами\n"
                    + "3. Пользователи и оценки\n"
                    + "4. Избранное и плейлисты\n"
                    + "5. Рекомендации\n"
                    + "6. Отчёты\n"
                    + "7. Импорт/экспорт\n"
                    + "8. Журнал\n"
                    + "0. Выход");
            int command = input.integer("Выбор: ", 0, 8);
            try {
                switch (command) {
                    case 0:
                        running = false;
                        break;
                    case 1:
                        catalog();
                        break;
                    case 2:
                        manage();
                        break;
                    case 3:
                        ratings();
                        break;
                    case 4:
                        playlists();
                        break;
                    case 5:
                        recommend();
                        break;
                    case 6:
                        reportsMenu();
                        break;
                    case 7:
                        filesMenu();
                        break;
                    case 8:
                        for (String entry : logger.getEntries()) {
                            System.out.println(entry);
                        }
                        break;
                }
            } catch (Exception exception) {
                System.out.println("Ошибка: " + exception.getMessage());
                logger.log("Ошибка: " + exception.getMessage());
            }
        }
        save();
        System.out.println("До свидания.");
    }

    private void catalog() {
        System.out.println("1 Все  2 Поиск по критериям  3 Сортировка");
        int command = input.integer("Выбор: ", 1, 3);
        if (command == 1) {
            show(service.sorted(new MediaSorter.ByTitle()));
        } else if (command == 2) {
            String query = input.text("Часть названия (Enter — любой): ");
            String tag = input.text("Тег (Enter — любой): ");
            MediaItem[] items = service.search(query.isEmpty() ? null : query, null, null, tag.isEmpty() ? null : tag);
            show(items);
        } else {
            int sortOrder = input.integer("1 Название, 2 Год: ", 1, 2);
            show(service.sorted(sortOrder == 1 ? new MediaSorter.ByTitle() : new MediaSorter.ByYear()));
        }
    }

    private void manage() throws MediaLibraryException {
        System.out.println("1 Добавить 2 Изменить название 3 Удалить 4 Найти по ID");
        int command = input.integer("Выбор: ", 1, 4);
        MediaLibrary library = service.getLibrary();
        if (command == 1) {
            int type = input.integer("1 фильм, 2 сериал, 3 альбом, 4 игра: ", 1, 4);
            int id = input.integer("ID: ", 1, 999999);
            String title = input.text("Название: ");
            int year = input.integer("Год: ", 1888, 2100);
            Genre genre = input.genre().get();
            int duration = input.integer("Длительность, мин: ", 0, 100000);
            String[] tags = input.text("Теги через запятую: ").split(",");
            MediaItem item;
            if (type == 1) {
                item = new Movie(id, title, year, genre, duration, input.text("Режиссёр: "), tags);
            } else if (type == 2) {
                item = new Series(id, title, year, genre, duration, input.integer("Сезонов: ", 1, 100), input.integer("Серий: ", 1, 10000), tags);
            } else if (type == 3) {
                item = new Album(id, title, year, genre, duration, input.text("Исполнитель: "), input.integer("Треков: ", 1, 1000), tags);
            } else {
                item = new VideoGame(id, title, year, genre, duration, input.text("Платформа: "), input.text("Разработчик: "), tags);
            }
            service.add(item);
            System.out.println("Добавлено.");
        } else if (command == 2) {
            MediaItem item = library.findItem(input.integer("ID: ", 1, 999999));
            if (item == null) {
                throw new MediaLibraryException("Не найдено");
            }
            item.setTitle(input.text("Новое название: "));
            logger.log("Изменено название ID " + item.getId());
        } else if (command == 3) {
            service.delete(input.integer("ID: ", 1, 999999));
        } else {
            MediaItem item = library.findItem(input.integer("ID: ", 1, 999999));
            System.out.println(item == null ? "Не найдено" : item);
        }
    }

    private void ratings() {
        System.out.println("1 Оценить 2 Показать отзывы");
        int command = input.integer("Выбор: ", 1, 2);
        MediaLibrary library = service.getLibrary();
        if (command == 1) {
            service.rate(input.integer("ID пользователя: ", 1, 999999), input.integer("ID материала: ", 1, 999999), input.integer("Оценка 1-10: ", 1, 10), input.text("Отзыв: "));
        } else {
            for (int i = 0; i < library.getRatings().size(); i++) {
                System.out.println(library.getRatings().get(i));
            }
        }
    }

    private void playlists() {
        MediaLibrary library = service.getLibrary();
        System.out.println("1 Добавить в избранное 2 Создать плейлист 3 Добавить материал в плейлист 4 Показать плейлисты 5 Избранное");
        int command = input.integer("Выбор: ", 1, 5);
        if (command == 1) {
            service.favorite(input.integer("ID пользователя: ", 1, 99999), input.integer("ID материала: ", 1, 99999));
        } else if (command == 2) {
            library.addPlaylist(new Playlist(library.getPlaylists().size() + 1, input.text("Название: ")));
        } else if (command == 3) {
            String name = input.text("Название плейлиста: ");
            Playlist playlist = library.findPlaylist(name);
            if (playlist == null) {
                throw new IllegalArgumentException("Плейлист не найден");
            }
            int id = input.integer("ID материала: ", 1, 999999);
            if (library.findItem(id) == null) {
                throw new IllegalArgumentException("Материал не найден");
            }
            playlist.add(id);
        } else if (command == 4) {
            for (int i = 0; i < library.getPlaylists().size(); i++) {
                Playlist playlist = (Playlist) library.getPlaylists().get(i);
                System.out.println(playlist);
                for (int id : playlist.getItemIds()) {
                    System.out.println("  " + library.findItem(id));
                }
            }
        } else {
            User user = library.findUser(input.integer("ID пользователя: ", 1, 99999));
            if (user == null) {
                throw new IllegalArgumentException("Нет пользователя");
            }
            for (int id : user.getFavorites()) {
                System.out.println(library.findItem(id));
            }
        }
    }

    private void recommend() {
        int id = input.integer("ID пользователя: ", 1, 99999);
        MediaItem[] items = service.recommendations(id);
        System.out.println("Рекомендации:");
        show(items);
    }

    private void reportsMenu() {
        System.out.println("1 Общая статистика 2 По жанрам 3 Дубликаты");
        int command = input.integer("Выбор: ", 1, 3);
        if (command == 1) {
            reports.summary();
        } else if (command == 2) {
            reports.genreReport();
        } else {
            reports.duplicates();
        }
    }

    private void filesMenu() {
        System.out.println("1 Экспорт 2 Импорт");
        int command = input.integer("Выбор: ", 1, 2);
        try {
            if (command == 1) {
                files.save(service.getLibrary());
                System.out.println("Сохранено в media.csv");
            } else {
                MediaLibrary loaded = files.load();
                System.out.println("Импортировано материалов: " + loaded.getItems().size() + " (текущий каталог не заменён)");
            }
        } catch (IOException exception) {
            System.out.println("Ошибка файла: " + exception.getMessage());
        }
    }

    private void save() {
        try {
            files.save(service.getLibrary());
        } catch (IOException exception) {
            System.out.println("Ошибка сохранения: " + exception.getMessage());
        }
    }

    private void show(MediaItem[] items) {
        if (items.length == 0) {
            System.out.println("Нет результатов");
        }
        for (MediaItem item : items) {
            System.out.println(item + " | рейтинг: " + String.format("%.2f", service.getLibrary().average(item.getId())));
        }
    }
}
