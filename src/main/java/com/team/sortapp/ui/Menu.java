package com.team.sortapp.ui;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;
import com.team.sortapp.concurrent.OccurrenceCounter;
import com.team.sortapp.io.io.provider.DataProvider;
import com.team.sortapp.io.io.provider.FileDataProvider;
import com.team.sortapp.io.io.provider.ManualDataProvider;
import com.team.sortapp.io.io.provider.RandomDataProvider;
import com.team.sortapp.model.Institute;
import com.team.sortapp.model.Student;
import com.team.sortapp.model.UniversityApplicant;
import com.team.sortapp.sorting.*;
import com.team.sortapp.sorting.comparator.StudentGradeBookComparator;
import com.team.sortapp.sorting.comparator.StudentGroupComparator;
import com.team.sortapp.sorting.comparator.StudentScoreComparator;
import com.team.sortapp.io.io.ResultWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private boolean running = false;

    private CustomList<Student> students = null;

    private SortContext<Student> sortContext;

    private Comparator<Student> currentComparator;

    private boolean useEvenNaturalSort = false;

    private Institute institute = null;

    private CustomList<UniversityApplicant> applicants = null;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        running = true;

        SortStrategy<Student> defaultStrategy = new BubbleSort<>();
        sortContext = new SortContext<>(defaultStrategy);
        currentComparator = new StudentGroupComparator();

        while (running) {
            printMenu();
            int choice = readInt("Ваш выбор: ", 1, 4);
            handleChoice(choice);
            System.out.println();
        }

        System.out.println("Работа программы завершена. До свидания!");
    }

    public void stop() {
        this.running = false;
    }

    private String readString(String promt, boolean blankOk) {
        while (true) {
            System.out.println(promt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty() || blankOk) {
                return line;
            }
            System.out.println(" Поле не может быть пустым.");
        }
    }

    private int readInt(String promt, int min, int max) {
        while (true) {
            System.out.println(promt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println(" Введите число от " + min + " до " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Некорректный ввод. Введите целое число.");
            }
        }
    }

    private long readLong(String promt) {
        while (true) {
            System.out.println(promt);
            String line = scanner.nextLine().trim();
            try {
                long value = Long.parseLong(line);
                if (value < 0) {
                    System.out.println(" Введите неотрицательное число.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println(" Некорректный ввод. Введите целое число.");
            }
        }
    }

    private void printMenu() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║             SORTAPP  —  ГЛАВНОЕ          ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ 1. Институт                              ║");
        System.out.println("║ 2. Студенты                              ║");
        System.out.println("║ 3. Абитуриенты                           ║");
        System.out.println("║ 4. Выход                                 ║");
        System.out.println("╚══════════════════════════════════════════╝");

        System.out.println(" Институт: ");
        System.out.println(institute == null ? "не создан" : institute);

        System.out.println(" Студенты ");
        if (students == null || students.isEmpty()) {
            System.out.println("пусто");
        } else {
            System.out.println(students.size() + " записей | " +
                    sortContext.getStrategyName() +
                    (useEvenNaturalSort ? " + чет/нечет" : ""));
        }

        System.out.println(" Абитуриенты:");
        if (applicants == null || applicants.isEmpty()) {
            System.out.println(" пусто");
        } else {
            System.out.println(" " + applicants.size() + " записей");
        }

        System.out.println();
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> runInstituteMenu();
            case 2 -> runStudentsMenu();
            case 3 -> runApplicantsMenu();
            case 4 -> stop();
            default -> System.out.println("  Некорректный выбор.");
        }
    }

    // Институт

    private void runInstituteMenu() {
        while (running) {
            printInstituteMenu();
            int choice = readInt("Ваш выбор: ", 1, 5);
            switch (choice) {
                case 1 -> instituteCreate();
                case 2 -> instituteView();
                case 3 -> instituteEdit();
                case 4 -> instituteDelete();
                case 5 -> {
                    return;
                }
            }
            System.out.println();
        }
    }

    private void printInstituteMenu() {
        System.out.println("  ┌─── ИНСТИТУТ ───────────────────────────┐");
        System.out.println("  │ 1. Создать институт                    │");
        System.out.println("  │ 2. Просмотреть информацию              │");
        System.out.println("  │ 3. Редактировать                       │");
        System.out.println("  │ 4. Удалить                             │");
        System.out.println("  │ 5. Назад                               │");
        System.out.println("  └────────────────────────────────────────┘");
    }

    private void instituteCreate() {
        if (institute != null) {
            System.out.println("  Институт уже существует. Удалите или отредактируйте его (п.3).");
            return;
        }

        String faculity = readString(" Институт: ", false);
        String departament = readString(" Факультет: ", false);
        int teachers = readInt(" Количество преподавателей: ", 1, 10_000);

        institute = new Institute.InstituteBuilder()
                .faculty(faculity)
                .department(departament)
                .numberOfTeacher(teachers)
                .build();

        System.out.println(" Институт создан: " + institute);
    }

    private void instituteView() {
        if (institute == null) {
            System.out.println(" Институт не создан. Выберите п.1.");
            return;
        }
        System.out.println("  ┌─── Информация об институте ─────────────┐");
        System.out.println("  │ Институт:             " + padLable(institute.getFaculty()));
        System.out.println("  │ Факультет:               " + padLable(institute.getDepartment()));
        System.out.println("  │ Кол-во преподавателей: " + padLable(String.valueOf(institute.getNumberOfTeachers())));
        System.out.println("  └────────────────────────────────────────┘");
    }

    private static String padLable(String value) {
        final int width = 16;
        if (value.length() > width) {
            value = value.substring(0, width -2) + "..";
        }
        return String.format("%-" + width +"s|", value);
    }

    private void instituteEdit() {
        if (institute == null) {
            System.out.println(" Институт не создан. Выберите п.1.");
            return;
        }
        System.out.println(" Текущее значение (Enter = оставить без изменений):");
        String faculity = readString(" Институт [" + institute.getFaculty() + "] ", true);
        if (faculity.isEmpty()) {
            faculity = institute.getDepartment();
        }
        String departament = readString(" Факультет [" +institute.getDepartment() + "]: ", true);
        if (departament.isEmpty()) {
            departament = institute.getDepartment();
        }
        String teachersStr = readString(" Преподаватели [" + institute.getNumberOfTeachers() + "] ", true);
        int teachers;
        if (teachersStr.isEmpty()) {
            teachers = institute.getNumberOfTeachers();
        } else {
            teachers = readInt(" Колличество преподавателей ", 1,10_000);
        }

        institute = new Institute.InstituteBuilder()
                .faculty(faculity)
                .department(departament)
                .numberOfTeacher(teachers)
                .build();

        System.out.println(" Институт обновлен: " + institute);
    }

    private void instituteDelete() {
        if (institute == null) {
            System.out.println(" Институт не создан - нечего удалять.");
            return;
        }
        institute = null;
        System.out.println(" Институт удален.");
    }

    // Студенты

    private void runStudentsMenu() {
        while (running) {
            printSudentsMenu();
            int choice = readInt("Ваш выбор: ",1,8);
            try {
                switch (choice) {
                    case 1 -> handleFillCollection();
                    case 2 -> handleSelectAlgorithm();
                    case 3 -> handleSelectField();
                    case 4 -> handleSortAndShow();
                    case 5 -> handleWriteToFile();
                    case 6 -> handleCountOccurrences();
                    case 7 -> handleShowStudents();
                    case 8 -> {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(" Ошибка: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void printSudentsMenu() {
        System.out.println("  ┌─── СТУДЕНТЫ ───────────────────────────┐");
        System.out.println("  │ 1. Заполнить коллекцию                 │");
        System.out.println("  │ 2. Выбрать алгоритм сортировки         │");
        System.out.println("  │ 3. Выбрать поле сортировки / чёт-нечёт │");
        System.out.println("  │ 4. Выполнить сортировку и показать     │");
        System.out.println("  │ 5. Записать результат в файл (append)  │");
        System.out.println("  │ 6. Подсчитать вхождения (многопоточно) │");
        System.out.println("  │ 7. Показать текущую коллекцию          │");
        System.out.println("  │ 8. Назад                               │");
        System.out.println("  └────────────────────────────────────────┘");
        System.out.println(" Алгоритм: " + sortContext.getStrategyName() +
                (useEvenNaturalSort ? " + чет/нечет" : ""));
        if (students !=null && !students.isEmpty()) {
            System.out.println(" Коллекция: " + students.size() + " студентов");
        } else {
            System.out.println(" Коллекция пуста");
        }
        System.out.println();
    }

    private void handleFillCollection() {
        System.out.println("  Способы заполнения:");
        System.out.println("    1 — Случайные данные");
        System.out.println("    2 — Ввод вручную");
        System.out.println("    3 — Из файла");
        int way = readInt("  Выберите способ (1-3): ", 1, 3);

        switch (way) {
            case 1 -> fillRandom();
            case 2 -> fillManual();
            case 3 -> fillFromFile();
        }
    }

    private void fillRandom() {
        int count = readInt(" Колличество студентов: ",1,100_000);
        DataProvider provider = new RandomDataProvider();
        students = provider.provide(count);
        System.out.println(" Коллекция заполнена: " + students.size() + " студентов.");
    }

    private void fillManual() {
        int count = readInt(" Колличество студентов: ",1,10_000);
        DataProvider provider = new ManualDataProvider(scanner);
        students = provider.provide(count);
        System.out.println(" Введено: " + students.size() + " студентов.");
    }

    private void fillFromFile() {
        System.out.println(" Путь к CSV-файлу: ");
        String filePath = scanner.nextLine().trim();

        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            System.out.println(" Файл не найден: " + filePath);
            return;
        }

        int maxCount = readInt(" Максимум записей для чтения: ", 0,100_000);
        int count = (maxCount == 0) ? Integer.MAX_VALUE: maxCount;

        DataProvider provider = new FileDataProvider(path);
        students = provider.provide(count);
        System.out.println(" Загружено из файла: " + students.size() + " студентов.");
    }

    private void handleSelectAlgorithm() {
        System.out.println("  Доступные алгоритмы:");
        System.out.println("    1 — Сортировка пузырьком");
        System.out.println("    2 — Быстрая сортировка");
        int choice = readInt("  Выберите алгоритм (1-2): ", 1, 2);

        SortStrategy<Student> selected = switch (choice) {
            case 1 -> new BubbleSort<>();
            case 2 -> new QuickSort<>();
            default -> throw new AssertionError("Недопустимый выбор: " + choice);
        };

        sortContext.setStrategy(selected);
        System.out.println(" Выбран: " + sortContext.getStrategyName());
    }

    private void handleSelectField() {
        System.out.println("  Поля сортировки:");
        System.out.println("    1 — Номер группы");
        System.out.println("    2 — Средний балл");
        System.out.println("    3 — Номер зачётной книжки");
        System.out.println("    4 — Доп. сортировка: чёт-натурал / нечёт-фикс");
        int choice = readInt("  Выберите поле (1-4): ", 1, 4);

        switch (choice) {
            case 1 -> {
                currentComparator = new StudentGroupComparator();
                useEvenNaturalSort = false;
                System.out.println(" Поле: номер группы");
            }
            case 2 -> {
                currentComparator = new StudentScoreComparator();
                useEvenNaturalSort = false;
                System.out.println(" Поле: средний балл");
            }
            case 3 -> {
                currentComparator = new StudentGradeBookComparator();
                useEvenNaturalSort = false;
                System.out.println(" Поле: номер зачетной книжки");
            }
            case 4 -> {
                useEvenNaturalSort = true;
                System.out.println(" Режим: чет-натурал / нечет-фикс");
            }
        }
    }

    private void handleSortAndShow() {
        if (students == null | students.isEmpty()) {
            System.out.println(" Коллекция пуста. Сначала заполните (п.1)");
            return;
        }

        if (useEvenNaturalSort) {
            SortStrategy<Student> evenStrategy = new EvenOddSortStrategy();
            SortContext<Student> evenCtx = new SortContext<>(evenStrategy);
            evenCtx.sort(students, currentComparator);
        } else {
            sortContext.sort(students, currentComparator);
        }

        System.out.println(" Результат сортировки (" + students.size() + " записей):");
        printStudentList(students);
    }

    private void handleWriteToFile() {
        if (students == null || students.isEmpty()) {
            System.out.println(" Коллекция пуста. Сначала заполните (п.1).");
            return;
        }

        System.out.println(" Путь к файлу для записи (result.csv): ");
        String filePath = scanner.nextLine().trim();
        if (filePath.isEmpty()) {
            filePath = "result.csv";
        }
        Path path = Path.of(filePath);

        try {
            ResultWriter.appendToFile(path, students);
            System.out.println(" Записано " + students.size()
                    + " студентов в " + path + " (режим append)." );
        } catch (Exception e) {
            System.out.println(" Ошибка записи: " + e.getMessage());
        }
    }

    private void handleCountOccurrences() {
        if (students == null || students.isEmpty()) {
            System.out.println(" Коллекция пуста. Сначала заполните (п.1).");
            return;
        }

        long target = readLong(" Введите номер зачетки для поиска: ");
        System.out.println(" Многопоточный подсчет...");
        OccurrenceCounter.countCreditBookOccurrences(students, target);
    }

    private void handleShowStudents() {
        if (students == null || students.isEmpty()) {
            System.out.println(" Коллекция пуста. Сначала заполните (п.1).");
            return;
        }
        System.out.println(" Текущая коллекция (" + students.size() + " записей.");
        printStudentList(students);
    }

    private void printStudentList(CustomList<Student> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("   " + (i + 1) + ", " + list.get(i));
        }
    }

    //Абитуриенты

    private void runApplicantsMenu() {
        while (running) {
            printApplicantsMenu();
            int choice = readInt(" Ваш выбор: ",1,6);
            try {
                switch (choice) {
                    case 1 -> applicantAdd();
                    case 2 -> applicantView();
                    case 3 -> applicantEdit();
                    case 4 -> applicantDelete();
                    case 5 -> applicantSortByScore();
                    case 6 -> {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(" Ошибка: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void printApplicantsMenu() {
        System.out.println("  ┌─── АБИТУРИЕНТЫ ────────────────────────┐");
        System.out.println("  │ 1. Добавить абитуриента                │");
        System.out.println("  │ 2. Показать всех                       │");
        System.out.println("  │ 3. Редактировать                       │");
        System.out.println("  │ 4. Удалить                             │");
        System.out.println("  │ 5. Отсортировать по баллу ЕГЭ          │");
        System.out.println("  │ 6. Назад                               │");
        System.out.println("  └────────────────────────────────────────┘");
        if (applicants != null & !applicants.isEmpty()) {
            System.out.println(" Абитуриентов нет: " + applicants.size());
        } else {
            System.out.println(" Список пуст.");
        }
        System.out.println();
    }

    private void applicantAdd() {
        System.out.println(" Новый абитуриент ");
        String name = readString(" Имя: ", false);
        String lastname = readString(" Фамилия: ", false);
        int score = readInt(" Балл ЕГЭ (0-400): ",0,400);

        UniversityApplicant a = new UniversityApplicant.UniversityApplicantBuilder()
                .name(name)
                .lastName(lastname)
                .totalExamScore(score)
                .build();

        if (applicants == null) {
            applicants = new CustomArrayList<>();
        }
        applicants.add(a);
        System.out.println(" Добавлен: " + a);
    }

    private void applicantView() {
        if (applicants == null || applicants.isEmpty()) {
            System.out.println(" Список абитуриентов пуст.");
            return;
        }
        System.out.println("  ┌────────────────────────────────────────────┐");
        System.out.println("  │  № │ Фамилия       │ Имя          │ Балл   │");
        System.out.println("  ├───┼───────────────┼──────────────┼────────┤");
        for (int i = 0; i < applicants.size(); i++) {
            UniversityApplicant a = applicants.get(i);
            System.out.printf("  │ %2d │ %-13s │ %-12s │ %4d   │%n",
                    i + 1, a.getLastName(), a.getName(), a.getExamScore());
        }
        System.out.println("  └────────────────────────────────────────────┘");
    }

    private void applicantEdit() {
        if (applicants == null || applicants.isEmpty()) {
            System.out.println(" Список абитуриентов пуст");
            return;
        }

        System.out.println(" Текущий список:");
        for (int i=0; i < applicants.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + applicants.get(i));
        }

        int index = readInt(" Номер записи для редактирования (1-" +applicants.size() + "):",
                1, applicants.size());
        UniversityApplicant old = applicants.get(index - 1);

        System.out.println(" Текущее значение (Enter = оставить без изменений):");
        String name = readString(" Имя [" + old.getName() + "]: ", true);
        if (name.isEmpty()) {
            name = old.getName();
        }
        String lastname = readString(" Фамилия [" + old.getLastName() + "]: ", true);
        if (lastname.isEmpty()) {
            lastname = old.getLastName();
        }
        String scoreStr = readString(" Балл[" + old.getExamScore() + "]: ", true);
        int score;
        if (scoreStr.isEmpty()) {
            score = old.getExamScore();
        } else {
            score = readInt(" Новый балл ЕГЭ (0-400): ",0,400);
        }

        UniversityApplicant updated = new UniversityApplicant.UniversityApplicantBuilder()
                .name(name)
                .lastName(lastname)
                .totalExamScore(score)
                .build();

        applicants.set(index - 1, updated);
        System.out.println(" Обновлено: " + updated);
    }

    private void applicantDelete() {
        if (applicants == null || applicants.isEmpty()) {
            System.out.println(" Список абитуриентов пуст.");
            return;
        }

        System.out.println(" Текущий список:");
        for (int i = 0; i < applicants.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + applicants.get(i));
        }

        int index = readInt(" Номер записи для удаления (1-" + applicants.size() + "): ",
                1, applicants.size());
        UniversityApplicant removed = applicants.remove(index - 1);
        System.out.println(" Удален: " + removed);
    }

    private void applicantSortByScore() {
        if (applicants == null || applicants.isEmpty()) {
            System.out.println(" Список абитуриентов пуст.");
            return;
        }

        int n = applicants.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - j; j++) {
                UniversityApplicant a = applicants.get(j);
                UniversityApplicant b = applicants.get(j + 1);
                if (a.getExamScore() < b.getExamScore()) {
                    applicants.set(j, b);
                    applicants.set(j + 1, a);
                }
            }
        }

        System.out.println(" Абитуриенты отсортированы по баллу ЕГЭ (убывание): ");
        applicantView();
    }
}