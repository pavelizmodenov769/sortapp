package model;

import exeption.ValidationExeption;

import java.util.Objects;

public class Student {
    private final String group;
    private final double averageScore;
    private final String creditBook;

    private Student(Builder builder) {
        this.group = builder.group;
        this.averageScore = builder.averageScore;
        this.creditBook = builder.creditBook;
    }

    public String getGroup() {
        return group;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public String getCreditBook() {
        return creditBook;
    }

    @Override
    public String toString() {
        return  "группа - " + group +
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
    public static class Builder {
        private String group;
        private double averageScore;
        private String creditBook;

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public Builder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public Builder setCreditBook(String creditBook) {
            this.creditBook = creditBook;
            return this;
        }

        public Student build() {
            ValidationExeption.validate(group, averageScore, creditBook);
            return new Student(this);
        }
    }
}
