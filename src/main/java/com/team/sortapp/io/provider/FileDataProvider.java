package com.team.sortapp.io.provider;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.exception.ValidationException;
import com.team.sortapp.model.Student;
import com.team.sortapp.io.util.CollectionFiller;
import com.team.sortapp.io.util.StudentValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public class FileDataProvider implements DataProvider {
    private final Path filePath;

    public FileDataProvider(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CustomList<Student> provide(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count не может быть отрицательным: " + count);
        }

        try (Stream<String> lines = Files.lines(filePath)) {

            // Используем атомарный счетчик для вывода номеров строк при ошибках
            final int[] lineCounter = {0};

            Stream<Student> studentStream = lines
                    .map(line -> {
                        lineCounter[0]++;
                        if (line.trim().isEmpty() || line.startsWith("#")) {
                            return null; // Пропускаем пустые строки и комментарии
                        }

                        String[] parts = line.split(",");
                        if (parts.length != 3) {
                            System.out.println("Предупреждение: Строка " + lineCounter[0]
                                    + " '" + line + "' не соответствует формату "
                                    + "(нужно 3 значения через запятую). Пропущена.");
                            return null;
                        }

                        try {
                            return StudentValidator.parseAndValidate(parts[0], parts[1], parts[2]);
                        } catch (ValidationException e) {
                            System.out.println("Предупреждение: Ошибка в строке "
                                    + lineCounter[0] + ": " + e.getMessage() + ". Пропущена.");
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .limit(count); // count — верхний лимит реально прочитанных студентов

            return CollectionFiller.fillFromStream(studentStream);

        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + filePath + ". Ошибка: " + e.getMessage());
        }
    }
}