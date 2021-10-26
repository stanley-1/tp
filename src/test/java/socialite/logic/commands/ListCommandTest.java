package socialite.logic.commands;

import static socialite.logic.commands.CommandTestUtil.assertCommandSuccess;
import static socialite.logic.commands.CommandTestUtil.showPersonAtIndex;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import socialite.model.CommandHistory;
import socialite.model.Model;
import socialite.model.ModelManager;
import socialite.model.UserPrefs;
import socialite.testutil.TypicalIndexes;
import socialite.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                TypicalPersons.getTypicalAddressBook(), new UserPrefs(), new CommandHistory(), new File[0]);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new CommandHistory(), new File[0]);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, TypicalIndexes.INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
