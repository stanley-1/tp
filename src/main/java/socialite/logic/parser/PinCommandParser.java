package socialite.logic.parser;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.PinCommand;
import socialite.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PinCommand object
 */
public class PinCommandParser implements Parser<PinCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PinCommand
     * and returns a PinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PinCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PinCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_HELP_GUIDE), pe);
        }
    }
}
