package com.team.sortapp.sorting;

import com.team.sortapp.collection.CustomList;
import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(CustomList<T> list, Comparator<? super T> comparator);
    String getName();
}