import java.util.Arrays;

public class MediaLibrary {
    private MediaItem[] items = new MediaItem[8];
    private int count;
    private User[] users = new User[8];
    private int userCount;
    private Playlist[] playlists = new Playlist[8];
    private int playlistCount;

    public void add(MediaItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Пустой элемент");
        }
        if (find(item.getId()) != null) {
            throw new IllegalArgumentException("Дубликат ID");
        }
        if (count == items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }
        items[count++] = item;
    }

    public void addUser(User user) {
        if (userCount == users.length) {
            users = Arrays.copyOf(users, users.length * 2);
        }
        users[userCount++] = user;
    }

    public void addPlaylist(Playlist playlist) {
        if (playlistCount == playlists.length) {
            playlists = Arrays.copyOf(playlists, playlists.length * 2);
        }
        playlists[playlistCount++] = playlist;
    }

    public MediaItem find(int id) {
        for (int i = 0; i < count; i++) {
            if (items[i].getId() == id) {
                return items[i];
            }
        }
        return null;
    }

    public User findUser(int id) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        return null;
    }

    public void remove(int id) {
        for (int i = 0; i < count; i++) {
            if (items[i].getId() == id) {
                for (int j = i; j < count - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[--count] = null;
                return;
            }
        }
        throw new IllegalArgumentException("Материал не найден");
    }

    public void showAll() {
        if (count == 0) {
            System.out.println("Каталог пуст");
        }
        for (int i = 0; i < count; i++) {
            System.out.println(items[i]);
        }
    }

    public void search(String query) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (items[i].getTitle().toLowerCase().contains(query.toLowerCase())

                    || items[i].getGenre().name().equalsIgnoreCase(query)) {
                System.out.println(items[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Ничего не найдено");
        }
    }

    public void rate(int itemId, int userId, int score, String review) {
        MediaItem item = find(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Материал не найден");
        }
        if (findUser(userId) == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }
        item.addRating(userId, score, review);
    }

    public void favorite(int userId, int itemId) {
        User user = findUser(userId);
        if (user == null || find(itemId) == null) {
            throw new IllegalArgumentException("Пользователь или материал не найден");
        }
        user.addFavorite(itemId);
    }

    public void createPlaylist(String name) {
        if (playlistCount == playlists.length) {
            playlists = Arrays.copyOf(playlists, playlists.length * 2);
        }
        playlists[playlistCount++] = new Playlist(name);
    }

    public void addToPlaylist(String name, int id) {
        if (find(id) == null) {
            throw new IllegalArgumentException("Материал не найден");
        }
        for (int i = 0; i < playlistCount; i++) {
            if (playlists[i].getName().equalsIgnoreCase(name)) {
                playlists[i].add(id);
                return;
            }
        }
        throw new IllegalArgumentException("Плейлист не найден");
    }

    public void showPlaylists() {
        for (int i = 0; i < playlistCount; i++) {
            System.out.println(playlists[i]);
            for (int id : playlists[i].getItemIds()) {
                System.out.println("  " + find(id));
            }
        }
    }

    public void reports() {
        int duration = 0;
        MediaItem best = null;
        for (int i = 0; i < count; i++) {
            duration += items[i].getDurationMinutes();
            if (best == null || items[i].averageRating() > best.averageRating()) {
                best = items[i];
            }
        }
        System.out.println("Материалов: " + count + "; суммарная длительность: " + duration + " мин.");
        System.out.println("Лучший рейтинг: " + (best == null ? "нет данных" : best));
    }
}
