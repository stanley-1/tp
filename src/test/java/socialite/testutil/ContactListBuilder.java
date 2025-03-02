package socialite.testutil;

import socialite.model.ContactList;
import socialite.model.person.Person;

/**
 * A utility class to help with building ContactList objects.
 * Example usage: <br>
 *     {@code ContactList cl = new ContactListBuilder().withPerson("John", "Doe").build();}
 */
public class ContactListBuilder {

    private ContactList contactList;

    public ContactListBuilder() {
        contactList = new ContactList();
    }

    public ContactListBuilder(ContactList contactList) {
        this.contactList = contactList;
    }

    /**
     * Adds a new {@code Person} to the {@code ContactList} that we are building.
     */
    public ContactListBuilder withPerson(Person person) {
        contactList.addPerson(person);
        return this;
    }

    public ContactList build() {
        return contactList;
    }
}
