package socialite.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import socialite.commons.exceptions.DataConversionException;
import socialite.model.ReadOnlyAddressBook;
import socialite.model.ReadOnlyCommandHistory;
import socialite.model.ReadOnlyUserPrefs;
import socialite.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, CommandHistoryStorage, ProfilePictureStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Optional<ReadOnlyCommandHistory> readCommandHistory() throws DataConversionException, IOException;

    @Override
    void saveCommandHistory(ReadOnlyCommandHistory commandHistory) throws IOException;
}
