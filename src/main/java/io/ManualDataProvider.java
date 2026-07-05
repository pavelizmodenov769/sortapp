package io;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.model.Student;

import java.util.Scanner;

public class ManualDataProvider implements DataProvider {
    private final Scanner scanner;

    public ManualDataProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public CustomList<Student> provide(int count) {
        if (count < 0) throw new IllegalArgumentException("Размер не может быть отрицательным.");

        CustomList<Student> list = new CustomArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Ввод данных студента №" + (i + 1) + " ---");

            String group = askField("Введите номер группы (целое число > 0): ");
            String score = askField("Введите средний балл (от 0.0 до 10.0): ");
            String gradeBook = askField("Введите номер зачетной книжки (целое число > 0): ");

            try {
                // Пытаемся собрать студента через валидатор
                Student student = StudentValidator.validateAndBuild(group, score, gradeBook);
                list.add(student);
                System.out.println("[Успешно добавлен]: " + student);
            } catch (RuntimeException e) {
                System.out.println("[Ошибка] " + e.getMessage());
                System.out.println("Повторите ввод для этого студента полностью.");
                i--; // Уменьшаем счетчик, чтобы повторить итерацию для текущего студента
            }
        }
        return list;
    }

    private String askField(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        while (input.trim().isEmpty()) {
            System.out.print("Поле не может быть пустым. " + prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    @Override
    public String getName() {
        return "Ручной ввод данных с клавиатуры";
    }
}