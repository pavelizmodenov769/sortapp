package com.team.sortapp.model;

import com.team.sortapp.exception.ValidationException;

import java.util.Objects;

public final class Student {
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
                group == student.group &&
                creditBook == student.creditBook;
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, averageScore, creditBook);
    }

    // Builder for Student

    public static final class StudentBuilder {
        private int group;
        private double averageScore;
        private long creditBook;
        private boolean groupSet;
        private boolean averageScoreSet;
        private boolean creditBookSet;

        public StudentBuilder setGroup(int group) {
            this.group = group;
            this.groupSet = true;
            return this;
        }

        public StudentBuilder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            this.averageScoreSet = true;
            return this;
        }

        public StudentBuilder setCreditBook(long creditBook) {
            this.creditBook = creditBook;
            this.creditBookSet = true;
            return this;
        }

        public Student build() {
            validate();
            return new Student(this);
        }

        private void validate() {
            if (!groupSet) {
                throw new ValidationException("Не указан номер группы.");
            }

            if (!averageScoreSet) {
                throw new ValidationException("Средний балл не указан.");
            }

            if (averageScore < 0 || averageScore > 10) {
                throw new ValidationException("Оценка не может быть меньше 0 и больше 10.");
            }

            if (!creditBookSet) {
                throw new ValidationException("Номер зачетной книжки не указан.");
            }

            if (creditBook < 0) {
                throw new ValidationException("Номер зачетной книжки не может быть отрицательным.");
            }
        }
    }
}
