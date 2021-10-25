---
layout: page
title: User Guide
---
SociaLite is a **desktop app for connecting you with your contacts’ social media pages, optimised for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you can type fast, SociaLite can get you to your contacts’ social media pages faster than traditional GUI apps!

* Table of Contents
1. [Features](#features)
    1. [Basic Functionality](#basic_functionality)
        1. Adding a person: add
        2. Listing all persons: list
        3. Editing a person: edit
        4. Locating persons by name: find
        5. Deleting a person: delete
    2. [Organisation of Contacts](#organisation)
        1. Create categories to group contacts: tag
        2. Edit categories as and when required: tag -u
        3. Query a category of contacts: tag
        4. Delete categories associated with contacts: tag -d
        5. Filter contacts based on social media platform: [PLATFORM]
        6. Track when I last queried my contact’s information: last
    3. [Customisation Tools](#customisation)
       1. Adding remarks about a specific contact: remark
       2. View dashboard/contact card associated with a specific contact: view
    4. [Help Guide and Exiting](#help_guide)
       1. Viewing help: help
       2. Remove all data: purge
       3. Exiting the program: exit
       4. Saving the data
       5. Editing the data file
2. [Coming Soon](#coming_soon)
   1. Adding a profile picture: picture
   2. Set reminders for a specific contact: remind
   3. Adding dates of special occasions associated with a specific contact: date
   4. Forwarding a contact card: forward
3. [FAQ](#faq)
4. [Command Summary](#summary) 

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `socialite.jar` from [here](https://github.com/AY2122S1-CS2103T-F11-4/tp/).

1. Copy the file to the folder you want to use as the _home folder_ for your SociaLite.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features <a name="features"></a> 

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

## Basic Functionality <a name="basic_functionality"></a>

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

## Organisation of Contacts <a name="organisation"></a>

### Create categories to group contacts: `tag`
Creates tags to categorise the contacts in the address book.

Format: `tag INDEX [t/TAG]`
* Add a category `TAG` to the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `tag 2 t/family` Adds a tag called “family” to the 2nd person in the address book.
* `tag 6 t/badminton t/football` Adds 2 tags named “badminton” and “football” to the 6th person in the address book.

### Edit categories as and when required: `tag -u`
Edits tags that are associated with contacts in the address book.

Format: `tag -u INDEX [t/TAG] [t/NEWTAG]`
* Edit and change a category `TAG` to `NEWTAG` for a person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The tag must be existent for the person enquired upon.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `tag -u 2 t/famy t/family` Changes a tag called “famy” to “family” for the 2nd person in the address book.
* `tag -u 6 t/badminton t/baddy` Changes the tag “badminton” to “baddy” for the 6th person in the address book.

### Query a category of contacts: `tag`

Finds out the contacts that are associated with a particular tag.

Format: `tag [t/TAG]`
* Queries all contacts that are tied to `TAG`.
* The tag must be existent and associated with certain contacts in the address book.

Examples:
* `tag t/family` Checks all contacts in the address book which the user categorises as “family”.
* `tag t/work` Checks all contacts in the address book which the user categorises as “work”.

### Delete categories associated with contacts: `tag -d`

Deletes tags that are associated with contacts in the address book.

Format: `tag -d INDEX [t/TAG]`
* Delete a category `TAG` to the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `tag -d 2 t/family` Deletes a tag called “family” to the 2nd person in the address book.
* `tag 6 t/badminton t/football` followed by `tag 6 -d t/` deletes both tags “badminton” and “football” from the 6th person in the address book.

### Filter contacts based on social media platform: `[PLATFORM]`

Show all contacts with their handles on the particular social media platform.

Format: `[PLATFORM]`
* The social media platform must be supported by SociaLite, e.g. `instagram`, `facebook`, `twitter`, `tiktok`, `snapchat` etc.

Examples:
* `instagram` Returns all contacts with their instagram handles.
* `twitter` Returns all contacts with their twitter usernames.

### Track when I last queried my contact’s information: `last`

Finds out the last queried contact.

Format: `last`

Examples:
* `last` Finds out the most recently queried contact, Betsy, with all her contact information for various fields.

## Customisation Tools <a name="customisation"></a>
For the purpose of illustrating examples in this section, assume that the following contacts exist within the user’s app:
1. Annie Baker
2. Charlie Decker

### Adding remarks about a specific contact: `remark`

Adds special notes about a contact.

Format: `remark INDEX r/[TEXT]`
* Adds a note about the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `remark 2 r/Doesn’t like vegetables`  adds the remark “Doesn’t like vegetables” to Charlie Decker’s listing in the address book.
* `find Annie` followed by `remark 1 r/Loves peas` adds the remark “Loves peas” to Annie Baker’s listing in the app.

### View dashboard/contact card associated with a specific contact: `view`

Opens a contact card which presents all previously stored details (e.g.: name, social media handles, special events) associated with the contact. In particular, any events associated with the contact will be enumerated and presented in a numbered list.

Format: `view INDEX`
* Adds a date for the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `view 1` opens Annie Baker’s contact card for the user’s perusal.
* `find Charlie` followed by `view 1` opens Charlie Decker’s contact card for viewing.

## Help Guide & Exiting <a name="help_guide"></a>

### Viewing help: `help`
Shows a message explaining how to access the help page.

Format: `help`

### Remove all data: `purge`
Removes all contacts in the app.

Format: purge

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

SociaLite data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

SociaLite data are saved as a JSON file `[JAR file location]/data/SociaLite.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, SociaLite will discard all data and start with an empty data file at the next run.
</div>

## Coming Soon <a name="coming_soon"></a>

### Adding a profile picture: `picture`
Adds a profile picture to an existing contact.

Format: `picture INDEX l/FILEPATH`
* Adds a profile picture to the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `picture 1 l/Desktop/AnniePic.png`  adds the image found at the specified file path to Annie Baker’s listing in the app.
* `find Charlie` followed by `picture 1 l/Desktop/CharliePic.png` adds the image found at the specified file path to Charlie Decker’s listing in the app.

### Set reminders for a specific contact: `remind`
Set reminders for special occasions associated with a contact. In order to set a reminder, the date of the event has to be previously added via `addDate`.

Format: `remind INDEX r/[DAYS]`
* Sets a reminder for an event at the specified `INDEX` associated with the contact.
* The index refers to the index number shown in the contact’s list of events.
* The index **must be a positive integer** 1, 2, 3, …​
* Users will be reminded of the event `[DAYS]` days in advance of the actual date.
* By default, a reminder will appear on the user’s dashboard 3 days prior to the event if no input is specified under the `[DAYS]` field.

Examples:
For the purpose of illustrating examples in this feature, assume that the following events exist within Annie Baker’s contact card:

1. 14 Sep 2021 Meeting
2. 10 Oct 2021 Lunch Appointment

* `remind 1 r/7`  sets a reminder for “Meeting”. Users will be reminded of this meeting through a banner on the app’s initialisation page daily from 7 Sep 2021 (i.e.: 7 days before the event).
* `remind 2` sets a reminder for “Lunch Appointment”. Users will be reminded of this event through a banner on the app’s initialization page daily from 7 Oct 2021 (i.e.: 3 days before the event).

### Adding dates of special occasions associated with a specific contact: `date`
Adds dates of special occasions (birthdays, appointments) associated with a contact.

Format: `date INDEX d/YYYY-MM-DD i/DETAILS`
* Adds a date for the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Date must be presented in `YYYY-MM-DD` format.
* Significance of the specified date is stated at the end under the `DETAILS` field.

Examples:
* `list` followed by `date 1 d/2021-09-14 i/Meeting` adds the event “Meeting” which falls on 14 Sep 2021, to Annie Baker’s listing in the app.
* `find Annie` followed by `date 1 d/2021-10-10 i/Lunch Appointment` adds the event “Lunch Appointment” which falls on 10 Oct 2021 to Annie Baker’s listing.

### Forwarding a contact card: forward
Forwards the contact card of the specified person by storing data in a separate .txt/.pdf file. User can choose to forward the file to others thereafter.

Format: `forward INDEX`
* Forwards the contact card of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `forward 2` creates a file to store the contact details of Charlie Decker.
* `find Annie` followed by `forward 1` creates a file to store Annie Baker’s contact details.

--------------------------------------------------------------------------------------------------------------------

## FAQ <a name="faq"></a>

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SociaLite home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary <a name="summary"></a>

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE [t/TAG]... [fb/FACEBOOK] [ig/INSTAGRAM] [tele/TELEGRAM] [tiktok/TIKTOK] [twitter/TWITTER]` 
**List** | `list`
**Edit** | `edit INDEX [n/NAME] [p/PHONE] [r/REMARK] [t/TAG]... [fb/FACEBOOK] [ig/INSTAGRAM] [tele/TELEGRAM] [tiktok/TIKTOK] [twitter/TWITTER]` 
**Find** | `find KEYWORD` 
**Delete** | `delete INDEX` 
**Add Remark** | `remark INDEX r/[REMARK]` 
**View help** | `help [COMMAND_WORD]` 
**Exit** | `exit`
**Help** | `help`
