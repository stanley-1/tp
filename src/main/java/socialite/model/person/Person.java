package socialite.model.person;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import socialite.commons.util.CollectionUtil;
import socialite.model.ProfilePictureList;
import socialite.model.handle.Facebook;
import socialite.model.handle.Instagram;
import socialite.model.handle.Telegram;
import socialite.model.handle.TikTok;
import socialite.model.handle.Twitter;
import socialite.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private ProfilePicture profilePicture = ProfilePicture.DEFAULT_PICTURE;

    // Social media handle fields
    private final Facebook facebook;
    private final Instagram instagram;
    private final Telegram telegram;
    private final TikTok tiktok;
    private final Twitter twitter;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Remark remark, Set<Tag> tags,
                  Facebook facebook, Instagram instagram, Telegram telegram, TikTok tiktok, Twitter twitter) {
        CollectionUtil.requireAllNonNull(name, phone, tags);
        this.name = name;
        this.phone = phone;
        this.remark = remark;
        this.tags.addAll(tags);
        this.facebook = facebook;
        this.instagram = instagram;
        this.telegram = telegram;
        this.tiktok = tiktok;
        this.twitter = twitter;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Remark getRemark() {
        return remark;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public TikTok getTiktok() {
        return tiktok;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public File getProfilePictureFile() {
        return ProfilePictureList.getInstance().getProfilePicture(this.profilePicture.value.toString());
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public Instagram getInstagram() {
        return instagram;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public void setProfilePicture(Path value) {
        this.profilePicture = new ProfilePicture(value);
    }
    /**
     * Returns true if both persons have the same name and phone number.
     * Rationale: Two unique persons can share the same first and last name.
     * For example, a user might be friends with two different individuals who are named "Alice Jones".
     * However, each Alice Jones will have a different phone number, similar to a unique National ID.
     * Hence, the phone number shall act as the tiebreaker to differentiate the two individuals instead.
     * Overall, this method defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getRemark().equals(getRemark())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getProfilePicture().equals(getProfilePicture())
                && otherPerson.getFacebook().equals(getFacebook())
                && otherPerson.getInstagram().equals(getInstagram())
                && otherPerson.getTelegram().equals(getTelegram())
                && otherPerson.getTiktok().equals(getTiktok())
                && otherPerson.getTwitter().equals(getTwitter());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, tags, facebook, instagram, telegram, tiktok, twitter);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone());


        if (getFacebook().get() != null) {
            builder.append("; Facebook: ").append(getFacebook().get());
        }

        if (getInstagram().get() != null) {
            builder.append("; Instagram: ").append(getInstagram().get());
        }

        if (getTelegram().get() != null) {
            builder.append("; Telegram: ").append(getTelegram().get());
        }

        if (getTiktok().get() != null) {
            builder.append("; TikTok: ").append(getTiktok().get());
        }

        if (getTwitter().get() != null) {
            builder.append("; Twitter: ").append(getTwitter().get());
        }

        if (getRemark().get() != null) {
            builder.append("; Remark: ").append(getRemark().get());
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        return builder.toString();
    }

}
