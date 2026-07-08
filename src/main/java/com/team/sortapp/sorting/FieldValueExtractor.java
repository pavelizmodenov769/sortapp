package com.team.sortapp.sorting;

import com.team.sortapp.model.Student;

public interface FieldValueExtractor {
    long extract(Student student);
}