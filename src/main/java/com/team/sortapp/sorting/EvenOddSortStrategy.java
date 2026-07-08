package com.team.sortapp.sorting;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;
import java.util.Comparator;

public class EvenOddSortStrategy implements SortStrategy<Student> {

    private final SortStrategy<Student> baseStrategy;
    private final FieldValueExtractor extractor;

    public EvenOddSortStrategy(SortStrategy<Student> baseStrategy, FieldValueExtractor extractor) {
        this.baseStrategy = baseStrategy;
        this.extractor = extractor;
    }

    @Override
    public void sort(CustomList<Student> list, Comparator<? super Student> comparator) {
        if (list.size() <= 1) {
            return;
        }

        int size = list.size();
        int[] evenIndices = new int[size];
        Student[] evenValues = new Student[size];
        int evenCount = 0;

        for (int i = 0; i < size; i++) {
            Student s = list.get(i);
            if (extractor.extract(s) % 2 == 0) {
                evenIndices[evenCount] = i;
                evenValues[evenCount] = s;
                evenCount++;
            }
        }

        if (evenCount <= 1) {
            return;
        }

        CustomList<Student> evenList = new CustomArrayList<>(evenCount);
        for (int i = 0; i < evenCount; i++) {
            evenList.add(evenValues[i]);
        }

        baseStrategy.sort(evenList, comparator);

        for (int i = 0; i < evenCount; i++) {
            int originalIndex = evenIndices[i];
            Student sortedStudent = evenList.get(i);
            list.set(originalIndex, sortedStudent);
        }
    }

    @Override
    public String getName() {
        return "Чёт/нечёт (" + baseStrategy.getName() + ")";
    }
}