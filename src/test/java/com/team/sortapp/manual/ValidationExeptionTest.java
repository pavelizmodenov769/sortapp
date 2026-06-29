package com.team.sortapp.manual;

import model.Student;

public class ValidationExeptionTest {
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
                    .setAvaregeScore(4.8)
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
            Student student1 = new Student.Builder()
                    .setGroup("")
                    .setAvaregeScore(4.8)
                    .setCreditBook("AA-102")
                    .build();

            System.out.println("testEmptyGroup FAILED");

        } catch (Exception e) {
            System.out.println("testEmptyGroup PASSED: " + e.getMessage());
        }
    }

    private static void testInvalidAvarageScore() {
        try {
            Student student2 = new Student.Builder()
                    .setGroup("JAVA-102")
                    .setAvaregeScore(6)
                    .setCreditBook("AA-102")
                    .build();

            System.out.println("testEmptyGroup FAILED");

        } catch (Exception e) {
            System.out.println("testEmptyGroup PASSED: " + e.getMessage());
        }
    }

    private static void testEmptyCreditBook() {
        try {
            Student student3 = new Student.Builder()
                    .setGroup("JAVA-102")
                    .setAvaregeScore(4.8)
                    .setCreditBook("")
                    .build();

            System.out.println("testEmptyGroup FAILED");

        } catch (Exception e) {
            System.out.println("testEmptyGroup PASSED: " + e.getMessage());
        }
    }
}
