package io;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDataProvider implements DataProvider {
    private final Path filePath;

    public FileDataProvider(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CustomList<Student> provide(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Размер коллекции не может быть отрицательным: " + count);
        }

        CustomList<Student> list = new CustomArrayList<>();
        if (count == 0 || filePath == null || !Files.exists(filePath)) {
            return list;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null && list.size() < count) {
                lineNumber++;
                line = line.trim();

                // Пропуск пустых строк и комментариев (начинающихся с #)
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    String[] tokens = line.split(",");
                    if (tokens.length != 3) {
                        System.out.println("[Предупреждение] Строка " + lineNumber + " пропущена: неверное количество полей (ожидалось 3).");
                        continue;
                    }

                    // Парсинг данных с удалением пробелов по краям (trim)
                    int group = Integer.parseInt(tokens[0].trim());
                    double score = Double.parseDouble(tokens[1].trim());
                    long gradeBook = Long.parseLong(tokens[2].trim());

                    // Создание объекта через Билдер (внутри сработает бизнес-валидация)
                    Student student = new Student.StudentBuilder()
                            .setGroup(group)
                            .setAverageScore(score)
                            .setCreditBook(gradeBook)
                            .build();

                    list.add(student);

                } catch (NumberFormatException e) {
                    System.out.println("[Предупреждение] Строка " + lineNumber + " пропущена: ошибка парсинга чисел. " + e.getMessage());
                } catch (RuntimeException e) {
                    // Перехватывает ValidationException из StudentBuilder.build()
                    System.out.println("[Предупреждение] Строка " + lineNumber + " пропущена: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("[Ошибка] Не удалось прочитать файл: " + e.getMessage());
        }

        return list;
    }

    @Override
    public String getName() {
        return "Загрузить данные из файла (.csv / .txt)";
    }
}