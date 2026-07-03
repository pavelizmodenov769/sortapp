package com.team.sortapp.sorting;

import com.team.sortapp.collection.CustomList;
import java.util.Comparator;

public class SortContext<T> {
    private SortStrategy<T> strategy;

    public SortContext(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void sort(CustomList<T> list, Comparator<? super T> comparator) {
        strategy.sort(list, comparator);
    }

    public String getStrategyName() {
        return strategy.getName();
    }
}