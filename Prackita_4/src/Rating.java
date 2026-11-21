public class Rating {
    private final int userId;
    private int score;
    private String review;

    public Rating(int userId, int score, String review) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException("Оценка должна быть от 1 до 10");
        }
        this.userId = userId;
        this.score = score;
        this.review = review == null ? "" : review;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public String getReview() {
        return review;
    }

    public void update(int score, String review) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException("Оценка должна быть от 1 до 10");
        }
        this.score = score;
        this.review = review == null ? "" : review;
    }

    public String toString() {
        return "Пользователь #" + userId + ": " + score + "/10 — " + review;
    }
}
