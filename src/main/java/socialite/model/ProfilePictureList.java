package socialite.model;

import socialite.storage.ProfilePictureStorage;
import socialite.storage.ProfilePictureStorageManager;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class ProfilePictureList implements ProfilePictureListInterface {

    private static ProfilePictureList instance;

    public ArrayList<File> pictures = new ArrayList<>();

    public static ProfilePictureList getInstance(File[] profilePictures) {
        if (instance == null) {
            instance = new ProfilePictureList(profilePictures);
        }
        return instance;
    }

    public static ProfilePictureList getInstance() {
        return instance;
    }

    private ProfilePictureList(File[] profilePictures) {
        if (profilePictures != null) {
            for (File profilePicture : profilePictures) {
                pictures.add(profilePicture);
            }
        }
    }

    public File getProfilePicture(String name) {
        for (File file : pictures) {
            if (file.getName().equals(name)) {
                return file;
            }
        }
        return null;
    }

    public void deleteProfilePicture(Path name) {
        pictures.stream().filter(f -> f.getName().equals(name.toString()));
    }

    public void saveProfilePicture(File file, String prefix) {
        pictures.add(ProfilePictureStorageManager.PROFILE_PIC_FOLDER_PATH.resolve(prefix + file.getName()).toFile());
    }
}
