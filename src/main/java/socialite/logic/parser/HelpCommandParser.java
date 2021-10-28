package socialite.logic.parser;

import socialite.logic.commands.AddCommand;
import socialite.logic.commands.DeleteCommand;
import socialite.logic.commands.EditCommand;
import socialite.logic.commands.FindCommand;
import socialite.logic.commands.HelpCommand;
import socialite.logic.commands.RemarkCommand;
import socialite.logic.commands.ShareCommand;

/**
 * Parses input arguments (if any) and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public static final String INVALID_KEYWORD = "default";

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     */
    public HelpCommand parse(String args) {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new HelpCommand(INVALID_KEYWORD);
        }

        switch(trimmedArgs) {

        case AddCommand.COMMAND_WORD:
            return new HelpCommand(AddCommand.MESSAGE_USAGE);

        case DeleteCommand.COMMAND_WORD:
            return new HelpCommand(DeleteCommand.MESSAGE_USAGE);

        case EditCommand.COMMAND_WORD:
            return new HelpCommand(EditCommand.MESSAGE_USAGE);

        case FindCommand.COMMAND_WORD:
            return new HelpCommand(FindCommand.MESSAGE_USAGE);

        case RemarkCommand.COMMAND_WORD:
            return new HelpCommand(RemarkCommand.MESSAGE_USAGE);

        case ShareCommand.COMMAND_WORD:
            return new HelpCommand(ShareCommand.MESSAGE_USAGE);

        default:
            return new HelpCommand(INVALID_KEYWORD);
        }
    }
}
