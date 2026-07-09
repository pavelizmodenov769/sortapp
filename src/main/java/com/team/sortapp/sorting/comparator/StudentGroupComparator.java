package com.team.sortapp.sorting.comparator;

import com.team.sortapp.model.Student;
import java.util.Comparator;

public class StudentGroupComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.getGroup(), s2.getGroup());
    }
}