package socialite.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.exceptions.CommandException;
import socialite.model.Model;
import socialite.model.person.Person;
import socialite.model.person.ProfilePicture;
import socialite.storage.Storage;

public class PictureCommand extends Command {

    //TODO: Add proper error message

    public static final String COMMAND_WORD = "picture";
    private final Index index;
    private final File picture;
    private final Storage storage;

    /**
     * Creates a command that adds a picture to a person
     * @param index Index of person to add picture to
     * @param picture File to add to person
     * @param storage Storage object for storing file
     */
    public PictureCommand(Index index, File picture, Storage storage) {
        requireNonNull(index);
        requireNonNull(storage);

        this.index = index;
        this.picture = picture;
        this.storage = storage;
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
        //TODO: Use proper feedback message
        return new CommandResult("picture added :-)");
    }

    private Person addPicToPerson(Person person, File file, Model model) {
        if (!person.getProfilePicture().equals(ProfilePicture.DEFAULT_PICTURE)) {
            // delete file if not default picture
            this.storage.deleteProfilePicture(
                    person.getProfilePicture().value);
            model.deleteProfilePicture(person.getProfilePicture().value);
        }
        // add new file, change person's profile picture
        String filename = LocalDateTime.now().toString();
        storage.saveProfilePicture(file, filename);
        model.saveProfilePicture(file, filename);
        person.setProfilePicture(Paths.get(filename));
        return person;
    }

}
