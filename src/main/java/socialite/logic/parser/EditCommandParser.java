package socialite.logic.parser;

import static java.util.Objects.requireNonNull;
import static socialite.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static socialite.logic.parser.CliSyntax.PREFIX_EMAIL;
import static socialite.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static socialite.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static socialite.logic.parser.CliSyntax.PREFIX_NAME;
import static socialite.logic.parser.CliSyntax.PREFIX_PHONE;
import static socialite.logic.parser.CliSyntax.PREFIX_REMARK;
import static socialite.logic.parser.CliSyntax.PREFIX_TAG;
import static socialite.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static socialite.logic.parser.CliSyntax.PREFIX_TIKTOK;
import static socialite.logic.parser.CliSyntax.PREFIX_TWITTER;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.EditCommand;
import socialite.logic.parser.exceptions.ParseException;
import socialite.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG,
                        PREFIX_FACEBOOK, PREFIX_INSTAGRAM, PREFIX_TELEGRAM, PREFIX_TIKTOK, PREFIX_TWITTER
                );

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe
            );
        }

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editPersonDescriptor.setRemark(ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }

        if (argMultimap.getValue(PREFIX_TIKTOK).isPresent()) {
            editPersonDescriptor.setTikTok(ParserUtil.parseTikTok(argMultimap.getValue(PREFIX_TIKTOK).orElse(null)));
        }
        if (argMultimap.getValue(PREFIX_TWITTER).isPresent()) {
            editPersonDescriptor.setTwitter(ParserUtil.parseTwitter(argMultimap.getValue(PREFIX_TWITTER).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (argMultimap.getValue(PREFIX_INSTAGRAM).isPresent()) {
            editPersonDescriptor.setInstagram(ParserUtil.parseInstagram(argMultimap.getValue(PREFIX_INSTAGRAM).get()));
        }

        if (argMultimap.getValue(PREFIX_FACEBOOK).isPresent()) {
            editPersonDescriptor.setFacebook(ParserUtil.parseFacebook(argMultimap.getValue(PREFIX_FACEBOOK).get()));
        }

        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            editPersonDescriptor.setTelegram(ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
