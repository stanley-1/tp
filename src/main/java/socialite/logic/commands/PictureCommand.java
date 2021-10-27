package socialite.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.exceptions.CommandException;
import socialite.model.Model;
import socialite.model.person.Person;
import socialite.model.person.ProfilePicture;

public class PictureCommand extends Command {

    public static final String COMMAND_WORD = "picture";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a profile picture to the person identified by the index number"
            + " used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_HELP_GUIDE =
            "Enter picture INDEX to add a profile picture to the person at INDEX";
    private final Index index;
    private final File picture;

    /**
     * Creates a command that adds a picture to a person
     * @param index Index of person to add picture to
     * @param picture File to add to person
     */
    public PictureCommand(Index index, File picture) {
        requireNonNull(index);

        this.index = index;
        this.picture = picture;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (this.picture == null) {
            return new CommandResult("Command aborted");
        }
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddPic = lastShownList.get(index.getZeroBased());
        Person personWithPic = addPicToPerson(personToAddPic, picture, model);

        model.setPerson(personToAddPic, personWithPic);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult("picture added", false, false, true);
    }

    private Person addPicToPerson(Person person, File file, Model model) {
        if (!person.getProfilePicture().equals(ProfilePicture.DEFAULT_PICTURE)) {
            // delete file if not default picture
            model.deleteProfilePicture(person.getProfilePicture().value);
        }

        // add new file, change person's profile picture
        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss"));
        model.saveProfilePicture(file, filename);
        person.setProfilePicture(Paths.get(filename));
        return person;
    }

}
