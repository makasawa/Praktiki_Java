package model;

import java.util.Arrays;

public class Playlist {
    private final int id;
    private String name;
    private int[] itemIds = new int[4];
    private int count;

    public Playlist(int id, String name) {
        if (id <= 0 || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Некорректный плейлист");
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

    public void add(int itemId) {
        for (int i = 0; i < count; i++) {
            if (itemIds[i] == itemId) {
                return;
            }
        }
        if (count == itemIds.length) {
            itemIds = Arrays.copyOf(itemIds, itemIds.length * 2);
        }
        itemIds[count++] = itemId;
    }

    public int[] getItemIds() {
        return Arrays.copyOf(itemIds, count);
    }

    public String toString() {
        return "#" + id + " " + name + " (" + count + " элементов)";
    }
}
