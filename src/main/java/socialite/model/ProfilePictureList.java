package socialite.model;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import socialite.storage.ProfilePictureStorageManager;

public class ProfilePictureList implements ProfilePictureListInterface {

    private static ProfilePictureList instance;

    private ArrayList<File> pictures = new ArrayList<>();

    private ProfilePictureList(File[] profilePictures) {
        if (profilePictures != null) {
            for (File profilePicture : profilePictures) {
                pictures.add(profilePicture);
            }
        }
    }

    public static ProfilePictureList getInstance(File[] profilePictures) {
        if (instance == null) {
            instance = new ProfilePictureList(profilePictures);
        }
        return instance;
    }

    public static ProfilePictureList getInstance() {
        return instance;
    }

    @Override
    public File getProfilePicture(String name) {
        for (File file : pictures) {
            if (file.getName().equals(name)) {
                return file;
            }
        }
        return null;
    }

    /**
     * deletes profile picture from the model
     * @param name
     */
    public void deleteProfilePicture(Path name) {
        pictures.stream().filter(f -> f.getName().equals(name.toString()));

    }

    /**
     * adds profile picture to model
     * @param file
     * @param name
     */
    public void saveProfilePicture(File file, String name) {
        pictures.add(ProfilePictureStorageManager.PROFILE_PIC_FOLDER_PATH.resolve(name).toFile());
    }
}
