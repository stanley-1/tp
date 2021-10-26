package socialite.model.person;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be formatted as NAME:YYYY-MM-DD or NAME:MM-DD for recurring dates";

    public static final String VALIDATION_REGEX =
            "([\\w\\s]+):(?:(\\d{4})-)?(\\d{2})-(\\d{2})\\s*";

    private String name;
    private LocalDate date;
    private boolean recurring;

    /**
     * Construct a new {@code Date} with a given name and value,
     * by parsing the input string.
     * @param date The input string to parse.
     */
    public Date(String date) {
        Pattern pattern = Pattern.compile(VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(date);

        matcher.find();
        String name = matcher.group(1);
        String yearString = matcher.group(2);
        boolean recurring = yearString == null;

        int year = recurring ? 0 : Integer.parseInt(yearString);
        int month = Integer.parseInt(matcher.group(3));
        int day = Integer.parseInt(matcher.group(4));

        this.name = name;
        this.date = LocalDate.of(year, month, day);
        this.recurring = recurring;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isRecurring() {
        return recurring;
    }

    /**
     * Returns true if a given string is a valid sequence of dates.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return name + ": " + date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && name.equals(((Date) other).name)
                && date.equals(((Date) other).date)
                && recurring == ((Date) other).recurring); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, recurring);
    }
}
