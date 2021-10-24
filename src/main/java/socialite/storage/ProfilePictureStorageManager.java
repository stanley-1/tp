package socialite.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfilePictureStorageManager implements ProfilePictureStorage {

    public static final Path PROFILE_PIC_FOLDER_PATH = Paths.get(
            "src", "main", "resources", "images", "profilepictures");

    public ProfilePictureStorageManager() {
        if (!Files.exists(PROFILE_PIC_FOLDER_PATH)) {
            try {
                Files.createDirectory(PROFILE_PIC_FOLDER_PATH);
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            }
        }
    }

    @Override
    public Path getProfilePictureFolderPath() {
        return PROFILE_PIC_FOLDER_PATH;
    }

    @Override
    public void deleteProfilePicture(String name) {
        try {
            Path pictureToDelete = getProfilePictureFolderPath().resolve(name);
            Files.delete(pictureToDelete);
        } catch (InvalidPathException ipe) {
            System.out.println(ipe.toString());
        } catch (NoSuchFileException nsfe) {
            System.out.println(nsfe.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }

    @Override
    public void saveProfilePicture(File file) {
        try {
            Files.copy(file.toPath(), getProfilePictureFolderPath().resolve(file.getName()));
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
