package com.team.sortapp.sorting.comparator;

import com.team.sortapp.model.Student;
import java.util.Comparator;

public class StudentScoreComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s1.getAverageScore(), s2.getAverageScore());
    }
}