package com.team.sortapp.concurrent;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class OccurrenceCounter {

    private static final int MIN_CHUNK_SIZE = 16;

    private OccurrenceCounter() {

    }

    public static int countCreditBookOccurrences(CustomList<Student> students, long target) {
        if (students == null) {
            throw new IllegalArgumentException("Список студентов не должен быть null");
        }
        int size = students.size();

        if (size == 0) {
            System.out.println("Значение " + target + " встречается 0 раз.");
            return 0;
        }

        int availableCores = Runtime.getRuntime().availableProcessors();
        int numThreads = Math.min(availableCores, Math.max(1, size / MIN_CHUNK_SIZE));

        int chunkSize = size / numThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Future<Integer>> futures = new ArrayList<>();
        for (int t = 0; t < numThreads; t++) {
            int from = t * chunkSize;
            int to = (t == numThreads - 1) ? size : from + chunkSize;
            futures.add(executor.submit(new ChunkCounter(students, target, from, to)));
        }

        int total = 0;
        for (Future<Integer> future : futures) {
            try {
                total += future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Подсчет прерван");
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                String reason = cause != null ? cause.getMessage() : e.getMessage();
                System.err.println("Ошибка в потоке: " + reason);
            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Значение " + target + " встречается " + total + " раз.");
        return total;
    }

    private static final class ChunkCounter implements Callable<Integer> {

        private final CustomList<Student> students;
        private final long target;
        private final int from;
        private final int to;

        ChunkCounter(CustomList<Student> students, long target, int from, int to) {
            this.students = students;
            this.target = target;
            this.from = from;
            this.to = to;
        }

        @Override
        public Integer call() {
            int count = 0;
            for (int i = from; i < to; i++) {
                if (students.get(i).getCreditBook() == target) {
                    count++;
                }
            }
            return count;
        }
    }
}