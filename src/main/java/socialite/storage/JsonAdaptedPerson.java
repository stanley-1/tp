package socialite.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import socialite.commons.exceptions.IllegalValueException;
import socialite.model.handle.Facebook;
import socialite.model.handle.Instagram;
import socialite.model.handle.Telegram;
import socialite.model.handle.TikTok;
import socialite.model.handle.Twitter;
import socialite.model.person.Name;
import socialite.model.person.Person;
import socialite.model.person.Phone;
import socialite.model.person.Remark;
import socialite.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String remark;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String facebook;
    private final String instagram;
    private final String telegram;
    private final String tiktok;
    private final String twitter;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("remark") String remark, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("facebook") String facebook, @JsonProperty("instagram") String instagram,
            @JsonProperty("telegram") String telegram, @JsonProperty("twitter") String twitter,
            @JsonProperty("tiktok") String tiktok) {
        this.name = name;
        this.phone = phone;
        this.remark = remark;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.facebook = facebook;
        this.instagram = instagram;
        this.telegram = telegram;
        this.tiktok = tiktok;
        this.twitter = twitter;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        remark = source.getRemark().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        facebook = source.getFacebook().value;
        instagram = source.getInstagram().value;
        telegram = source.getTelegram().value;
        tiktok = source.getTiktok().value;
        twitter = source.getTwitter().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (facebook != null && !Facebook.isValidHandle(facebook)) {
            throw new IllegalValueException(Facebook.MESSAGE_CONSTRAINTS);
        }
        final Facebook modelFacebook = facebook != null ? new Facebook(facebook) : null;

        if (instagram != null && !Instagram.isValidHandle(instagram)) {
            throw new IllegalValueException(Instagram.MESSAGE_CONSTRAINTS);
        }
        final Instagram modelInstagram = instagram != null ? new Instagram(instagram) : null;

        if (telegram != null && !Telegram.isValidHandle(telegram)) {
            throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
        }

        final Telegram modelTelegram = telegram != null ? new Telegram(telegram) : null;

        if (twitter == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Twitter.class.getSimpleName())));
        }
        if (!Twitter.isValidHandle(twitter)) {
            throw new IllegalValueException(Twitter.MESSAGE_CONSTRAINTS);
        }
        final Twitter modeTwitter = new Twitter(twitter);

        if (tiktok == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TikTok.class.getSimpleName())));
        }
        if (!TikTok.isValidHandle(tiktok)) {
            throw new IllegalValueException(TikTok.MESSAGE_CONSTRAINTS);
        }
        final TikTok modelTikTok = new TikTok(tiktok);

        return new Person(modelName, modelPhone, modelRemark, modelTags, modelFacebook,
                modelInstagram, modelTelegram, modelTikTok, modeTwitter);
    }
}
