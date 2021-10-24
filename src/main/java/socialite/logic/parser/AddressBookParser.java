package socialite.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import socialite.commons.core.Messages;
import socialite.logic.commands.*;
import socialite.logic.parser.exceptions.ParseException;
import socialite.storage.Storage;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, Storage storage) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, storage);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, storage);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, storage);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments, storage);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments, storage);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments, storage);

        case PictureCommand.COMMAND_WORD:
            return new PictureCommandParser().parse(arguments, storage);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
