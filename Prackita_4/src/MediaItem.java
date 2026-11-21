import java.util.Arrays;

public abstract class MediaItem {
    private int id;
    private String title;
    private int year;
    private Genre genre;
    private int durationMinutes;
    private String[] tags;
    private Rating[] ratings;
    private int ratingCount;

    public MediaItem(int id, String title, int year, Genre genre, int durationMinutes, String[] tags) {
        if (id <= 0 || title == null || title.trim().isEmpty() || year < 1888 || durationMinutes < 0 || genre == null) {
            throw new IllegalArgumentException("Некорректные данные медиаматериала");
        }
        this.id = id;
        this.title = title.trim();
        this.year = year;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.tags = tags == null ? new String[0] : Arrays.copyOf(tags, tags.length);
        ratings = new Rating[4];
    }

    public abstract String getType();

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

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Пустое название");
        }
        this.title = title.trim();
    }

    public void setYear(int year) {
        if (year < 1888) {
            throw new IllegalArgumentException("Некорректный год");
        }
        this.year = year;
    }

    public void addRating(int userId, int score, String review) {
        for (int i = 0; i < ratingCount; i++) {
            if (ratings[i].getUserId() == userId) {
                throw new IllegalArgumentException("Пользователь уже оценил этот материал");
            }
        }
        if (ratingCount == ratings.length) {
            ratings = Arrays.copyOf(ratings, ratings.length * 2);
        }
        ratings[ratingCount++] = new Rating(userId, score, review);
    }

    public double averageRating() {
        if (ratingCount == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < ratingCount; i++) {
            sum += ratings[i].getScore();
        }
        return (double) sum / ratingCount;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public Rating[] getRatings() {
        return Arrays.copyOf(ratings, ratingCount);
    }

    public boolean hasTag(String tag) {
        for (String currentTag : tags) {
            if (currentTag.equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return String.format("#%d %-12s %-25s %d %-12s %d мин. рейтинг %.1f (%d)", id, getType(), title, year, genre, durationMinutes, averageRating(), ratingCount);
    }

    public boolean equals(Object other) {
        return other instanceof MediaItem && ((MediaItem) other).id == id;
    }

    public int hashCode() {
        return id;
    }
}
