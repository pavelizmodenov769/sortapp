package com.team.sortapp.model;

import com.team.sortapp.exception.ValidationException;

import java.util.Objects;

public class Institute {
    private final String faculty;
    private final String department;
    private final int numberOfTeachers;

    private Institute(InstituteBuilder builder) {
        this.faculty = builder.faculty;
        this.department = builder.department;
        this.numberOfTeachers = builder.numberOfTeachers;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDepartment() {
        return department;
    }

    public int getNumberOfTeachers() {
        return numberOfTeachers;
    }

    @Override
    public String toString() {
        return "Информация об институте: " +
                "факультет:" + faculty +
                ", Кафедра: " + department +
                ", количество преподавателей: " + numberOfTeachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institute institute = (Institute) o;
        return numberOfTeachers == institute.numberOfTeachers
                && Objects.equals(faculty, institute.faculty)
                && Objects.equals(department, institute.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faculty, department, numberOfTeachers);
    }

    // Builder for Institute
    public static class InstituteBuilder {
        private String faculty;
        private String department;
        private int numberOfTeachers;

        public InstituteBuilder faculty(String faculty) {
            this.faculty = faculty;
            return this;
        }

        public InstituteBuilder department(String department) {
            this.department = department;
            return this;
        }

        public InstituteBuilder numberOfTeacher(int numberOfTeachers) {
            this.numberOfTeachers = numberOfTeachers;
            return this;
        }

        public Institute build() {
            validateInstitute();
            return new Institute(this);
        }

        private void validateInstitute() {
            if (numberOfTeachers <= 0) {
                throw new ValidationException("Не указано количество преподавателей.");
            }

            if (faculty == null || faculty.isBlank()) {
                throw new ValidationException("Не указан факультет.");
            }

            if (department == null || department.isBlank()) {
                throw new ValidationException("Не указана кафедра.");
            }
        }
    }
}
