package com.team.sortapp.manual;

import com.team.sortapp.collection.CustomList;
import com.team.sortapp.exception.ValidationException;
import com.team.sortapp.model.Student;
import com.team.sortapp.io.provider.RandomDataProvider;
import com.team.sortapp.io.ResultWriter;
import com.team.sortapp.io.util.StudentValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class IoTests {

    public static void main(String[] args) throws IOException {
        System.out.println("=== СТАРТ ТЕСТИРОВАНИЯ ВВОДА/ВЫВОДА ===");

        testValidatorFailures();
        testRandomProviderCount();
        testResultWriterAppendMode();

        System.out.println("=== ВСЕ ТЕСТЫ ДЛЯ ВЕТКИ DEV 3 ПРОЙДЕНЫ! ===");
    }

    private static void testValidatorFailures() {
        // Тест на не-число
        try {
            StudentValidator.parseAndValidate("abc", "4.5", "12345");
            throw new RuntimeException("Тест провален: Валидатор пропустил строку 'abc' в качестве группы");
        } catch (ValidationException e) { /* Успех */ }

        // Тест на диапазон баллов
        try {
            StudentValidator.parseAndValidate("10", "11.5", "12345");
            throw new RuntimeException("Тест провален: Валидатор пропустил балл 11.5");
        } catch (ValidationException e) { /* Успех */ }
    }

    private static void testRandomProviderCount() {
        RandomDataProvider provider = new RandomDataProvider();
        CustomList<Student> list = provider.provide(15);
        if (list.size() != 15) {
            throw new RuntimeException("Тест провален: RandomDataProvider вернул размер " + list.size() + " вместо 15");
        }
    }

    private static void testResultWriterAppendMode() throws IOException {
        String testFile = "test_append_output.txt";
        Files.deleteIfExists(Paths.get(testFile)); // Чистим перед тестом

        RandomDataProvider provider = new RandomDataProvider();
        CustomList<Student> batch1 = provider.provide(2);
        CustomList<Student> batch2 = provider.provide(2);

        // Пишем дважды в один файл
        ResultWriter.appendToFile(testFile, batch1, "Первая партия");
        ResultWriter.appendToFile(testFile, batch2, "Вторая партия");

        List<String> lines = Files.readAllLines(Paths.get(testFile));

        // Считаем маркеры блоков, чтобы убедиться, что файл именно дописывался, а не затирался
        long blockCount = lines.stream().filter(l -> l.contains("=== БЛОК:")).count();

        Files.deleteIfExists(Paths.get(testFile)); // Чистим после теста

        if (blockCount != 2) {
            throw new RuntimeException("Тест провален: Режим APPEND не сработал. Найдено блоков записей: " + blockCount);
        }
    }
}
