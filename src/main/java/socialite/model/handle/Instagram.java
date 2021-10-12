package socialite.model.handle;

import static java.util.Objects.requireNonNull;

import socialite.commons.util.AppUtil;

/**
 * Represents a Person's Instagram handle in SociaLite.
 * Guarantees: immutable; handle is valid as declared in {@link #isValidHandle(String)}
 */
public class Instagram {

    public static final String MESSAGE_CONSTRAINTS =
            "Instagram handles should only contain alphanumeric characters, periods & underscores.\n"
            + "A handle is limited to 30 characters and can't use other punctuation marks.\n"
            + "https://tinyurl.com/instaHandle";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9._]{1,30}$";
    public final String value;

    /**
     * Constructs a {@code Instagram}.
     *
     * @param handle A valid Instagram handle.
     */
    public Instagram(String handle) {
        requireNonNull(handle);
        AppUtil.checkArgument(isValidHandle(handle), MESSAGE_CONSTRAINTS);
        value = handle;
    }

    /**
     * Returns if a given input is a valid Facebook username.
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
                || (other instanceof Instagram // instanceof handles nulls
                && value.equals(((Instagram) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
