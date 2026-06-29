package com.team.sortapp.manual;

import model.Student;

public class ValidationExceptionTest {
    public static void main(String[] args) {

        testValidStudent();
        testEmptyGroup();
        testInvalidAvarageScore();
        testEmptyCreditBook();
    }

    private static void testValidStudent() {
        try {
            Student student = new Student.Builder()
                    .setGroup("JAVA-102")
                    .setAverageScore(4.8)
                    .setCreditBook("AA-102")
                    .build();

            System.out.print("testValidStudent PASSED");
            System.out.println(": " + student);

        } catch (Exception e) {
            System.out.println("testValidStudent FAILED: " + e.getMessage());
        }
    }

    private static void testEmptyGroup() {
        try {
            new Student.Builder()
                    .setGroup("")
                    .setAverageScore(4.8)
                    .setCreditBook("AA-102")
                    .build();

            System.out.println("testEmptyGroup FAILED");

        } catch (Exception e) {
            System.out.println("testEmptyGroup PASSED: " + e.getMessage());
        }
    }

    private static void testInvalidAvarageScore() {
        try {
            new Student.Builder()
                    .setGroup("JAVA-102")
                    .setAverageScore(6)
                    .setCreditBook("AA-102")
                    .build();

            System.out.println("testEmptyGroup FAILED");

        } catch (Exception e) {
            System.out.println("testEmptyGroup PASSED: " + e.getMessage());
        }
    }

    private static void testEmptyCreditBook() {
        try {
            new Student.Builder()
                    .setGroup("JAVA-102")
                    .setAverageScore(4.8)
                    .setCreditBook("")
                    .build();

            System.out.println("testEmptyGroup FAILED");

        } catch (Exception e) {
            System.out.println("testEmptyGroup PASSED: " + e.getMessage());
        }
    }
}
