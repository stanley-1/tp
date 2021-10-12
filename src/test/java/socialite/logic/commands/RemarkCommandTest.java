package socialite.logic.commands;

import static socialite.logic.commands.CommandTestUtil.assertCommandFailure;
import static socialite.logic.commands.RemarkCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static socialite.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import socialite.model.Model;
import socialite.model.ModelManager;
import socialite.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new RemarkCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
