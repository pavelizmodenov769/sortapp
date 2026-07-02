package io;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

public interface DataProvider {
    CustomList<Student> provide(int count);

    // Возвращает название источника данных для построения динамического меню.
    String getName();
}
