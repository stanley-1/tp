package socialite.model.handle;

import static java.util.Objects.requireNonNull;

import socialite.commons.util.AppUtil;

/**
 * Represents a Person's Facebook username in SociaLite.
 * Guarantees: immutable; handle is valid as declared in {@link #isValidHandle(String)}
 */
public class Facebook {

    public static final String MESSAGE_CONSTRAINTS =
            "A Facebook username should only contain alphanumeric characters and full stops.\n"
            + "It must also be at least 5 characters long.\n"
            + "https://www.facebook.com/help/105399436216001/?helpref=uf_share";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9.]{5,}$";
    public final String value;

    /**
     * Constructs a {@code Facebook}.
     *
     * @param handle A valid Facebook username.
     */
    public Facebook(String handle) {
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
                || (other instanceof Facebook // instanceof handles nulls
                && value.equals(((Facebook) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
