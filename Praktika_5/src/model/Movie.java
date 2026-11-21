package model;

public class Movie extends MediaItem {
    private String director;

    public Movie(int id, String title, int year, Genre genre, int duration, String director, String[] tags) {
        super(id, title, year, genre, duration, tags);
        if (director == null || director.trim().isEmpty()) {
            throw new IllegalArgumentException("Режиссёр обязателен");
        }
        this.director = director;
    }

    public MediaType getType() {
        return MediaType.MOVIE;
    }

    public String getDetails() {
        return "Режиссёр: " + director;
    }

    public String getDirector() {
        return director;
    }
}
