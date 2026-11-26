import exceptions.TimeSpanException;

// Перевірка завдання 1 (TimeSpan з власними виключеннями)
public class TimeSpanDemo {
    public static void main(String[] args) {
        TimeSpan span = new TimeSpan(2, 30);
        System.out.println("span = " + span);

        span.add(1, 45);
        System.out.println("add(1, 45) -> " + span);
        span.subtract(50);
        System.out.println("subtract(50) -> " + span);
        span.scale(2);
        System.out.println("scale(2) -> " + span);

        try {
            new TimeSpan(2, 75);
        } catch (TimeSpanException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        try {
            span.add(-10);
        } catch (TimeSpanException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        try {
            span.subtract(new TimeSpan(100, 0));
        } catch (TimeSpanException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        try {
            span.scale(0);
        } catch (TimeSpanException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        System.out.println("Після всіх помилок span не змінився: " + span);
    }
}
