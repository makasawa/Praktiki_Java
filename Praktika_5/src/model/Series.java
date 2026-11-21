package model;

public class Series extends MediaItem {
    private int seasons;
    private int episodes;

    public Series(int id, String title, int year, Genre genre, int duration, int seasons, int episodes, String[] tags) {
        super(id, title, year, genre, duration, tags);
        if (seasons < 1 || episodes < 1) {
            throw new IllegalArgumentException("Число сезонов и серий должно быть положительным");
        }
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public MediaType getType() {
        return MediaType.SERIES;
    }

    public String getDetails() {
        return seasons + " сез., " + episodes + " сер.";
    }
}
