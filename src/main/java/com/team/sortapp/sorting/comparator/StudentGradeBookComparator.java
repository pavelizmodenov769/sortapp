package com.team.sortapp.sorting.comparator;

import com.team.sortapp.model.Student;

import java.util.Comparator;

public class StudentGradeBookComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Long.compare(s1.getCreditBook(), s2.getCreditBook());
    }
}