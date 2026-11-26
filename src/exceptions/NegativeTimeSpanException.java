package exceptions;

public class NegativeTimeSpanException extends TimeSpanException {
    public NegativeTimeSpanException(String message) {
        super(message);
    }
}
