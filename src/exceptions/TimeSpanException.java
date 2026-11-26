package exceptions;

// Базовий клас для помилок, пов'язаних з TimeSpan
public class TimeSpanException extends RuntimeException {
    public TimeSpanException(String message) {
        super(message);
    }
}
