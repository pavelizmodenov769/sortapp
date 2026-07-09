package com.team.sortapp.manual;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;
import com.team.sortapp.sorting.BubbleSort;
import com.team.sortapp.sorting.EvenOddSortStrategy;
import com.team.sortapp.sorting.FieldValueExtractor;
import com.team.sortapp.sorting.SortStrategy;
import com.team.sortapp.sorting.comparator.StudentGroupComparator;
import com.team.sortapp.sorting.comparator.StudentGradeBookComparator;

public class EvenOddTest {
    public static void main(String[] args) {
        testEvenOddGroup();
        testEvenOddCreditBook();
        testNoEvenElements();
        testAllEvenElements();
        testSingleElement();
    }

    private static void testEvenOddGroup() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(student(3, 4.0, 1003L));
        list.add(student(2, 3.8, 1002L));
        list.add(student(5, 4.5, 1005L));
        list.add(student(8, 4.2, 1008L));
        list.add(student(4, 3.9, 1004L));
        list.add(student(7, 4.1, 1007L));

        FieldValueExtractor groupExtractor = s -> s.getGroup();
        SortStrategy<Student> evenOdd = new EvenOddSortStrategy(
                new BubbleSort<>(),
                groupExtractor
        );

        Student[] original = new Student[list.size()];
        for (int i = 0; i < list.size(); i++) original[i] = list.get(i);

        evenOdd.sort(list, new StudentGroupComparator());

        for (int i = 0; i < list.size(); i++) {
            if (groupExtractor.extract(original[i]) % 2 != 0) {
                if (!original[i].equals(list.get(i))) {
                    System.out.println("FAIL: нечётный элемент сдвинулся на позиции " + i);
                    return;
                }
            }
        }

        Integer prevEven = null;
        for (int i = 0; i < list.size(); i++) {
            if (groupExtractor.extract(list.get(i)) % 2 == 0) {
                int current = list.get(i).getGroup();
                if (prevEven != null && current < prevEven) {
                    System.out.println("FAIL: чётные не отсортированы");
                    return;
                }
                prevEven = current;
            }
        }

        System.out.println("testEvenOddGroup: PASSED");
    }

    private static void testEvenOddCreditBook() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(student(1, 4.0, 1001L));
        list.add(student(2, 4.2, 1004L));
        list.add(student(3, 4.5, 1003L));
        list.add(student(4, 4.1, 1002L));

        FieldValueExtractor creditExtractor = s -> s.getCreditBook();
        SortStrategy<Student> evenOdd = new EvenOddSortStrategy(
                new BubbleSort<>(),
                creditExtractor
        );

        Student[] original = new Student[list.size()];
        for (int i = 0; i < list.size(); i++) original[i] = list.get(i);

        evenOdd.sort(list, new StudentGradeBookComparator());

        for (int i = 0; i < list.size(); i++) {
            if (creditExtractor.extract(original[i]) % 2 != 0) {
                if (!original[i].equals(list.get(i))) {
                    System.out.println("FAIL: нечётный номер зачётки сдвинулся");
                    return;
                }
            }
        }

        Long prevEven = null;
        for (int i = 0; i < list.size(); i++) {
            if (creditExtractor.extract(list.get(i)) % 2 == 0) {
                long current = list.get(i).getCreditBook();
                if (prevEven != null && current < prevEven) {
                    System.out.println("FAIL: чётные зачётки не отсортированы");
                    return;
                }
                prevEven = current;
            }
        }

        System.out.println("testEvenOddCreditBook: PASSED");
    }

    private static void testNoEvenElements() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(student(1, 4.0, 1001L));
        list.add(student(3, 4.5, 1003L));

        FieldValueExtractor groupExtractor = s -> s.getGroup();
        SortStrategy<Student> evenOdd = new EvenOddSortStrategy(
                new BubbleSort<>(),
                groupExtractor
        );

        evenOdd.sort(list, new StudentGroupComparator());

        System.out.println("testNoEvenElements: PASSED");
    }

    private static void testAllEvenElements() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(student(4, 4.0, 1004L));
        list.add(student(2, 4.5, 1002L));

        FieldValueExtractor groupExtractor = s -> s.getGroup();
        SortStrategy<Student> evenOdd = new EvenOddSortStrategy(
                new BubbleSort<>(),
                groupExtractor
        );

        evenOdd.sort(list, new StudentGroupComparator());

        if (list.get(0).getGroup() != 2 || list.get(1).getGroup() != 4) {
            System.out.println("FAIL: все чётные должны быть отсортированы");
            return;
        }
        System.out.println("testAllEvenElements: PASSED");
    }

    private static void testSingleElement() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(student(2, 4.0, 1002L));

        FieldValueExtractor groupExtractor = s -> s.getGroup();
        SortStrategy<Student> evenOdd = new EvenOddSortStrategy(
                new BubbleSort<>(),
                groupExtractor
        );

        evenOdd.sort(list, new StudentGroupComparator());

        System.out.println("testSingleElement: PASSED");
    }

    private static Student student(int group, double score, long credit) {
        return new Student.StudentBuilder()
                .setGroup(group)
                .setAverageScore(score)
                .setCreditBook(credit)
                .build();
    }
}