package io;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.util.Random;
import java.util.stream.Stream;

public class RandomDataProvider implements DataProvider {
    private final Random random = new Random();

    @Override
    public CustomList<Student> provide(int count) {
        if (count < 0) throw new IllegalArgumentException("Размер не может быть отрицательным.");
        long baseGradeBook = 20260000L;
        // Используем Stream.generate + CollectionFiller
        Stream<Student> studentStream = Stream.generate(() -> {
            int group = random.nextInt(999) + 1;
            double score = 1.0 + (random.nextDouble() * 9.0); // [1.0; 10.0]
            long gradeBook = baseGradeBook + random.nextInt(100000);

            return new Student.StudentBuilder()
                    .setGroup(group)
                    .setAverageScore(score)
                    .setCreditBook(gradeBook)
                    .build();
        }).limit(count);

        return CollectionFiller.fillFromStream(studentStream);
    }

    @Override
    public String getName() {
        return "Автоматическая генерация случайных данных";
    }
}
