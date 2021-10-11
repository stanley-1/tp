package socialite.model.handle;

import static java.util.Objects.requireNonNull;

import socialite.commons.util.AppUtil;

public class Twitter {

    public final String value;
    public static final String MESSAGE_CONSTRAINTS = "Handle should only consist of alphanumerical characters" +
            "and underscores, should be between 4-15 characters long";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_]{4,15}";

    public Twitter(String value) {
        requireNonNull(value);
        AppUtil.checkArgument(isValidHandle(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public static boolean isValidHandle(String value) {
        return value.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof Twitter
                ? value.equals(((Twitter) obj).value)
                : false);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
