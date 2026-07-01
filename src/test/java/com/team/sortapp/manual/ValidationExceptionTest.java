package com.team.sortapp.manual;

import com.team.sortapp.exeption.ValidationException;
import com.team.sortapp.model.Student;

public class ValidationExceptionTest {
    public static void main(String[] args) {

        testValidStudent();
        testEmptyGroup();
        testInvalidAverageScore();
        testEmptyCreditBook();
        testNegativeCreditBook();
    }

    private static void testValidStudent() {
        try {
            Student student = new Student.StudentBuilder()
                    .setGroup(111102)
                    .setAverageScore(4.8)
                    .setCreditBook(111102L)
                    .build();

            System.out.print("testValidStudent PASSED");
            System.out.println(": " + student);

        } catch (Exception e) {
            System.out.println("testValidStudent FAILED: " + e.getMessage());
        }
    }

    private static void testEmptyGroup() {
        try {
            new Student.StudentBuilder()
                    .setAverageScore(4.8)
                    .setCreditBook(111102L)
                    .build();

            System.out.println("testEmptyGroup FAILED");

        } catch (ValidationException e) {
            System.out.println("testEmptyGroup PASSED: " + e.getMessage());
        }
    }

    private static void testInvalidAverageScore() {
        try {
            new Student.StudentBuilder()
                    .setGroup(111102)
                    .setAverageScore(5.5)
                    .setCreditBook(111102L)
                    .build();

            System.out.println("testInvalidAverageScore FAILED");

        } catch (ValidationException e) {
            System.out.println("testInvalidAverageScore PASSED: " + e.getMessage());
        }
    }

    private static void testEmptyCreditBook() {
        try {
            new Student.StudentBuilder()
                    .setGroup(111102)
                    .setAverageScore(4.8)
                    .build();

            System.out.println("testEmptyCreditBook FAILED");

        } catch (ValidationException e) {
            System.out.println("testEmptyCreditBook PASSED: " + e.getMessage());
        }
    }

    private static void testNegativeCreditBook() {
        try {
            new Student.StudentBuilder()
                    .setGroup(111102)
                    .setAverageScore(4.8)
                    .setCreditBook(-90087654L)
                    .build();

            System.out.println("testNegativeCreditBook FAILED");

        } catch (ValidationException e) {
            System.out.println("testNegativeCreditBook: " + e.getMessage());
        }
    }
}
