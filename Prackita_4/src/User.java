import java.util.Arrays;

public class User {
    private int id;
    private String name;
    private int[] favorites;
    private int favoriteCount;

    public User(int id, String name) {
        if (id <= 0 || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Некорректный пользователь");
        }
        this.id = id;
        this.name = name;
        favorites = new int[4];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addFavorite(int itemId) {
        if (isFavorite(itemId)) {
            return;
        }
        if (favoriteCount == favorites.length) {
            favorites = Arrays.copyOf(favorites, favorites.length * 2);
        }
        favorites[favoriteCount++] = itemId;
    }

    public boolean isFavorite(int itemId) {
        for (int i = 0; i < favoriteCount; i++) {
            if (favorites[i] == itemId) {
                return true;
            }
        }
        return false;
    }

    public int[] getFavorites() {
        return Arrays.copyOf(favorites, favoriteCount);
    }

    public String toString() {
        return "#" + id + " " + name + " (избранное: " + favoriteCount + ")";
    }
}
