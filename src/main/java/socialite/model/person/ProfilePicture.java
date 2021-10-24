package socialite.model.person;

/**
 * Represents a Person's Profile Picture in address book.
 * Default value is the default profile picture.
 */
public class ProfilePicture {

    public final String value;
    public static final ProfilePicture DEFAULT_PICTURE = new ProfilePicture("/images/default_profile_picture.png");

    public ProfilePicture(String value) {
        this.value = value;
    }
}
