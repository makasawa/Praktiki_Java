import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        MediaLibrary library = new MediaLibrary();
        seed(library);
        while (true) {
            System.out.println("\n=== МЕДИАТЕКА ===\n"
                    + "1 Каталог\n"
                    + "2 Добавить материал\n"
                    + "3 Поиск\n"
                    + "4 Оценить\n"
                    + "5 В избранное\n"
                    + "6 Создать плейлист\n"
                    + "7 Добавить в плейлист\n"
                    + "8 Плейлисты\n"
                    + "9 Удалить\n"
                    + "10 Статистика\n"
                    + "0 Выход");
            int command = number("Выбор: ");
            try {
                if (command == 0) {
                    break;
                }
                switch (command) {
                    case 1:
                        library.showAll();
                        break;
                    case 2:
                        add(library);
                        break;
                    case 3:
                        System.out.print("Название или жанр: ");
                        library.search(SCANNER.nextLine());
                        break;
                    case 4:
                        library.rate(number("ID материала: "), number("ID пользователя (1-3): "), number("Оценка 1-10: "), read("Отзыв: "));
                        break;
                    case 5:
                        library.favorite(number("ID пользователя: "), number("ID материала: "));
                        break;
                    case 6:
                        library.createPlaylist(read("Название: "));
                        break;
                    case 7:
                        library.addToPlaylist(read("Плейлист: "), number("ID материала: "));
                        break;
                    case 8:
                        library.showPlaylists();
                        break;
                    case 9:
                        library.remove(number("ID: "));
                        break;
                    case 10:
                        library.reports();
                        break;
                    default:
                        System.out.println("Нет такой команды");
                }
            } catch (RuntimeException exception) {
                System.out.println("Ошибка: " + exception.getMessage());
            }
        }
        System.out.println("Работа завершена.");
    }

    private static int number(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException exception) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private static String read(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }

    private static void add(MediaLibrary library) {
        int type = number("Тип: 1-фильм, 2-сериал, 3-альбом, 4-игра: ");
        int id = number("ID: ");
        String title = read("Название: ");
        int year = number("Год: ");
        Genre[] genres = Genre.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + ". " + genres[i]);
        }
        int genreNumber = number("Жанр: ");
        if (genreNumber < 1 || genreNumber > genres.length) {
            throw new IllegalArgumentException("Нет такого жанра");
        }
        int duration = number("Длительность (мин): ");
        String[] tags = read("Теги через запятую: ").split(",");
        MediaItem item;
        if (type == 1) {
            item = new Movie(id, title, year, genres[genreNumber - 1], duration, read("Режиссёр: "), tags);
        } else if (type == 2) {
            item = new Series(id, title, year, genres[genreNumber - 1], duration, number("Сезонов: "), number("Серий: "), tags);
        } else if (type == 3) {
            item = new Album(id, title, year, genres[genreNumber - 1], duration, read("Исполнитель: "), number("Треков: "), tags);
        } else if (type == 4) {
            item = new VideoGame(id, title, year, genres[genreNumber - 1], duration, read("Платформа: "), read("Разработчик: "), tags);
        } else {
            throw new IllegalArgumentException("Неизвестный тип");
        }
        library.add(item);
        System.out.println("Добавлено.");
    }

    private static void seed(MediaLibrary library) {
        library.addUser(new User(1, "Анна"));
        library.addUser(new User(2, "Иван"));
        library.addUser(new User(3, "Мария"));
        library.add(new Movie(1, "Начало", 2010, Genre.SCI_FI, 148, "Кристофер Нолан", new String[]{"сон", "триллер"}));
        library.add(new Series(2, "Шерлок", 2010, Genre.DRAMA, 90, 4, 13, new String[]{"детектив"}));
        library.add(new Album(3, "The Dark Side of the Moon", 1973, Genre.ROCK, 43, "Pink Floyd", 10, new String[]{"classic"}));
        library.add(new VideoGame(4, "The Witcher 3", 2015, Genre.FANTASY, 300, "PC", "CD Projekt", new String[]{"RPG"}));
        library.add(new Movie(5, "Интерстеллар", 2014, Genre.SCI_FI, 169, "Кристофер Нолан", new String[]{"космос"}));
        library.add(new Series(6, "Друзья", 1994, Genre.COMEDY, 22, 10, 236, new String[]{"ситком"}));
        library.add(new Album(7, "Thriller", 1982, Genre.POP, 42, "Michael Jackson", 9, new String[]{"pop"}));
        library.add(new VideoGame(8, "Minecraft", 2011, Genre.ADVENTURE, 0, "PC", "Mojang", new String[]{"sandbox"}));
    }
}
