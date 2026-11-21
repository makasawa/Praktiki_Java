package model;

public enum MediaType {
    MOVIE("Фильм", 1),
    SERIES("Сериал", 2),
    ALBUM("Альбом", 3),
    VIDEO_GAME("Видеоигра", 4);

    private final String title;
    private final int code;

    MediaType(String title, int code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public int getCode() {
        return code;
    }

    public static MediaType fromCode(int code) {
        for (MediaType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Неизвестный тип");
    }
}
