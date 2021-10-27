package socialite.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import socialite.commons.exceptions.IllegalValueException;
import socialite.model.person.Date;

/**
 * Jackson-friendly version of {@link Date}.
 */
class JsonAdaptedDate {
    private static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter RECURRING_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd");

    private final String name;
    private final LocalDate date;
    private final boolean recurring;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code name}.
     */
    @JsonCreator
    public JsonAdaptedDate(@JsonProperty("name") String name, @JsonProperty("date") LocalDate date,
                           @JsonProperty("recurring") boolean recurring) {
        this.name = name;
        this.date = date;
        this.recurring = recurring;
    }

    /**
     * Converts a given {@code Date} into this class for Jackson use.
     */
    public JsonAdaptedDate(Date source) {
        name = source.getName();
        date = source.getDate();
        recurring = source.isRecurring();
    }

    /**
     * Converts this Jackson-friendly adapted date object into the model's {@code Date} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Date.
     */
    public Date toModelType() throws IllegalValueException {
        String constructedDate = name + ":" + (recurring
                ? RECURRING_DATE_FORMATTER.format(date)
                : FULL_DATE_FORMATTER.format(date));
        if (!Date.isValidDate(constructedDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(constructedDate);
    }

}
