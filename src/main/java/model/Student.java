package model;

import exeption.ValidationExeption;

import java.util.Objects;

public class Student {
    private final String group;
    private final double avarageScore;
    private final String creditBook;

    private Student(Builder builder) {
        this.group = builder.group;
        this.avarageScore = builder.avarageScore;
        this.creditBook = builder.creditBook;
    }

    public String getGroup() {
        return group;
    }

    public double getAvarageScore() {
        return avarageScore;
    }

    public String getCreditBook() {
        return creditBook;
    }

    @Override
    public String toString() {
        return  "группа - " + group +
                "; средний балл - " + avarageScore +
                "; номер зачетной книжки - " + creditBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(avarageScore, student.avarageScore) == 0 &&
                Objects.equals(group, student.group) &&
                Objects.equals(creditBook, student.creditBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, avarageScore, creditBook);
    }

    // Builder for Student
    public static class Builder {
        private String group;
        private double avarageScore;
        private String creditBook;

        public Builder setGroup(String group) {
            this.group = group;
            return this;
        }

        public Builder setAvaregeScore(double avaregeScore) {
            this.avarageScore = avaregeScore;
            return this;
        }

        public Builder setCreditBook(String creditBook) {
            this.creditBook = creditBook;
            return this;
        }

        public Student build() {
            ValidationExeption.validate(group, avarageScore, creditBook);
            return new Student(this);
        }
    }
}
