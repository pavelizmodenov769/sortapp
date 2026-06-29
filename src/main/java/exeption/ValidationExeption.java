package exeption;

public class ValidationExeption {
    public static void validate(String group, double avaregeScore, String creditBook) {
        if (group == null || group.isBlank()) {
            throw new IllegalArgumentException("Укажите номер группы.");
        }

        if (avaregeScore < 0 || avaregeScore > 5) {
            throw new IllegalArgumentException("Оценка не может быть меньше 0 и больше 5.");
        }

        if (creditBook == null || creditBook.isBlank()) {
            throw new IllegalArgumentException("Укажите номер зачетной книжки.");
        }
    }
}
