package ui;

import java.util.Scanner;

public class Input {
    private Scanner scanner = new Scanner(System.in);

    public String text(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int integer(String prompt, int min, int max) {
        while (true) {
            String line = text(prompt);
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException exception) {
            }
            System.out.println("Введите целое число от " + min + " до " + max);
        }
    }

    public int integer(String prompt) {
        while (true) {
            String line = text(prompt);
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException exception) {
                System.out.println("Введите целое число");
            }
        }
    }

    public boolean yesNo(String prompt) {
        return integer(prompt + " (1-да, 0-нет): ", 0, 1) == 1;
    }

    public GenreChoice genre() {
        model.Genre[] genres = model.Genre.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + ". " + genres[i]);
        }
        return new GenreChoice(genres[integer("Жанр: ", 1, genres.length) - 1]);
    }

    public static class GenreChoice {
        private model.Genre value;

        public GenreChoice(model.Genre value) {
            this.value = value;
        }

        public model.Genre get() {
            return value;
        }
    }
}
