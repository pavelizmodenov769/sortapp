package com.team.sortapp.collection;

import java.util.function.Consumer;

public interface CustomList<T> extends Iterable<T> {

    void add(T element);

    T get(int index);

    T set(int index, T element);

    int size();

    boolean isEmpty();

    T remove(int index);



}
