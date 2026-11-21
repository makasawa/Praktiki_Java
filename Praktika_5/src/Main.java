import java.io.IOException;
import service.DemoData;
import service.Logger;
import service.MediaService;
import storage.MediaFileStorage;
import storage.MediaLibrary;
import ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        Logger logger = new Logger("log.txt");
        MediaFileStorage files = new MediaFileStorage("media.csv");
        MediaLibrary library;
        try {
            library = files.exists() ? files.load() : DemoData.create(logger);
        } catch (IOException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
            library = DemoData.create(logger);
        }
        new ConsoleUI(new MediaService(library, logger), files, logger).run();
    }
}
