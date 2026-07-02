package io;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileDataProvider implements DataProvider {
    private final Path filePath;

    public FileDataProvider(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CustomList<Student> provide(int count) {
        if (count < 0) throw new IllegalArgumentException("Размер не может быть отрицательным.");
        if (!Files.exists(filePath)) {
            System.out.println("[Ошибка] Файл не найден: " + filePath.toAbsolutePath());
            return CollectionFiller.fillFromStream(Stream.empty());
        }

        AtomicInteger lineCounter = new AtomicInteger(0);

        try (Stream<String> lines = Files.lines(filePath)) {
            Stream<Student> studentStream = lines
                    .map(line -> {
                        int currentLine = lineCounter.incrementAndGet();
                        String trimmed = line.trim();

                        // Пропуск комментариев и пустот
                        if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                            return null;
                        }

                        String[] tokens = trimmed.split(",");
                        if (tokens.length != 3) {
                            System.out.println("[Предупреждение] Строка " + currentLine + " пропущена: ожидалось 3 поля, получено " + tokens.length);
                            return null;
                        }

                        try {
                            return StudentValidator.validateAndBuild(tokens[0], tokens[1], tokens[2]);
                        } catch (RuntimeException e) {
                            System.out.println("[Предупреждение] Строка " + currentLine + " пропущена: " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .limit(count);

            return CollectionFiller.fillFromStream(studentStream);

        } catch (IOException e) {
            System.out.println("[Критическая ошибка ввода-вывода]: " + e.getMessage());
            return CollectionFiller.fillFromStream(Stream.empty());
        }
    }

    @Override
    public String getName() {
        return "Чтение данных из файла CSV";
    }
}