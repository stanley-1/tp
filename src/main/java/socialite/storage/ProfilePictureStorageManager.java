package socialite.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import socialite.model.ProfilePictureSyncModel;

public class ProfilePictureStorageManager implements ProfilePictureStorage {

    public static final Path PROFILE_PIC_FOLDER_PATH = Paths.get(
            "data", "profilepictures");
    private static ProfilePictureStorageManager instance;

    private ProfilePictureStorageManager() {
        if (!Files.exists(PROFILE_PIC_FOLDER_PATH)) {
            try {
                Files.createDirectories(PROFILE_PIC_FOLDER_PATH);
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            }
        }
    }

    @Override
    public Path getProfilePictureFolderPath() {
        return PROFILE_PIC_FOLDER_PATH;
    }

    public static ProfilePictureStorageManager getInstance() {
        if (instance == null) {
            instance = new ProfilePictureStorageManager();
        }
        return instance;
    }

    @Override
    public void syncProfilePictures(
            ProfilePictureSyncModel.ProfilePictureEditDescriptor profilePictureEditDescription) {
        saveProfilePicture(profilePictureEditDescription.source, profilePictureEditDescription.dest);
        deleteProfilePicture(profilePictureEditDescription.toDelete);
    }

    @Override
    public void deleteProfilePicture(Path name) {
        if (name == null) {
            return;
        }
        try {
            Path pictureToDelete = PROFILE_PIC_FOLDER_PATH.resolve(name);
            Files.delete(pictureToDelete);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void saveProfilePicture(File file, String name) {
        if (file == null || name == null) {
            return;
        }
        try {
            Files.copy(file.toPath(), getProfilePictureFolderPath().resolve(name));
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
