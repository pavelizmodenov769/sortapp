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
        int mid = low + (high - low) / 2;
        T lowVal = list.get(low);
        T midVal = list.get(mid);
        T highVal = list.get(high);

        if (comparator.compare(lowVal, midVal) > 0) {
            list.set(low, midVal);
            list.set(mid, lowVal);
            lowVal = list.get(low);
            midVal = list.get(mid);
        }
        if (comparator.compare(lowVal, highVal) > 0) {
            list.set(low, highVal);
            list.set(high, lowVal);
            lowVal = list.get(low);
            highVal = list.get(high);
        }
        if (comparator.compare(midVal, highVal) > 0) {
            list.set(mid, highVal);
            list.set(high, midVal);
            midVal = list.get(mid);
            highVal = list.get(high);
        }

        list.set(mid, list.get(high - 1));
        list.set(high - 1, midVal);
        T pivot = list.get(high - 1);

        int i = low;
        int j = high - 1;

        while (true) {
            do {
                i++;
            } while (comparator.compare(list.get(i), pivot) < 0);

            do {
                j--;
            } while (j > low && comparator.compare(list.get(j), pivot) > 0);

            if (i >= j) {
                break;
            }

            T temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }

        list.set(high - 1, list.get(i));
        list.set(i, pivot);
        return i;
    }

    @Override
    public String getName() {
        return "Быстрая сортировка";
    }
}