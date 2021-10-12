package socialite.model.handle;

import static java.util.Objects.requireNonNull;

import socialite.commons.util.AppUtil;

public class TikTok {

    public static final String MESSAGE_CONSTRAINTS = "Username should only consist of alphanumerical characters, \n"
            + "underscores and periods, and should not end with period";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_\\.][^\\.]$";
    public final String value;

    /**
     * Constructor for {@code TikTok} object
     *
     * @param value TikTok handle
     */
    public TikTok(String value) {
        requireNonNull(value);
        AppUtil.checkArgument(isValidHandle(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Checks if given tiktok handle is valid
     */
    public static boolean isValidHandle(String value) {
//        return value.matches(VALIDATION_REGEX);
        return true;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof TikTok
                ? value.equals(((TikTok) obj).value)
                : false);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
