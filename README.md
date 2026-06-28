# SortApp — приложение сортировки студентов

## Структура проекта

```
sortapp/
├── pom.xml                      # Maven-сборка (опционально)
├── .gitignore
├── README.md                    # этот файл
└── src/
    ├── main/java/com/team/sortapp/
    │   ├── model/               # Dev 1: Student + Builder
    │   ├── collection/          # Dev 1: CustomList, CustomArrayList
    │   ├── exception/           # Dev 1: ValidationException
    │   ├── strategy/            # Dev 2: SortStrategy + алгоритмы
    │   ├── comparator/          # Dev 2: компараторы + чёт/нечёт сортировка
    │   ├── io/                  # Dev 3: ввод/вывод/валидация/стримы
    │   ├── concurrent/          # Dev 4: многопоточный подсчёт
    │   ├── ui/                  # Dev 4: меню + главный цикл
    │   └── Application.java     # Dev 4: точка входа
    └── test/java/com/team/sortapp/manual/   # ручные тесты (по модулям)
```

## Распределение веток (команда из 4 человек)

| Разработчик | Ветка                  | Зона ответственности                                                   | Нагрузка |
|-------------|------------------------|------------------------------------------------------------------------|----------|
| Dev 1       | `feature/domain`       | `Student`, `Builder`, `CustomList`, `ValidationException`              | средняя  |
| Dev 2       | `feature/sorting`      | `SortStrategy` + алгоритмы + 3 компаратора + доп. задание 1 (чёт/нечёт)| высокая  |
| Dev 3       | `feature/io`           | ввод/вывод, валидация, стримы, append (доп. 2, доп. 3)                 | высокая  |
| Dev 4       | `feature/threading-ui` | многопоточность (доп. 4), UI, главный цикл, интеграция, Git            | высокая  |

## Порядок слияния в `master` (строго!)

```
feature/domain ──► feature/sorting ──┐
                                     │
feature/io ──────────────────────────┼──► feature/threading-ui ──► master
```

- `feature/domain` мержится первым — от него зависят все.
- `feature/sorting` и `feature/io` можно мержить параллельно (не зависят друг от друга).
- `feature/threading-ui` мержится последним — интегрирует все модули.

## Сборка и запуск (без Maven)

Из папки `sortapp/`:

```bash
# Компиляция
javac -encoding UTF-8 -d out \
  $(find src/main/java -name "*.java")

# Запуск
java -cp out com.team.sortapp.Application
```

## Сборка и запуск (через Maven, опционально)

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.team.sortapp.Application"
```

## Соглашения по кодстайлу (Java Conventions)

- Пакеты — `lowercase`, классы — `PascalCase`, методы/поля — `camelCase`, константы — `UPPER_SNAKE`.
- Один класс — один файл (кроме вложенных, как `Builder`).
- Javadoc на всех публичных методах интерфейсов и ключевых классов.
- Отступы — 4 пробела, кодировка UTF-8.
- **Запрещено** использовать готовые реализации сортировки (`Arrays.sort`,
  `Collections.sort`, `List.sort`, `Stream.sorted`) и готовые паттерны из библиотек.

## Тестирование

Тесты — **ручные**, в отдельных классах с `main`-методом в пакете
`com.team.sortapp.manual`. Запуск каждого:

```bash
java -cp out com.team.sortapp.manual.StudentManualTest
```
