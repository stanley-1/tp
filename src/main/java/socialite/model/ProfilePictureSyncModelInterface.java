package socialite.model;

import java.nio.file.Path;

public interface ProfilePictureSyncModelInterface {

    /**
     * deletes profile picture from the model
     */
    void deleteProfilePicture(Path name);
}
