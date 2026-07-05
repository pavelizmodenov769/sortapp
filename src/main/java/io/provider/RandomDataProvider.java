package io.provider;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;
import io.util.CollectionFiller;

import java.util.Random;
import java.util.stream.Stream;

public class RandomDataProvider implements DataProvider {
    private final Random random = new Random();

    @Override
    public CustomList<Student> provide(int count) {
        Stream<Student> studentStream = Stream.generate(() -> {
            int group = random.nextInt(50) + 1; // 1 - 50
            double score = 2.0 + (random.nextDouble() * 8.0); // 2.0 - 10.0
            score = Math.round(score * 100.0) / 100.0;
            long gradeBook = 100000L + random.nextInt(900000); // 6 знаков

            return new Student.StudentBuilder()
                    .setGroup(group)
                    .setAverageScore(score)
                    .setCreditBook(gradeBook)
                    .build();
        }).limit(count);

        return CollectionFiller.fillFromStream(studentStream);
    }
}
