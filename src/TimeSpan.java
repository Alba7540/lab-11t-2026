import exceptions.IllegalInputTimeSpanException;
import exceptions.IllegalScaleFactorException;
import exceptions.NegativeTimeSpanException;

public class TimeSpan {
    private int hours;
    private int minutes;

    public TimeSpan() {
        this(0, 0);
    }

    public TimeSpan(int minutes) {
        checkMinutes(minutes);
        this.hours = minutes / 60;
        this.minutes = minutes % 60;
    }

    public TimeSpan(int hours, int minutes) {
        checkHoursAndMinutes(hours, minutes);
        this.hours = hours;
        this.minutes = minutes;
    }

    public TimeSpan(TimeSpan span) {
        this(span.getHours(), span.getMinutes());
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void add(int hours, int minutes) {
        checkHoursAndMinutes(hours, minutes);
        this.hours += hours;
        this.minutes += minutes;
        normalize();
    }

    public void add(int minutes) {
        checkMinutes(minutes);
        this.minutes += minutes;
        normalize();
    }

    public void add(TimeSpan span) {
        add(span.getHours(), span.getMinutes());
    }

    public void subtract(int hours, int minutes) {
        checkHoursAndMinutes(hours, minutes);
        subtract(hours * 60 + minutes);
    }

    public void subtract(int minutes) {
        checkMinutes(minutes);
        int total = getTotalMinutes() - minutes;
        if (total < 0) {
            throw new NegativeTimeSpanException("Інтервал не може стати від'ємним: "
                    + this + " - " + minutes + " хв");
        }
        this.hours = total / 60;
        this.minutes = total % 60;
    }

    public void subtract(TimeSpan span) {
        subtract(span.getHours(), span.getMinutes());
    }

    public double getTotalHours() {
        return hours + minutes / 60.0;
    }

    public int getTotalMinutes() {
        return hours * 60 + minutes;
    }

    public void scale(int factor) {
        if (factor <= 0) {
            throw new IllegalScaleFactorException("Множник має бути більше 0, отримано: " + factor);
        }
        hours *= factor;
        minutes *= factor;
        normalize();
    }

    private void normalize() {
        hours += minutes / 60;
        minutes = minutes % 60;
    }

    private void checkMinutes(int minutes) {
        if (minutes < 0) {
            throw new IllegalInputTimeSpanException("Кількість хвилин не може бути від'ємною: " + minutes);
        }
    }

    private void checkHoursAndMinutes(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) {
            throw new IllegalInputTimeSpanException("Некоректний інтервал: " + hours + " год " + minutes + " хв");
        }
    }

    @Override
    public String toString() {
        return hours + " год " + minutes + " хв";
    }
}
