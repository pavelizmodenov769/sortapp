package com.team.sortapp.io;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ResultWriter {
    public static void appendToFile(String filePath, CustomList<Student> list, String operationLabel) {
        try (FileWriter fw = new FileWriter(filePath, true); PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            pw.println("=== БЛОК: " + operationLabel + " ===");
            pw.println(" Время записи: " + timestamp);

            for (Student student : list) {
                pw.printf(Locale.US, "%d,%.2f,%d%n", student.getGroup(), student.getAverageScore(), student.getCreditBook());
            }
            pw.println();

        } catch (IOException e) {
            System.out.println(" Ошибка при записи результатов в файл: " + e.getMessage());
        }
    }

    public static void appendToFile(Path path, CustomList<Student> students) {
        appendToFile(path.toString(), students, "Результаты сортировки");
    }
}
