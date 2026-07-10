package com.team.sortapp.io.util;

import com.team.sortapp.exception.ValidationException;
import com.team.sortapp.model.Student;

public class StudentValidator {

    public static Student parseAndValidate(String rawGroup, String rawScore, String rawGradeBook) {
        int group;
        double score;
        long gradeBook;

        try {
            group = Integer.parseInt(rawGroup.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Номер группы должен быть целым числом. Получено: '" + rawGroup + "'");
        }

        try {
            score = Double.parseDouble(rawScore.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Средний балл должен быть вещественным числом. Получено: '" + rawScore + "'");
        }

        try {
            gradeBook = Long.parseLong(rawGradeBook.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Номер зачетной книжки должен быть целым числом. Получено: '" + rawGradeBook + "'");
        }

        // Проверка бизнес-правил, согласованных с Dev 1
        if (group <= 0) {
            throw new ValidationException("Номер группы должен быть больше 0. Получено: " + group);
        }
        if (score < 0.0 || score > 10.0) {
            throw new ValidationException("Средний балл должен быть в диапазоне [0; 10]. Получено: " + score);
        }
        if (gradeBook <= 0) {
            throw new ValidationException("Номер зачетной книжки должен быть больше 0. Получено: " + gradeBook);
        }

        // Собираем объект через Builder от Dev 1
        return new Student.StudentBuilder()
                .setGroup(group)
                .setAverageScore(score)
                .setCreditBook(gradeBook)
                .build();
    }
}