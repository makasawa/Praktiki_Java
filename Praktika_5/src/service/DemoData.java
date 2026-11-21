package service;

import model.Album;
import model.Genre;
import model.Movie;
import model.Series;
import model.User;
import model.VideoGame;
import storage.MediaLibrary;

public class DemoData {

    public static MediaLibrary create(Logger logger) {
        MediaLibrary library = new MediaLibrary();
        library.addUser(new User(1, "Анна"));
        library.addUser(new User(2, "Иван"));
        library.addUser(new User(3, "Мария"));
        library.addUser(new User(4, "Олег"));
        library.addItem(new Movie(1, "Начало", 2010, Genre.SCI_FI, 148, "Кристофер Нолан", "сон,триллер".split(",")));
        library.addItem(new Movie(2, "Интерстеллар", 2014, Genre.SCI_FI, 169, "Кристофер Нолан", "космос".split(",")));
        library.addItem(new Series(3, "Шерлок", 2010, Genre.DRAMA, 90, 4, 13, "детектив".split(",")));
        library.addItem(new Series(4, "Друзья", 1994, Genre.COMEDY, 22, 10, 236, "ситком".split(",")));
        library.addItem(new Album(5, "The Dark Side of the Moon", 1973, Genre.ROCK, 43, "Pink Floyd", 10, "classic".split(",")));
        library.addItem(new Album(6, "Thriller", 1982, Genre.POP, 42, "Michael Jackson", 9, "pop".split(",")));
        library.addItem(new VideoGame(7, "The Witcher 3", 2015, Genre.FANTASY, 300, "PC", "CD Projekt", "RPG,open-world".split(",")));
        library.addItem(new VideoGame(8, "Minecraft", 2011, Genre.ADVENTURE, 0, "PC", "Mojang", "sandbox".split(",")));
        library.addItem(new Movie(9, "Побег из Шоушенка", 1994, Genre.DRAMA, 142, "Фрэнк Дарабонт", "драма".split(",")));
        library.addItem(new Movie(10, "Матрица", 1999, Genre.SCI_FI, 136, "Лана и Лилли Вачовски", "киберпанк".split(",")));
        library.addItem(new Series(11, "Чернобыль", 2019, Genre.DRAMA, 60, 1, 5, "история".split(",")));
        library.addItem(new Album(12, "Kind of Blue", 1959, Genre.JAZZ, 46, "Miles Davis", 5, "jazz".split(",")));
        library.addItem(new VideoGame(13, "Portal 2", 2011, Genre.ADVENTURE, 8, "PC", "Valve", "puzzle".split(",")));
        library.addItem(new Movie(14, "Один дома", 1990, Genre.COMEDY, 103, "Крис Коламбус", "комедия".split(",")));
        library.addItem(new Series(15, "Очень странные дела", 2016, Genre.FANTASY, 55, 4, 34, "мистика".split(",")));
        MediaService service = new MediaService(library, logger);
        service.rate(1, 1, 9, "Отличный сюжет");
        service.rate(2, 1, 10, "Шедевр");
        service.rate(3, 1, 8, "Интересно");
        service.rate(1, 2, 10, "Великолепно");
        service.rate(2, 2, 9, "Сильно");
        service.rate(3, 2, 9, "Любимый фильм");
        service.rate(1, 3, 8, "Хороший сериал");
        service.rate(2, 3, 9, "Отлично");
        service.rate(4, 3, 9, "Советую");
        service.rate(2, 5, 10, "Классика");
        service.rate(3, 5, 9, "Великолепно");
        service.rate(4, 7, 10, "Лучшая RPG");
        return library;
    }
}
