package socialite.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.model.CommandHistory;
import socialite.model.Model;
import socialite.model.ModelManager;
import socialite.model.UserPrefs;
import socialite.model.person.Person;
import socialite.testutil.TypicalIndexes;
import socialite.testutil.TypicalPersons;

import java.io.File;
import java.nio.file.Paths;

public class PictureCommandTest {

    private Model model =
            new ModelManager(
                    TypicalPersons.getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
    private File acceptedFile = Paths.get("data", "profilepictures", "simu.jpeg").toFile();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAddPic = model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        PictureCommand pictureCommand = new PictureCommand(TypicalIndexes.INDEX_FIRST_PERSON, false, acceptedFile);
        Person personWithPic = pictureCommand.addPicToPerson(personToAddPic, acceptedFile, model);

        String expectedMessage = String.format(PictureCommand.MESSAGE_PICTURE_PERSON_SUCCESS, personToAddPic);

        ModelManager expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new CommandHistory());
        expectedModel.setPerson(personToAddPic, personWithPic);

        CommandTestUtil.assertCommandSuccess(pictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListWithoutGui_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PictureCommand pictureCommand = new PictureCommand(outOfBoundIndex, false);

        CommandTestUtil.assertCommandFailure(pictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    // tests that index out of bounds is caught before fileChooser is shown
    @Test
    public void execute_invalidIndexUnfilteredListWithGui_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PictureCommand pictureCommand = new PictureCommand(outOfBoundIndex, true);

        CommandTestUtil.assertCommandFailure(pictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Person personToAddPicture = model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        PictureCommand pictureCommand = new PictureCommand(TypicalIndexes.INDEX_FIRST_PERSON, false, acceptedFile);
        Person personWithPicture = pictureCommand.addPicToPerson(personToAddPicture, acceptedFile, model);

        String expectedMessage = String.format(PictureCommand.MESSAGE_PICTURE_PERSON_SUCCESS, personToAddPicture);

        Model expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new CommandHistory());
        expectedModel.setPerson(personToAddPicture, personWithPicture);

        CommandTestUtil.assertCommandSuccess(pictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PictureCommand pictureCommand = new PictureCommand(outOfBoundIndex, false);

        CommandTestUtil.assertCommandFailure(pictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredListWithNullPicture_showsAborted() {
        PictureCommand pictureCommand = new PictureCommand(TypicalIndexes.INDEX_FIRST_PERSON, false, null);
        String expectedMessage = PictureCommand.MESSAGE_COMMAND_ABORTED;
        CommandTestUtil.assertCommandFailure(pictureCommand, model, expectedMessage);
    }

}
