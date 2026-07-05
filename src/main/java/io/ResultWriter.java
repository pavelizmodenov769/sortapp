package io;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultWriter {
    public static void appendToFile(String filePath, CustomList<Student> list, String operationLabel) {
        // Открываем FileWriter в режиме APPEND (второй параметр true)
        try (FileWriter fw = new FileWriter(filePath, true); PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            pw.println(" БЛОК: " + operationLabel + " ===");
            pw.println(" Время записи: " + timestamp);

            for (Student student : list) {
                // Записываем в формате, пригодном для обратного чтения: группа, балл, зачетка
                pw.printf("%d,%.2f,%d%n", student.getGroup(), student.getAverageScore(), student.getCreditBook());
            }
            pw.println(); // Разделительный пустой отступ между блоками данных

        } catch (IOException e) {
            System.out.println(" Ошибка при записи результатов в файл: " + e.getMessage());
        }
    }
}