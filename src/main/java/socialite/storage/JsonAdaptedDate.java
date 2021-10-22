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
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String name;
    private final LocalDate date;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code name}.
     */
    @JsonCreator
    public JsonAdaptedDate(@JsonProperty("name") String name, @JsonProperty("date") LocalDate date) {
        this.name = name;
        this.date = date;
    }

    /**
     * Converts a given {@code Date} into this class for Jackson use.
     */
    public JsonAdaptedDate(Date source) {
        name = source.getName();
        date = source.getDate();
    }

    /**
     * Converts this Jackson-friendly adapted date object into the model's {@code Date} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Date.
     */
    public Date toModelType() throws IllegalValueException {
        String constructedDate = name + ":" + formatter.format(date);
        if (!Date.isValidDate(constructedDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(constructedDate);
    }

}
