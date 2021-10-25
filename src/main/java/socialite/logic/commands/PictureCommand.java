package socialite.logic.commands;

import socialite.commons.core.Messages;
import socialite.commons.core.index.Index;
import socialite.logic.commands.exceptions.CommandException;
import socialite.model.Model;
import socialite.model.person.Person;
import socialite.model.person.ProfilePicture;
import socialite.storage.Storage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class PictureCommand extends Command {

    //TODO: Add proper error message

    public static final String COMMAND_WORD = "picture";
    private final Index index;
    private final File picture;
    private final Storage storage;

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
        Person personWithPic = addPicToPerson(personToAddPic, picture);

        model.setPerson(personToAddPic, personWithPic);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        //TODO: Use proper feedback message
        return new CommandResult("picture added :-)");
    }

    private Person addPicToPerson(Person person, File file) {
        // TODO: place file in profilepictures folder
        if (!person.getProfilePicture().equals(ProfilePicture.DEFAULT_PICTURE)) {
            // TODO: delete file
            System.out.println(person.getProfilePicture().value);
            this.storage.deleteProfilePicture(
                    person.getProfilePicture().value);
        }
        // TODO: add new file, change person's profile picture
        storage.saveProfilePicture(file, person.getName().fullName);
        person.setProfilePicture(Paths.get(person.getName().fullName + file.getName()));
        return person;
    }

}
