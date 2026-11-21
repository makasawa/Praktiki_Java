public class Movie extends MediaItem {
    private String director;

    public Movie(int id, String title, int year, Genre genre, int duration, String director, String[] tags) {
        super(id, title, year, genre, duration, tags);
        if (director == null || director.trim().isEmpty()) {
            throw new IllegalArgumentException("Укажите режиссёра");
        }
        this.director = director;
    }

    public String getType() {
        return "Фильм";
    }

    public String toString() {
        return super.toString() + " | режиссёр: " + director;
    }
}
