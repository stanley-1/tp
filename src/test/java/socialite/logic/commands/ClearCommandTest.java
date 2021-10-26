package socialite.logic.commands;

import org.junit.jupiter.api.Test;

import socialite.model.AddressBook;
import socialite.model.CommandHistory;
import socialite.model.Model;
import socialite.model.ModelManager;
import socialite.model.UserPrefs;
import socialite.testutil.TypicalPersons;

import java.io.File;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model =
                new ModelManager(
                        TypicalPersons.getTypicalAddressBook(), new UserPrefs(), new CommandHistory(), new File[0]);
        Model expectedModel =
                new ModelManager(
                        TypicalPersons.getTypicalAddressBook(), new UserPrefs(), new CommandHistory(), new File[0]);
        expectedModel.setAddressBook(new AddressBook());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
