package model;

import java.util.Arrays;

public abstract class MediaItem {
    private final int id;
    private String title;
    private int year;
    private Genre genre;
    private int durationMinutes;
    private String[] tags;

    public MediaItem(int id, String title, int year, Genre genre, int durationMinutes, String[] tags) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным");
        }
        setTitle(title);
        setYear(year);
        setGenre(genre);
        setDurationMinutes(durationMinutes);
        this.id = id;
        this.tags = tags == null ? new String[0] : Arrays.copyOf(tags, tags.length);
    }

    public abstract MediaType getType();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String[] getTags() {
        return Arrays.copyOf(tags, tags.length);
    }

    public void setTitle(String v) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException("Название пустое");
        }
        title = v.trim();
    }

    public void setYear(int v) {
        if (v < 1888 || v > 2100) {
            throw new IllegalArgumentException("Некорректный год");
        }
        year = v;
    }

    public void setGenre(Genre v) {
        if (v == null) {
            throw new IllegalArgumentException("Жанр не задан");
        }
        genre = v;
    }

    public void setDurationMinutes(int v) {
        if (v < 0) {
            throw new IllegalArgumentException("Длительность не может быть отрицательной");
        }
        durationMinutes = v;
    }

    public boolean hasTag(String tag) {
        for (String currentTag : tags) {
            if (currentTag.equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    public abstract String getDetails();

    public String toString() {
        return String.format("#%-3d %-10s %-26s %4d %-14s %5d мин. %s", id, getType().getTitle(), title, year, genre, durationMinutes, getDetails());
    }

    public boolean equals(Object other) {
        return other instanceof MediaItem && ((MediaItem) other).id == id;
    }

    public int hashCode() {
        return id;
    }
}
