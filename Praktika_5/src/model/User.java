package model;

import java.util.Arrays;

public class User {
    private final int id;
    private String name;
    private int[] favorites = new int[4];
    private int favoriteCount;

    public User(int id, String name) {
        if (id <= 0 || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Некорректный пользователь");
        }
        this.id = id;
        this.name = name.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addFavorite(int id) {
        if (isFavorite(id)) {
            return;
        }
        if (favoriteCount == favorites.length) {
            favorites = Arrays.copyOf(favorites, favorites.length * 2);
        }
        favorites[favoriteCount++] = id;
    }

    public boolean isFavorite(int id) {
        for (int i = 0; i < favoriteCount; i++) {
            if (favorites[i] == id) {
                return true;
            }
        }
        return false;
    }

    public int[] getFavorites() {
        return Arrays.copyOf(favorites, favoriteCount);
    }

    public String toString() {
        return "#" + id + " " + name + "; избранное: " + favoriteCount;
    }
}
