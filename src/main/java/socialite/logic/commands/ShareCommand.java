package socialite.logic.commands;


import static java.util.Objects.requireNonNull;

import java.util.List;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.exceptions.CommandException;
import socialite.model.Model;
import socialite.model.person.Person;

/**
 * Copy a person's contact info to the system clipboard using its displayed index from the address book.
 */
public class ShareCommand extends Command {

    public static final String COMMAND_WORD = "share";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copy a person's contact info to the system clipboard "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHARE_PERSON_SUCCESS = "Copied to the Clipboard:\n"
            + "Hey, have you tried SociaLite? I have enjoyed using it, you should check it out too!\n"
            + "";

    public static final String MESSAGE_HELP_GUIDE = "Enter 'help share' for in-app guidance.";

    private final Index targetIndex;

    public ShareCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShare = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SHARE_PERSON_SUCCESS, personToShare));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShareCommand // instanceof handles nulls
                && targetIndex.equals(((ShareCommand) other).targetIndex)); // state check
    }
}
