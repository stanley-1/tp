package socialite.model;

import java.io.File;
import java.nio.file.Path;

public class ProfilePictureSyncModel implements ProfilePictureSyncModelInterface {

    private Path toDelete;

    private String dest;

    private File source;

    @Override
    public void deleteProfilePicture(Path name) {
        this.toDelete = name;
    }

    /**
     * adds profile picture to model
     * @param file
     * @param name
     */
    public void saveProfilePicture(File file, String name) {
        this.dest = name;
        this.source = file;
    }

    public Path getPicToDelete() {
        return this.toDelete;
    }

    public String getDest() {
        return this.dest;
    }

    public File getSourceFile() {
        return this.source;
    }

    /**
     * Resets variables for next picture command
     */
    public void clear() {
        dest = null;
        source = null;
        toDelete = null;
    }

}
