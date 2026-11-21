public class VideoGame extends MediaItem {
    private String platform;
    private String developer;

    public VideoGame(int id, String title, int year, Genre genre, int duration, String platform, String developer, String[] tags) {
        super(id, title, year, genre, duration, tags);
        this.platform = platform;
        this.developer = developer;
    }

    public String getType() {
        return "Видеоигра";
    }

    public String toString() {
        return super.toString() + " | платформа: " + platform + ", разработчик: " + developer;
    }
}
