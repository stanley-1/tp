package socialite.logic.parser;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.ShareCommand;
import socialite.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShareCommand object
 */
public class ShareCommandParser implements Parser<ShareCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShareCommand
     * @throws ParseException if the user input does not conform with the expected format
     */
    public ShareCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShareCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_HELP_GUIDE), pe);
        }
    }
}
