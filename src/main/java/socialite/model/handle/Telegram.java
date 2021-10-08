package socialite.model.handle;

import static java.util.Objects.requireNonNull;

import socialite.commons.util.AppUtil;

/**
 * Represents a Person's Telegram handle in SociaLite.
 * Guarantees: immutable; handle is valid as declared in {@link #isValidHandle(String)}
 */
public class Telegram {

    public static final String MESSAGE_CONSTRAINTS =
            "Telegram handles should only contain alphanumeric characters and underscores,"
            + "and it should be at least 5 characters long.\n"
            + "https://telegram.org/faq#q-what-can-i-use-as-my-username";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_]{5,}$";
    public final String value;

    /**
     * Constructs a {@code Telegram}.
     *
     * @param handle A valid Telegram handle.
     */
    public Telegram(String handle) {
        requireNonNull(handle);
        AppUtil.checkArgument(isValidHandle(handle), MESSAGE_CONSTRAINTS);
        value = handle;
    }

    /**
     * Returns if a given handle is a valid Telegram handle.
     */
    public static boolean isValidHandle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Telegram // instanceof handles nulls
                && value.equals(((Telegram) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
