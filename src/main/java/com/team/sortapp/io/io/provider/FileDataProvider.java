package com.team.sortapp.io.io.provider;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.exception.ValidationException;
import com.team.sortapp.model.Student;
import com.team.sortapp.io.io.util.CollectionFiller;
import com.team.sortapp.io.io.util.StudentValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class FileDataProvider {
    private final String filePath;

    public FileDataProvider(String filePath) {
        this.filePath = filePath;
    }

    public CustomList<Student> provideFromFile() {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {

            // Используем атомарный счетчик для вывода номеров строк при ошибках
            final int[] lineCounter = {0};

            Stream<Student> studentStream = lines.map(line -> {
                lineCounter[0]++;
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    return null; // Пропускаем пустые строки и комментарии
                }

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println("️Предупреждение: Строка " + lineCounter[0] + " '" + line + "' не соответствует формату (нужно 3 значения через запятую). Пропущена.");
                    return null;
                }

                try {
                    return StudentValidator.parseAndValidate(parts[0], parts[1], parts[2]);
                } catch (ValidationException e) {
                    System.out.println("Предупреждение: Ошибка в строке " + lineCounter[0] + ": " + e.getMessage() + ". Пропущена.");
                    return null;
                }
            }).filter(Objects::nonNull);

            return CollectionFiller.fillFromStream(studentStream);

        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + filePath + ". Ошибка: " + e.getMessage());
        }
    }
}