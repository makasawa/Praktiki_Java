import java.util.Arrays;

public class Playlist {
    private String name;
    private int[] itemIds;
    private int count;

    public Playlist(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название плейлиста пустое");
        }
        this.name = name;
        itemIds = new int[4];
    }

    public void add(int id) {
        for (int i = 0; i < count; i++) {
            if (itemIds[i] == id) {
                return;
            }
        }
        if (count == itemIds.length) {
            itemIds = Arrays.copyOf(itemIds, itemIds.length * 2);
        }
        itemIds[count++] = id;
    }

    public String getName() {
        return name;
    }

    public int[] getItemIds() {
        return Arrays.copyOf(itemIds, count);
    }

    public String toString() {
        return name + " — элементов: " + count;
    }
}
