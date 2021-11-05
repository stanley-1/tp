package socialite.logic.parser;

import static java.util.Objects.requireNonNull;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.PictureCommand;
import socialite.logic.parser.exceptions.ParseException;

public class PictureCommandParser implements Parser<PictureCommand> {

    @Override
    public PictureCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (!args.trim().matches("\\d+")) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PictureCommand.MESSAGE_HELP_GUIDE));
        }

        return new PictureCommand(index, true);
    }
}
