package socialite.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import socialite.commons.exceptions.DataConversionException;
import socialite.model.ContactList;
import socialite.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link ContactList}.
 */
public interface ContactListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getContactListFilePath();

    /**
     * Returns ContactList data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readContactList() throws DataConversionException, IOException;

    /**
     * @see #getContactListFilePath()
     */
    Optional<ReadOnlyAddressBook> readContactList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveContactList(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveContactList(ReadOnlyAddressBook)
     */
    void saveContactList(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}
