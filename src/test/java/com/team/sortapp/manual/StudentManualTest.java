package com.team.sortapp.manual;

import model.Student;

public class StudentManualTest {

    public static void main(String[] args) {
        Student student1 = new Student.Builder()
                .setGroup("JAVA-102")
                .setAverageScore(4.8)
                .setCreditBook("AA 102")
                .build();

        Student student2 = new Student.Builder()
                .setGroup("JAVA-102")
                .setAverageScore(4.8)
                .setCreditBook("AA 102")
                .build();

        Student student3 = new Student.Builder()
                .setGroup("JAVA-103")
                .setAverageScore(4.7)
                .setCreditBook("TT 103")
                .build();

        System.out.println("Студент1: " + student1);
        System.out.println("Студент2: " + student2);
        System.out.println("Студент3: " + student3);
        System.out.println();

        System.out.println("Equals: " + student1.equals(student2));
        System.out.println("Equals: " + student1.equals(student3));
        System.out.println();

        System.out.println("HashCodes:");
        System.out.println(student1.hashCode());
        System.out.println(student2.hashCode());
        System.out.println(student3.hashCode());
    }
}
