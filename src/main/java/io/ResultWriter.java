package io;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultWriter {
    private final Path outputPath;

    public ResultWriter(Path outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Дописывает (APPEND) список студентов в конец файла.
     */
    public void appendToFile(CustomList<Student> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("[Предупреждение] Нет данных для записи.");
            return;
        }

        // Открываем в режиме добавления APPEND. Если файла нет - CREATE его.
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            // Пишем метку времени текущего блока (Текущий год в системе: 2026)
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("# === Отсортировано (Добавление): " + timestamp + " ===");
            writer.newLine();

            // Запись сущностей
            for (Student student : students) {
                String line = String.format("%d,%.1f,%d", student.getGroup(), student.getAverageScore(), student.getCreditBook());
                writer.write(line.replace(',', '.')); // Защита от локали
                writer.newLine();
            }
            System.out.println("[Успех] Данные успешно добавлены в файл: " + outputPath.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("[Ошибка записи в файл]: " + e.getMessage());
        }
    }
}