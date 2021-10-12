package socialite.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import socialite.commons.exceptions.IllegalValueException;
import socialite.model.handle.Facebook;
import socialite.model.handle.Instagram;
import socialite.model.handle.Telegram;
import socialite.model.person.Address;
import socialite.model.person.Email;
import socialite.model.person.Name;
import socialite.model.person.Phone;
import socialite.testutil.Assert;
import socialite.testutil.TypicalPersons;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_FACEBOOK = "cannot_have_underscore";
    private static final String INVALID_INSTAGRAM = "cannot_be_longer_than_30_characters";
    private static final String INVALID_TELEGRAM = "cannot.have.dot";

    private static final String VALID_NAME = TypicalPersons.BENSON.getName().toString();
    private static final String VALID_PHONE = TypicalPersons.BENSON.getPhone().toString();
    private static final String VALID_EMAIL = TypicalPersons.BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = TypicalPersons.BENSON.getAddress().toString();
    private static final String VALID_REMARK = TypicalPersons.BENSON.getRemark().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalPersons.BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_FACEBOOK = TypicalPersons.BENSON.getFacebook().toString();
    private static final String VALID_INSTAGRAM = TypicalPersons.BENSON.getInstagram().toString();
    private static final String VALID_TELEGRAM = TypicalPersons.BENSON.getTelegram().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(TypicalPersons.BENSON);
        Assertions.assertEquals(TypicalPersons.BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage =
                String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage =
                String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage =
                String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_REMARK, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage =
                String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, invalidTags,
                VALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidFacebook_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                INVALID_FACEBOOK, VALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage = Facebook.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidInstagram_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                VALID_FACEBOOK, INVALID_INSTAGRAM, VALID_TELEGRAM
        );
        String expectedMessage = Instagram.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                VALID_FACEBOOK, VALID_INSTAGRAM, INVALID_TELEGRAM
        );
        String expectedMessage = Telegram.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
