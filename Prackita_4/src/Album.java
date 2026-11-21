public class Album extends MediaItem {
    private String artist;
    private int tracks;

    public Album(int id, String title, int year, Genre genre, int duration, String artist, int tracks, String[] tags) {
        super(id, title, year, genre, duration, tags);
        if (artist == null || artist.trim().isEmpty() || tracks < 1) {
            throw new IllegalArgumentException("Некорректный исполнитель/число треков");
        }
        this.artist = artist;
        this.tracks = tracks;
    }

    public String getType() {
        return "Альбом";
    }

    public String toString() {
        return super.toString() + " | исполнитель: " + artist + ", треков: " + tracks;
    }
}
