package com.team.sortapp.io.io.provider;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.exeption.ValidationException;
import com.team.sortapp.model.Student;
import com.team.sortapp.io.io.util.StudentValidator;

import java.util.Scanner;

public class ManualDataProvider implements DataProvider {
    private final Scanner scanner;

    public ManualDataProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public CustomList<Student> provide(int count) {
        CustomList<Student> list = new CustomArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Ввод данных студента №" + (i + 1) + " ---");
            Student student = readSingleStudent();
            list.add(student);
        }
        return list;
    }

    private Student readSingleStudent() {
        while (true) {
            System.out.print("Введите номер группы: ");
            String group = scanner.next();
            System.out.print("Введите средний балл [0; 10]: ");
            String score = scanner.next();
            System.out.print("Введите номер зачетной книжки: ");
            String gradeBook = scanner.next();

            try {
                return StudentValidator.parseAndValidate(group, score, gradeBook);
            } catch (ValidationException e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
                System.out.println("Пожалуйста, повторите ввод этого студента целиком.");
            }
        }
    }
}
