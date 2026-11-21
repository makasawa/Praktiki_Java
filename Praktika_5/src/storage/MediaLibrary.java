package storage;

import exception.DuplicateItemException;
import exception.InvalidRatingException;
import exception.MediaLibraryException;
import java.util.Arrays;
import model.MediaItem;
import model.Playlist;
import model.Rating;
import model.User;

public class MediaLibrary {
    private EntityStorage items = new EntityStorage();
    private EntityStorage users = new EntityStorage();
    private EntityStorage ratings = new EntityStorage();
    private EntityStorage playlists = new EntityStorage();

    public EntityStorage getItems() {
        return items;
    }

    public EntityStorage getUsers() {
        return users;
    }

    public EntityStorage getRatings() {
        return ratings;
    }

    public EntityStorage getPlaylists() {
        return playlists;
    }

    public void addItem(MediaItem item) {
        if (findItem(item.getId()) != null) {
            throw new DuplicateItemException("Элемент с таким ID уже существует");
        }
        items.add(item);
    }

    public MediaItem findItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            MediaItem item = (MediaItem) items.get(i);
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public User findUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            User user = (User) users.get(i);
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        if (findUser(user.getId()) != null) {
            throw new DuplicateItemException("Пользователь уже существует");
        }
        users.add(user);
    }

    public void removeItem(int id) throws MediaLibraryException {
        for (int i = 0; i < items.size(); i++) {
            if (((MediaItem) items.get(i)).getId() == id) {
                items.removeAt(i);
                return;
            }
        }
        throw new MediaLibraryException("Материал не найден");
    }

    public void addRating(int userId, int itemId, int score, String review) {
        if (score < 1 || score > 10) {
            throw new InvalidRatingException("Оценка должна быть от 1 до 10");
        }
        if (findUser(userId) == null || findItem(itemId) == null) {
            throw new IllegalArgumentException("Пользователь или материал не найден");
        }
        for (int i = 0; i < ratings.size(); i++) {
            Rating rating = (Rating) ratings.get(i);
            if (rating.getUserId() == userId && rating.getItemId() == itemId) {
                throw new InvalidRatingException("Пользователь уже оценивал этот материал");
            }
        }
        ratings.add(new Rating(userId, itemId, score, review));
    }

    public double average(int itemId) {
        int sum = 0;
        int count = 0;
        for (int i = 0; i < ratings.size(); i++) {
            Rating rating = (Rating) ratings.get(i);
            if (rating.getItemId() == itemId) {
                sum += rating.getScore();
                count++;
            }
        }
        return count == 0 ? 0 : (double) sum / count;
    }

    public int ratingCount(int itemId) {
        int count = 0;
        for (int i = 0; i < ratings.size(); i++) {
            if (((Rating) ratings.get(i)).getItemId() == itemId) {
                count++;
            }
        }
        return count;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public Playlist findPlaylist(String name) {
        for (int i = 0; i < playlists.size(); i++) {
            Playlist playlist = (Playlist) playlists.get(i);
            if (playlist.getName().equalsIgnoreCase(name)) {
                return playlist;
            }
        }
        return null;
    }

    public String toString() {
        return "Медиатека: " + items.size() + " материалов, " + users.size() + " пользователей, " + ratings.size() + " оценок";
    }
}
