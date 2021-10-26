package socialite.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfilePictureStorageManager implements ProfilePictureStorage {


    public static ProfilePictureStorageManager instance;

    public static final Path PROFILE_PIC_FOLDER_PATH = Paths.get(
            "data", "profilepictures");

    private ProfilePictureStorageManager() {
        if (!Files.exists(PROFILE_PIC_FOLDER_PATH)) {
            try {
                Files.createDirectory(PROFILE_PIC_FOLDER_PATH);
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            }
        }
    }

    @Override
    public File[] getProfilePictures() {
        return new File(PROFILE_PIC_FOLDER_PATH.toString()).listFiles();
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
    public void deleteProfilePicture(Path name) {
        try {
            Path pictureToDelete = PROFILE_PIC_FOLDER_PATH.resolve(name);
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
    public void saveProfilePicture(File file, String prefix) {
        try {
            Files.copy(file.toPath(), getProfilePictureFolderPath().resolve(prefix + file.getName()));
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
