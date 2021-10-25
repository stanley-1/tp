package socialite.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ProfilePictureStorageManager implements ProfilePictureStorage {

    public ArrayList<File> pictures = new ArrayList<>();

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
        File[] files = new File(PROFILE_PIC_FOLDER_PATH.toString()).listFiles();
        for (File file : files) {
            pictures.add(file);
        }
    }

    @Override
    public Path getProfilePictureFolderPath() {
        return PROFILE_PIC_FOLDER_PATH;
    }

    public File get(String name) {
        for (File file : pictures) {
            if (file.getName().equals(name)) {
                return file;
            }
        }
        return null;
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
            pictures.stream().filter(f -> f.getName().equals(name.toString()));
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
            pictures.add(getProfilePictureFolderPath().resolve(prefix + file.getName()).toFile());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
    }
}
