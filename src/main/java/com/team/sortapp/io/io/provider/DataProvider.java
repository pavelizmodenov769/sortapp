package com.team.sortapp.io.io.provider;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

public interface DataProvider {
    CustomList<Student> provide(int count);

}