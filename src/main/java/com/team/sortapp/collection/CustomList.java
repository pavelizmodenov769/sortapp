package com.team.sortapp.collection;

public interface CustomList<T> extends Iterable<T> {

    void add(T element);

    T get(int index);

    T set(int index, T element);

    T remove(int index);

    void clear();

    boolean isEmpty();

    int size();
}
