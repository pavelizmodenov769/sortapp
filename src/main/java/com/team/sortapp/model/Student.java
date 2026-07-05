package com.team.sortapp.model;

import com.team.sortapp.exception.ValidationException;

import java.util.Objects;

public class Student {
    private final int group;
    private final double averageScore;
    private final long creditBook;

    private Student(StudentBuilder builder) {
        this.group = builder.group;
        this.averageScore = builder.averageScore;
        this.creditBook = builder.creditBook;
    }

    public int getGroup() {
        return group;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public long getCreditBook() {
        return creditBook;
    }

    @Override
    public String toString() {
        return "группа - " + group +
                "; средний балл - " + averageScore +
                "; номер зачетной книжки - " + creditBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(averageScore, student.averageScore) == 0 &&
                Objects.equals(group, student.group) &&
                Objects.equals(creditBook, student.creditBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, averageScore, creditBook);
    }

    // Builder for Student
    // Обёртки, чтобы отличать "не установлено"

    public static class StudentBuilder {
        private Integer group;
        private Double averageScore;
        private Long creditBook;

        public StudentBuilder setGroup(Integer group) {
            this.group = group;
            return this;
        }

        public StudentBuilder setAverageScore(Double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public StudentBuilder setCreditBook(Long creditBook) {
            this.creditBook = creditBook;
            return this;
        }

        public Student build() {
            validate();
            return new Student(this);
        }

        private void validate() {
            if (group == null) {
                throw new ValidationException("Не указан номер группы.");
            }

            if (averageScore == null) {
                throw new ValidationException("Средний балл не указан.");
            }

            if (averageScore < 0 || averageScore > 5) {
                throw new ValidationException("Оценка не может быть меньше 0 и больше 5.");
            }

            if (creditBook == null) {
                throw new ValidationException("Номер зачетной книжки не указан.");
            }

            if (creditBook < 0) {
                throw new ValidationException("Номер зачетной книжки не может быть отрицательным.");
            }
        }
    }
}
