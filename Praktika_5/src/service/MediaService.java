package service;

import exception.MediaLibraryException;
import model.Genre;
import model.MediaItem;
import model.Rating;
import model.User;
import storage.MediaLibrary;

public class MediaService {
    private MediaLibrary library;
    private Logger logger;

    public MediaService(MediaLibrary library, Logger logger) {
        this.library = library;
        this.logger = logger;
    }

    public MediaLibrary getLibrary() {
        return library;
    }

    public void add(MediaItem item) {
        library.addItem(item);
        logger.log("Добавлен материал " + item.getId());
    }

    public void delete(int id) throws MediaLibraryException {
        library.removeItem(id);
        logger.log("Удалён материал " + id);
    }

    public void rate(int userId, int itemId, int score, String review) {
        library.addRating(userId, itemId, score, review);
        logger.log("Оценка: user=" + userId + ", item=" + itemId + ", score=" + score);
    }

    public void favorite(int userId, int itemId) {
        User user = library.findUser(userId);
        if (user == null || library.findItem(itemId) == null) {
            throw new IllegalArgumentException("Пользователь или материал не найден");
        }
        user.addFavorite(itemId);
        logger.log("Избранное: " + userId + " -> " + itemId);
    }

    public MediaItem[] sorted(MediaComparator comparator) {
        MediaItem[] items = new MediaItem[library.getItems().size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = (MediaItem) library.getItems().get(i);
        }
        MediaSorter.sort(items, comparator);
        return items;
    }

    public MediaItem[] search(String title, Genre genre, Integer year, String tag) {
        MediaItem[] matches = new MediaItem[library.getItems().size()];
        int count = 0;
        for (int i = 0; i < library.getItems().size(); i++) {
            MediaItem item = (MediaItem) library.getItems().get(i);
            if (title != null && !item.getTitle().toLowerCase().contains(title.toLowerCase())) {
                continue;
            }
            if (genre != null && item.getGenre() != genre) {
                continue;
            }
            if (year != null && item.getYear() != year) {
                continue;
            }
            if (tag != null && !item.hasTag(tag)) {
                continue;
            }
            matches[count++] = item;
        }
        MediaItem[] result = new MediaItem[count];
        System.arraycopy(matches, 0, result, 0, count);
        return result;
    }

    public MediaItem[] recommendations(int userId) {
        User target = library.findUser(userId);
        if (target == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }
        int bestUserId = -1;
        int bestCommon = 0;
        for (int i = 0; i < library.getUsers().size(); i++) {
            User other = (User) library.getUsers().get(i);
            if (other.getId() == userId) {
                continue;
            }
            int common = 0;
            for (int j = 0; j < library.getRatings().size(); j++) {
                Rating rating = (Rating) library.getRatings().get(j);
                if (rating.getUserId() == userId) {
                    for (int k = 0; k < library.getRatings().size(); k++) {
                        Rating otherRating = (Rating) library.getRatings().get(k);
                        if (otherRating.getUserId() == other.getId()
                               
                                && otherRating.getItemId() == rating.getItemId()
                               
                                && Math.abs(otherRating.getScore() - rating.getScore()) <= 2) {
                            common++;
                        }
                    }
                }
            }
            if (common > bestCommon) {
                bestCommon = common;
                bestUserId = other.getId();
            }
        }
        MediaItem[] candidates = new MediaItem[library.getItems().size()];
        int count = 0;
        if (bestUserId != -1) {
            for (int i = 0; i < library.getRatings().size(); i++) {
                Rating rating = (Rating) library.getRatings().get(i);
                if (rating.getUserId() == bestUserId && rating.getScore() >= 7
                       
                        && !target.isFavorite(rating.getItemId())
                       
                        && hasNotRated(userId, rating.getItemId())) {
                    MediaItem item = library.findItem(rating.getItemId());
                    if (item != null) {
                        candidates[count++] = item;
                    }
                }
            }
        }
        if (count == 0) {
            for (int i = 0; i < library.getItems().size(); i++) {
                MediaItem item = (MediaItem) library.getItems().get(i);
                if (library.average(item.getId()) >= 7 && !target.isFavorite(item.getId())
                       
                        && hasNotRated(userId, item.getId())) {
                    candidates[count++] = item;
                }
            }
        }
        MediaItem[] result = new MediaItem[count];
        System.arraycopy(candidates, 0, result, 0, count);
        return result;
    }

    private boolean hasNotRated(int userId, int itemId) {
        for (int i = 0; i < library.getRatings().size(); i++) {
            Rating rating = (Rating) library.getRatings().get(i);
            if (rating.getUserId() == userId && rating.getItemId() == itemId) {
                return false;
            }
        }
        return true;
    }
}
