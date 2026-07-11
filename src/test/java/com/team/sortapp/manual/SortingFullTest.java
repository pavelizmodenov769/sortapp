package com.team.sortapp.manual;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;
import com.team.sortapp.sorting.BubbleSort;
import com.team.sortapp.sorting.QuickSort;
import com.team.sortapp.sorting.SortStrategy;
import com.team.sortapp.sorting.comparator.StudentGroupComparator;
import com.team.sortapp.sorting.comparator.StudentScoreComparator;
import com.team.sortapp.sorting.comparator.StudentGradeBookComparator;
import java.util.Comparator;

public class SortingFullTest {
    public static void main(String[] args) {
        testEmptyList();
        testSingleElement();
        testMultipleElements();
        testDuplicates();
        testReverseOrder();
    }

    private static void testEmptyList() {
        CustomList<Student> list = new CustomArrayList<>();
        SortStrategy<Student> bubble = new BubbleSort<>();
        SortStrategy<Student> quick = new QuickSort<>();

        bubble.sort(list, new StudentGroupComparator());
        quick.sort(list, new StudentGroupComparator());

        System.out.println("testEmptyList: PASSED");
    }

    private static void testSingleElement() {
        CustomList<Student> list1 = new CustomArrayList<>();
        list1.add(new Student.StudentBuilder().setGroup(101).setAverageScore(4.5).setCreditBook(1001L).build());

        CustomList<Student> list2 = new CustomArrayList<>();
        list2.add(new Student.StudentBuilder().setGroup(101).setAverageScore(4.5).setCreditBook(1001L).build());

        new BubbleSort<Student>().sort(list1, new StudentGroupComparator());
        new QuickSort<Student>().sort(list2, new StudentGroupComparator());

        System.out.println("testSingleElement: PASSED");
    }

    private static void testMultipleElements() {
        Student s1 = new Student.StudentBuilder().setGroup(103).setAverageScore(4.8).setCreditBook(1003L).build();
        Student s2 = new Student.StudentBuilder().setGroup(101).setAverageScore(3.9).setCreditBook(1001L).build();
        Student s3 = new Student.StudentBuilder().setGroup(102).setAverageScore(4.5).setCreditBook(1002L).build();

        Comparator<Student>[] comparators = new Comparator[] {
            new StudentGroupComparator(),
            new StudentScoreComparator(),
            new StudentGradeBookComparator()
        };

        SortStrategy<Student>[] strategies = new SortStrategy[] {
            new BubbleSort<>(),
            new QuickSort<>()
        };

        for (SortStrategy<Student> strategy : strategies) {
            for (Comparator<Student> comp : comparators) {
                CustomList<Student> list = new CustomArrayList<>();
                list.add(s1);
                list.add(s2);
                list.add(s3);
                strategy.sort(list, comp);
                if (!isSorted(list, comp)) {
                    System.out.println("FAIL: " + strategy.getName() + " with comparator");
                    return;
                }
            }
        }

        System.out.println("testMultipleElements: PASSED");
    }

    private static void testDuplicates() {
        Student s1 = new Student.StudentBuilder().setGroup(101).setAverageScore(4.5).setCreditBook(1001L).build();
        Student s2 = new Student.StudentBuilder().setGroup(101).setAverageScore(4.5).setCreditBook(1001L).build();
        Student s3 = new Student.StudentBuilder().setGroup(102).setAverageScore(4.5).setCreditBook(1002L).build();

        CustomList<Student> list1 = new CustomArrayList<>();
        list1.add(s1);
        list1.add(s2);
        list1.add(s3);

        CustomList<Student> list2 = new CustomArrayList<>();
        list2.add(s1);
        list2.add(s2);
        list2.add(s3);

        Comparator<Student> comp = new StudentGroupComparator();
        new BubbleSort<Student>().sort(list1, comp);
        new QuickSort<Student>().sort(list2, comp);

        if (!isSorted(list1, comp) || !isSorted(list2, comp)) {
            System.out.println("FAIL: testDuplicates");
            return;
        }
        System.out.println("testDuplicates: PASSED");
    }

    private static void testReverseOrder() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(new Student.StudentBuilder().setGroup(105).setAverageScore(5.0).setCreditBook(1005L).build());
        list.add(new Student.StudentBuilder().setGroup(104).setAverageScore(4.5).setCreditBook(1004L).build());
        list.add(new Student.StudentBuilder().setGroup(103).setAverageScore(4.0).setCreditBook(1003L).build());
        list.add(new Student.StudentBuilder().setGroup(102).setAverageScore(3.5).setCreditBook(1002L).build());
        list.add(new Student.StudentBuilder().setGroup(101).setAverageScore(3.0).setCreditBook(1001L).build());

        CustomList<Student> listCopy = new CustomArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listCopy.add(list.get(i));
        }

        Comparator<Student> comp = new StudentGroupComparator();
        new BubbleSort<Student>().sort(list, comp);
        new QuickSort<Student>().sort(listCopy, comp);

        if (!isSorted(list, comp) || !isSorted(listCopy, comp)) {
            System.out.println("FAIL: testReverseOrder");
            return;
        }
        System.out.println("testReverseOrder: PASSED");
    }

    private static boolean isSorted(CustomList<Student> list, Comparator<Student> comp) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (comp.compare(list.get(i), list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }
}