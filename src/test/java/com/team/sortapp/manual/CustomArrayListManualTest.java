package com.team.sortapp.manual;

import com.team.sortapp.collection.CustomArrayList;

public class CustomArrayListManualTest {
    public static void main(String[] args) {
        try {
            new CustomArrayList<>(0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        int initialCapacity = 20;

        CustomArrayList<String> list = new CustomArrayList<>(initialCapacity);

        // Проверка на рост массива,
        // добавляем на 10 элементов больше, чем заданная ёмкость
        for (int i = 0; i < initialCapacity + 10; i++) {
            list.add("" + i);
        }

        System.out.println("Ёмкость была: " + initialCapacity + ", " + "стала: " + list.size());

        // Проверка получения элемента
        System.out.println("Первый элемент: " + list.get(0));
        System.out.println("Последний Элемент: " + list.get(list.size() - 1));

        // Проверка границ
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            list.get(list.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("------------------------------------");

        // Проверка forEach
        System.out.print("Итерация: ");

        for (String s : list) {
            System.out.print(s + ", ");
        }
        System.out.println(" ");
        System.out.println("------------------------------------");

        // Изменение элемента
        String oldElement = list.set(0, "A");
        System.out.print("Новый список: ");

        for (String str : list) {
            System.out.print(str + ", ");
        }
        System.out.println(" ");
        System.out.println("Замененный элемент: " + oldElement);
        System.out.println("------------------------------------");

        //Удаление элемента
        String removeElement = list.remove(1);
        System.out.print("Новый список: ");
        for (String s : list) {
            System.out.print(s + ", ");
        }
        System.out.println(" ");

        System.out.println("Удаленный элемент: " + removeElement);
    }
}
