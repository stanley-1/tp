package socialite.storage;

import java.io.File;
import java.nio.file.Path;

import socialite.model.ProfilePictureSyncModel;

public interface ProfilePictureStorage {

    /**
     * Returns the folder path containing all profile pictures
     *
     * @return path to folder containing profile pictures
     */
    Path getProfilePictureFolderPath();

    void deleteProfilePicture(Path name);

    void saveProfilePicture(File profilePicture, String prefix);

    void syncProfilePictures(ProfilePictureSyncModel.ProfilePictureEditDescriptor profilePictureEditDescriptor);

}
