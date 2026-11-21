package model;

public class Album extends MediaItem {
    private String artist;
    private int tracks;

    public Album(int id, String title, int year, Genre genre, int duration, String artist, int tracks, String[] tags) {
        super(id, title, year, genre, duration, tags);
        if (artist == null || artist.trim().isEmpty() || tracks < 1) {
            throw new IllegalArgumentException("Некорректные данные альбома");
        }
        this.artist = artist;
        this.tracks = tracks;
    }

    public MediaType getType() {
        return MediaType.ALBUM;
    }

    public String getDetails() {
        return artist + ", треков: " + tracks;
    }
}
