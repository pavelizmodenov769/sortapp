package com.team.sortapp.sorting;

import com.team.sortapp.collection.CustomList;
import java.util.Comparator;

public class QuickSort<T> implements SortStrategy<T> {

    @Override
    public void sort(CustomList<T> list, Comparator<? super T> comparator) {
        if (list.size() <= 1) {
            return;
        }
        quickSort(list, comparator, 0, list.size() - 1);
    }

    private void quickSort(CustomList<T> list, Comparator<? super T> comparator, int low, int high) {
        while (low < high) {
            int pivotIndex = partition(list, comparator, low, high);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(list, comparator, low, pivotIndex - 1);
                low = pivotIndex + 1;
            } else {
                quickSort(list, comparator, pivotIndex + 1, high);
                high = pivotIndex - 1;
            }
        }
    }

    private int partition(CustomList<T> list, Comparator<? super T> comparator, int low, int high) {
        T pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    private void swap(CustomList<T> list, int a, int b) {
        T tmp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, tmp);
    }

    @Override
    public String getName() {
        return "Быстрая сортировка";
    }
}