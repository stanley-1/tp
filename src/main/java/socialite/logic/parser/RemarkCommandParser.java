package socialite.logic.parser;

import static java.util.Objects.requireNonNull;
import static socialite.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static socialite.logic.parser.CliSyntax.PREFIX_REMARK;

import socialite.commons.core.index.Index;
import socialite.commons.exceptions.IllegalValueException;
import socialite.logic.commands.RemarkCommand;
import socialite.logic.parser.exceptions.ParseException;
import socialite.model.person.Remark;
import socialite.storage.Storage;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args, Storage storage) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_HELP_GUIDE), ive);
        }

        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse(null));

        return new RemarkCommand(index, remark);
    }
}
