package com.team.sortapp.manual;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.concurrent.OccurrenceCounter;
import com.team.sortapp.model.Student;

public class OccurrenceCounterManualTest {

    private  OccurrenceCounterManualTest() {
    }

    public static void main(String[] args) {
        System.out.println("== OccurrenceCounterManualTest ==");
        TestRunner.reset();

        testExactCount();
        testNotFound();
        testEmptyCollection();
        testLargeCollectionConsistency();

        TestRunner.summary();
        if (TestRunner.hasFailures()) {
            System.exit(1);
        }
    }

    private static void testExactCount() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(student(1,8.0,100L));
        list.add(student(2, 7.0, 200L));
        list.add(student(3, 9.0, 100L));
        list.add(student(4, 6.0, 300L));
        list.add(student(5, 8.5, 100L));

        int count = OccurrenceCounter.countCreditBookOccurrences(list,100L);
        TestRunner.checkEquals(3, count,
                "3 вхождения зачётки 100 среди 5 студентов");
    }

    private static void testNotFound() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(student(1, 8.0, 100L));
        list.add(student(2, 7.0, 200L));

        int count = OccurrenceCounter.countCreditBookOccurrences(list, 999L);
        TestRunner.checkEquals(0, count,
                "0 вхождений несуществующего значения");
    }

    private static void testEmptyCollection() {
        CustomList<Student> list = new CustomArrayList<>();

        int count = OccurrenceCounter.countCreditBookOccurrences(list, 1L);
        TestRunner.checkEquals(0, count,
                "0 вхождений в пустой коллекции");
    }

    private static void testLargeCollectionConsistency() {
        CustomList<Student> list = new CustomArrayList<>();
        int size = 100_000;
        long targetGradeBook = 42L;

        // Заполняем: зачётки 1..100000, из которых каждое 3-е = 42.
        for (int i = 1; i <= size; i++) {
            long gradeBook = (i % 3 == 0) ? targetGradeBook : i;
            list.add(student(i, 5.0 + (i % 10) * 0.1, gradeBook));
        }

        // Однопоточный подсчёт (baseline).
        int expected = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCreditBook() == targetGradeBook) {
                expected++;
            }
        }

        int actual = OccurrenceCounter.countCreditBookOccurrences(list, targetGradeBook);

        TestRunner.checkEquals(expected, actual,
                "многопоточный результат совпадает с однопоточным на "
                        + size + " элементах (ожидается " + expected + ")");

        // Дополнительно проверяем, что expected правильный.
        TestRunner.checkEquals(size / 3, expected,
                "baseline: каждое 3-е = " + targetGradeBook + ", ожидается " + (size / 3));
    }

    private static Student student( int group, double score, long creditBook) {
        return new Student.StudentBuilder()
                .setGroup(group)
                .setAverageScore(score)
                .setCreditBook(creditBook)
                .build();
    }
}
