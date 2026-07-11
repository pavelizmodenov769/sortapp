package com.team.sortapp.model;

import com.team.sortapp.exception.ValidationException;

import java.util.Objects;

public class UniversityApplicant {
    private final int totalExamScore;
    private final String name;
    private final String lastName;

    private UniversityApplicant(UniversityApplicantBuilder builder) {
        this.totalExamScore = builder.totalExamScore;
        this.name = builder.name;
        this.lastName = builder.lastName;
    }

    public int getExamScore() {
        return totalExamScore;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Абитуриент: " +
                "балл ЕГЭ: " + totalExamScore +
                ", Имя: " + name +
                ",Фамилия: " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityApplicant that = (UniversityApplicant) o;
        return this.totalExamScore == that.totalExamScore
                && Objects.equals(name, that.name)
                && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalExamScore, name, lastName);
    }

    // Builder for UniversityApplicant
    public static class UniversityApplicantBuilder {
        private Integer totalExamScore;
        private String name;
        private String lastName;

        public UniversityApplicantBuilder totalExamScore(Integer totalExamScore) {
            this.totalExamScore = totalExamScore;
            return this;
        }

        public UniversityApplicantBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UniversityApplicantBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UniversityApplicant build() {
            validateApplicant();
            return new UniversityApplicant(this);
        }

        private void validateApplicant() {
            if (totalExamScore == null) {
                throw new ValidationException("Не указан балл абитуриента.");
            }

            if (totalExamScore < 0 || totalExamScore > 400) {
                throw new ValidationException("Оценка не может быть меньше 0 и больше 400.");
            }

            if (name == null || name.isBlank()) {
                throw new ValidationException("Имя не указано.");
            }

            if (lastName == null || lastName.isBlank()) {
                throw new ValidationException("Фамилия не указана.");
            }
        }
    }
}