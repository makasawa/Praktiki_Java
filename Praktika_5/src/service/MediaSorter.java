package service;

import model.MediaItem;

public class MediaSorter {

    public static class ByTitle implements MediaComparator {

        public int compare(MediaItem first, MediaItem second) {
            return first.getTitle().compareToIgnoreCase(second.getTitle());
        }
    }

    public static class ByYear implements MediaComparator {

        public int compare(MediaItem first, MediaItem second) {
            return first.getYear() - second.getYear();
        }
    }

    public static void sort(MediaItem[] items, MediaComparator comparator) {
        for (int i = 1; i < items.length; i++) {
            MediaItem item = items[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(items[j], item) > 0) {
                items[j + 1] = items[j];
                j--;
            }
            items[j + 1] = item;
        }
    }
}
