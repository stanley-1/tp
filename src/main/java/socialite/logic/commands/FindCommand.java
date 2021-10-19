package socialite.logic.commands;

import static java.util.Objects.requireNonNull;

import socialite.commons.core.Messages;
import socialite.model.Model;
import socialite.model.person.containsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters contacts based on specified keywords.\n"
            + "Keywords can be in the form of tags (prefix with 't/'),\n"
            + "handles (prefix with 'p/'), and names (no prefix needed).\n"
            + "Returns all contacts containing ALL keywords listed in the command.\n"
            + "Parameters: find [KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice t/colleagues p/instagram\n"
            + "Type 'find all/' to reset to full list of contacts";

    private final containsKeywordsPredicate predicate;

    public FindCommand(containsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
