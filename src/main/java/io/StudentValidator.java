package io;

import com.team.sortapp.model.Student;

public class StudentValidator  {
    public static Student validateAndBuild(String rawGroup, String rawScore, String rawGradeBook) {
    int group;
    try {
        group = Integer.parseInt(rawGroup.trim());
    } catch (NumberFormatException e) {
        throw new RuntimeException("Номер группы должен быть целым числом. Получено: '" + rawGroup + "'");
    }

    double score;
    try {
        score = Double.parseDouble(rawScore.trim());
    } catch (NumberFormatException e) {
        throw new RuntimeException("Средний балл должен быть дробным или целым числом. Получено: '" + rawScore + "'");
    }

    long gradeBook;
    try {
        gradeBook = Long.parseLong(rawGradeBook.trim());
    } catch (NumberFormatException e) {
        throw new RuntimeException("Номер зачетной книжки должен быть целым числом. Получено: '" + rawGradeBook + "'");
    }

    // Передаем распарсенные данные в Builder, где сработает проверка диапазонов (>0, [0;10])
    try {
        return new Student.StudentBuilder()
                .setGroup(group)
                .setAverageScore(score)
                .setCreditBook(gradeBook)
                .build();
    } catch (RuntimeException e) {
        // Перехватываем ValidationException из домена и пробрасываем дальше
        throw new RuntimeException("Ошибка валидации диапазонов: " + e.getMessage());
    }
}
}