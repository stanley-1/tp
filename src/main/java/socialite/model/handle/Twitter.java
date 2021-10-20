package socialite.model.handle;

import java.util.Optional;

import socialite.commons.util.AppUtil;

public class Twitter extends Handle {

    public static final String MESSAGE_CONSTRAINTS = "Handle should only consist of alphanumerical characters \n"
            + "and underscores, should be between 4-15 characters long";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_]{4,15}$";

    /**
     * Constructor for {@code Twitter} object
     *
     * @param value Twitter handle
     */
    public Twitter(String value) {
        if (value != null && !value.equals("")) {
            AppUtil.checkArgument(isValidHandle(value), MESSAGE_CONSTRAINTS);
        }
        this.value = Optional.ofNullable(value);
    }

    /**
     * Checks if given twitter handle is valid
     */
    public static boolean isValidHandle(String value) {
        return value.matches(VALIDATION_REGEX);
    }

    @Override
    public String getUrl() {
        return "http://www.twitter.com/" + this.get();
    }

    @Override
    public String toString() {
        return this.value.orElse("");
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Twitter && value.equals(((Twitter) obj).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
