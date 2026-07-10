package com.team.sortapp.sorting;

import com.team.sortapp.collection.CustomList;

import java.util.Comparator;

public class BubbleSort<T> implements SortStrategy<T> {

    @Override
    public void sort(CustomList<T> list, Comparator<? super T> comparator) {
        int size = list.size();
        if (size <= 1) {
            return;
        }

        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < size - 1 - i; j++) {
                T current = list.get(j);
                T next = list.get(j + 1);
                if (comparator.compare(current, next) > 0) {
                    list.set(j, next);
                    list.set(j + 1, current);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "Сортировка пузырьком";
    }
}