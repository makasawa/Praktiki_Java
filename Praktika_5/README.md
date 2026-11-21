# Практическая работа 5 — вариант 28 «Медиатека с рекомендациями»

Расширенная реализация по требованиям документа: абстрактная иерархия `MediaItem`,
интерфейс сравнения с двумя сортировками, два enum с полями и методами, собственные
массивные хранилища, три исключения, CSV импорт/экспорт, журнал `log.txt`, аналитика,
демонстрационные данные и меню с подменю. Generics, коллекции, Stream API, лямбды
и многопоточность не используются.

Запуск:
```bash
javac --release 8 -encoding UTF-8 -d out src/Main.java src/model/*.java src/service/*.java src/storage/*.java src/exception/*.java src/ui/*.java
java -Dfile.encoding=UTF-8 -cp out Main
```
