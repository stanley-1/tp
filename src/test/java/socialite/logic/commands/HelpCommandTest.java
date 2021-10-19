package socialite.logic.commands;

import static socialite.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import socialite.model.Model;
import socialite.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult =
                new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(HelpCommand.GENERIC_RESPONSE), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_add() {
        CommandResult expectedCommandResult =
                new CommandResult(AddCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand(AddCommand.MESSAGE_USAGE), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_delete() {
        CommandResult expectedCommandResult =
                new CommandResult(DeleteCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand(DeleteCommand.MESSAGE_USAGE), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_edit() {
        CommandResult expectedCommandResult =
                new CommandResult(EditCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand(EditCommand.MESSAGE_USAGE), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_find() {
        CommandResult expectedCommandResult =
                new CommandResult(FindCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand(FindCommand.MESSAGE_USAGE), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_remark() {
        CommandResult expectedCommandResult =
                new CommandResult(RemarkCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand(RemarkCommand.MESSAGE_USAGE), model, expectedCommandResult, expectedModel);
    }

}
