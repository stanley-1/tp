package socialite.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import socialite.commons.core.LogsCenter;
import socialite.commons.exceptions.DataConversionException;
import socialite.commons.exceptions.IllegalValueException;
import socialite.commons.util.FileUtil;
import socialite.commons.util.JsonUtil;
import socialite.model.ReadOnlyAddressBook;

/**
 * A class to access ContactList data stored as a json file on the hard disk.
 */
public class JsonContactListStorage implements ContactListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonContactListStorage.class);

    private Path filePath;

    public JsonContactListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getContactListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readContactList() throws DataConversionException {
        return readContactList(filePath);
    }

    /**
     * Similar to {@link #readContactList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readContactList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableContactList> jsonContactsList = JsonUtil.readJsonFile(
                filePath, JsonSerializableContactList.class);
        if (jsonContactsList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonContactsList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveContactList(ReadOnlyAddressBook addressBook) throws IOException {
        saveContactList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveContactList(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveContactList(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableContactList(addressBook), filePath);
    }

}
