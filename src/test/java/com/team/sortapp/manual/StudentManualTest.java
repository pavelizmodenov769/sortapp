package com.team.sortapp.manual;

import com.team.sortapp.model.Student;

public class StudentManualTest {

    public static void main(String[] args) {
        Student student1 = new Student.StudentBuilder()
                .setGroup(102)
                .setAverageScore(4.8)
                .setCreditBook(111102L)
                .build();

        Student student2 = new Student.StudentBuilder()
                .setGroup(102)
                .setAverageScore(4.8)
                .setCreditBook(111102L)
                .build();

        Student student3 = new Student.StudentBuilder()
                .setGroup(103)
                .setAverageScore(4.7)
                .setCreditBook(111103L)
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
