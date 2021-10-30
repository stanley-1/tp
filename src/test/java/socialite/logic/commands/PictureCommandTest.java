package socialite.logic.commands;

import static socialite.logic.commands.CommandTestUtil.assertCommandFailure;
import static socialite.logic.commands.CommandTestUtil.assertCommandSuccess;
import static socialite.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static socialite.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.jupiter.api.Test;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.model.CommandHistory;
import socialite.model.Model;
import socialite.model.ModelManager;
import socialite.model.UserPrefs;


public class PictureCommandTest {

    private final Model model = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), new CommandHistory());

    @Test
    public void execute_picture_isNull() {
        PictureCommand pictureCommand = new PictureCommand(INDEX_FIRST_PERSON, null);
        assertCommandSuccess(pictureCommand, model, "Command aborted", model);
    }

    @Test
    public void execute_personIndexOutOfBounds_failure() {
        PictureCommand pictureCommand = new PictureCommand(Index.fromOneBased(999), new File("/dummy.png"));
        assertCommandFailure(pictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addPicture_success() {
        PictureCommand pictureCommand = new PictureCommand(INDEX_FIRST_PERSON, new File("/dummy.png"));
        assertCommandSuccess(pictureCommand, model, "picture added", model);
    }



}
