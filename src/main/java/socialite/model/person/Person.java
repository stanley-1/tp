package socialite.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import socialite.commons.util.CollectionUtil;
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
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // Social media handle fields
    private final Facebook facebook;
    private final Instagram instagram;
    private final Telegram telegram;
    private final TikTok tiktok;
    private final Twitter twitter;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Facebook facebook,
                  Instagram instagram, Telegram telegram, TikTok tiktok, Twitter twitter) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
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

    public Facebook getFacebook() {
        return facebook;
    }

    public Instagram getInstagram() {
        return instagram;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getFacebook().equals(getFacebook())
                && otherPerson.getInstagram().equals(getInstagram())
                && otherPerson.getTelegram().equals(getTelegram())
                && otherPerson.getTiktok().equals(getTiktok())
                && otherPerson.getTwitter().equals(getTwitter());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, facebook, instagram, telegram, tiktok, twitter);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        if (getFacebook() != null) {
            builder.append("; Facebook: ").append(getFacebook());
        }

        if (getInstagram() != null) {
            builder.append("; Instagram: ").append(getInstagram());
        }


        if (getTelegram() != null) {
            builder.append("; Telegram: ").append(getTelegram());
        }

        if (getTiktok() != null) {
            builder.append("; TikTok: ").append(getTiktok());
        }

        if (getTwitter() != null) {
            builder.append("; Twitter: ").append(getTwitter());
        }

        return builder.toString();
    }

}
