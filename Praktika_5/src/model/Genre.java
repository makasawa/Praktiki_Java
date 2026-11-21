package model;

public enum Genre {
    ACTION("Боевик"),
    COMEDY("Комедия"),
    DRAMA("Драма"),
    FANTASY("Фэнтези"),
    SCI_FI("Фантастика"),
    HORROR("Ужасы"),
    ROCK("Рок"),
    POP("Поп"),
    JAZZ("Джаз"),
    ADVENTURE("Приключения"),
    STRATEGY("Стратегия");

    private final String title;

    Genre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title;
    }
}
