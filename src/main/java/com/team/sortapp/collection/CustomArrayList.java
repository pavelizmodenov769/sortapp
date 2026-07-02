package com.team.sortapp.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomArrayList<T> implements CustomList<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Начальная емкость массива должна быть больше 0.");
        }

        elements = new Object[initialCapacity];
        size = 0;
    }

    @Override
    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;

    }
    // Возвращает элемент по индексу.
    // Unchecked cast безопасен, потому что в elements хранятся только объекты типа Т,
    // добавленные через add(T).
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);

        T oldValue = (T) elements[index];
        elements[index] = element;

        return oldValue;
    }
    // Unchecked cast безопасен: в elements могут лежать только объекты типа T,
    // потому что все добавления проходят через add(T), а поле elements приватно.
    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        checkIndex(index);

        T removed = (T) elements[index];

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        size--;
        return removed;
    }

    @Override
    public void clear() {
        size = 0;

        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length + elements.length / 2 + 1;
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона [0, " + (size - 1) + "]");
        }
    }

    // Итератор
    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        // Unchecked cast безопасен: в elements могут лежать только объекты типа T,
        // потому что все добавления проходят через add(T), а поле elements приватно.
        // hasNext() и currentIndex гарантируют, что мы читаем только занятые ячейки.

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Элемент не найден.");
            }
            return (T) elements[currentIndex++];
        }
    }
}
