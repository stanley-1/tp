package socialite.storage;

import java.io.File;
import java.nio.file.Path;

public interface ProfilePictureStorage {

    /**
     * Returns the folder path containing all profile pictures
     *
     * @return path to folder containing profile pictures
     */
    Path getProfilePictureFolderPath();

    void deleteProfilePicture(Path name);

    File[] getProfilePictures();

    void saveProfilePicture(File profilePicture, String prefix);

}
