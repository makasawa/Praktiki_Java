public class Series extends MediaItem {
    private int seasons;
    private int episodes;

    public Series(int id, String title, int year, Genre genre, int duration, int seasons, int episodes, String[] tags) {
        super(id, title, year, genre, duration, tags);
        if (seasons < 1 || episodes < 1) {
            throw new IllegalArgumentException("Некорректное число сезонов/серий");
        }
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public String getType() {
        return "Сериал";
    }

    public String toString() {
        return super.toString() + " | сезонов: " + seasons + ", серий: " + episodes;
    }
}
