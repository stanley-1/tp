package socialite.storage;

import socialite.model.person.ProfilePicture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface ProfilePictureStorage {

    /**
     * Returns the folder path containing all profile pictures
     *
     * @return path to folder containing profile pictures
     */
    Path getProfilePictureFolderPath();

    void deleteProfilePicture(Path name);

    void saveProfilePicture(File profilePicture, String prefix);

}
