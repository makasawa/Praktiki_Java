package model;

public class Rating {
    private final int userId;
    private final int itemId;
    private int score;
    private String review;

    public Rating(int userId, int itemId, int score, String review) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException("Оценка должна быть от 1 до 10");
        }
        this.userId = userId;
        this.itemId = itemId;
        this.score = score;
        this.review = review == null ? "" : review;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getScore() {
        return score;
    }

    public String getReview() {
        return review;
    }

    public String toString() {
        return "Пользователь " + userId + " оценил #" + itemId + " на " + score + "/10: " + review;
    }
}
