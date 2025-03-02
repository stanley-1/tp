package socialite.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import socialite.commons.exceptions.DataConversionException;
import socialite.model.ContactList;
import socialite.model.ReadOnlyContactList;
import socialite.testutil.Assert;
import socialite.testutil.TypicalPersons;

public class JsonContactListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonContactListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readContactList_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readContactList(null));
    }

    private java.util.Optional<ReadOnlyContactList> readContactList(String filePath) throws Exception {
        return new JsonContactListStorage(Paths.get(filePath)).readContactList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readContactList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readContactList("notJsonFormatContactList.json"));
    }

    @Test
    public void readContactList_invalidPersonContactList_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readContactList("invalidPersonContactList.json"));
    }

    @Test
    public void readContactList_invalidAndValidPersonContactList_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readContactList("invalidAndValidPersonContactList.json"));
    }

    @Test
    public void readContactList_duplicatePersonContactList_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readContactList("duplicatePersonContactList.json"));
    }

    @Test
    public void readAndSaveContactList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempContactList.json");
        ContactList original = TypicalPersons.getTypicalContactList();
        JsonContactListStorage jsonContactListStorage = new JsonContactListStorage(filePath);

        // Save in new file and read back
        jsonContactListStorage.saveContactList(original, filePath);
        ReadOnlyContactList readBack = jsonContactListStorage.readContactList(filePath).get();
        assertEquals(original, new ContactList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(TypicalPersons.HOON);
        original.removePerson(TypicalPersons.ALICE);
        jsonContactListStorage.saveContactList(original, filePath);
        readBack = jsonContactListStorage.readContactList(filePath).get();
        assertEquals(original, new ContactList(readBack));

        // Save and read without specifying file path
        original.addPerson(TypicalPersons.IDA);
        jsonContactListStorage.saveContactList(original); // file path not specified
        readBack = jsonContactListStorage.readContactList().get(); // file path not specified
        assertEquals(original, new ContactList(readBack));

    }

    @Test
    public void saveContactList_nullContactList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveContactList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code contactList} at the specified {@code filePath}.
     */
    private void saveContactList(ReadOnlyContactList contactList, String filePath) {
        try {
            new JsonContactListStorage(Paths.get(filePath))
                    .saveContactList(contactList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveContactList_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveContactList(new ContactList(), null));
    }
}
