---
layout: page
title: Developer Guide
---
* Table of Contents
1. [Acknowledgements](#acknowledgements)
2. [Setting Up, Getting Started](#setting-up-getting-started)
3. [Design](#design)
    1. [Architecture](#architecture)
    2. [UI Component](#ui)
    3. [Logic Component](#logic)
    4. [Model Component](#model)
    5. [Storage Component](#storage)
    6. [Common Classes](#common)
4. [Implementation](#implementation)
   1. [Help Command](#help_command)
   2. [Find Command](#find_command)
   3. [Picture Command](#picture_command)
5. [Documentation](#docs)
6. [Appendix: Requirements](#requirements)
    1. [Product Scope](#scope)
    2. [User Stories](#user-stories)
    3. [Use Cases](#use-cases)
    4. [Non-Functional Requirements](#nfr)
    5. [Glossary](#glossary)


--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements** <a name="acknowledgements"></a>

* Adapted from [_SE-Education_](https://se-education.org/addressbook-level3/DeveloperGuide.html)'s original [*AddressBook*](https://github.com/se-edu/addressbook-level3)
* GUI tests adapted from [*AddressBook* Level 4](https://github.com/se-edu/addressbook-level4)

--------------------------------------------------------------------------------------------------------------------

## **Setting Up, Getting Started** <a name="setting-up-getting-started"></a>

Refer to the guide [_Setting Up and Getting Started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design** <a name="design"></a>

<div markdown="span" class="alert alert-primary">
:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2122S1-CS2103T-F11-4/tp/tree/master/docs/diagrams) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture <a name="architecture"></a>

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/Main.java) and [`MainApp`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/MainApp.java). It is responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui): The UI of the App.
* [**`Logic`**](#logic): Parses and executes the appropriate command according to the user's input.
* [**`Model`**](#model): Holds the data of the App in memory.
* [**`Storage`**](#storage): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component <a name="ui"></a>

The `UI` component interacts with other components of SociaLite, and uses the JavaFX UI framework to render a GUI.

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/ui/Ui.java).

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts, including `CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter`, etc. All the parts, as well as the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.  The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/resources/view/MainWindow.fxml).

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

The `PersonCard` part also has a reference to the `MainWindow` to provide improved user feedback via the main window after user interactions which do not execute a command using `Logic`.

### Logic component <a name="logic"></a>

**API** : [`Logic.java`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `SocialiteParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `SocialiteParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `SocialiteParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component <a name="model"></a>
**API** : [`Model.java`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/model/Model.java)

<img src="images/ModelClassDiagram.png" width="750" />


The `Model` component,

* stores the contact list data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in SociaLite, which `Person` references. This allows SociaLite to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="550" />

</div>


### Storage component <a name="storage"></a>

**API** : [`Storage.java`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="950" />

The `Storage` component,
* can save contact list data, user preference data and command history data in json format, and read them back into corresponding objects.
* inherits from `ContactListStorage`, `UserPrefsStorage`, `CommandHistoryStorage` and `ProfilePictureStorage` which means it can be treated as any one (if the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes <a name="common"></a>

Classes used by multiple components are in the `socialite.commons` package. These include:

* [`core`](https://github.com/AY2122S1-CS2103T-F11-4/tp/tree/master/src/main/java/socialite/commons/core): General classes which deal with storage of SociaLite configuration and GUI settings, logging and commonly used messages. The [`Index`](https://github.com/AY2122S1-CS2103T-F11-4/tp/blob/master/src/main/java/socialite/commons/core/index/Index.java) class is also used widely to manage references to specific items in the contact list.
* [`exceptions`](https://github.com/AY2122S1-CS2103T-F11-4/tp/tree/master/src/main/java/socialite/commons/exceptions): Common exceptions used throughout SociaLite.
* [`util`](https://github.com/AY2122S1-CS2103T-F11-4/tp/tree/master/src/main/java/socialite/commons/util): Utility methods pertaining to collections, files, JSON, strings, etc.

--------------------------------------------------------------------------------------------------------------------

## **Implementation** <a name="implementation"></a>

This section describes some noteworthy details on how certain features are implemented.

### Help Command <a name="help_command"></a>

In order to simplify the error messages displayed via the GUI whenever a user enters an invalid command or non-conforming command format, 
the display of in-app guidance and help messages has been abstracted away from the specific command. As a result, the existing `HelpCommand` was 
revamped and expanded to display in-app guidance, consisting of the following details for a specified command:
* Overview of specified command's function
* Acceptable format of user input
* Example of using the specified command

Represented below is the sequence diagram when `help XYZ` is executed. For illustration purposes, `XYZ` refers to an acceptable command in SociaLite that has in-app guidance for users' reference.

![HelpSequenceDiagram](images/HelpSequenceDiagram.png)

For the correct `HelpCommand` to be created and appropriate in-app guidance to be displayed, a `HelpCommandParser` was created to parse additional keywords provided by the user. 
The acceptable keywords are: `add` `delete` `edit` `find` `picture` `pin` `unpin` `remark` and `share`.

Should an invalid keyword accompany `help`, the `HelpCommandParser` will create a generic `HelpCommand` that displays the HelpWindow dialog box. 
The HelpWindow dialog box provides users with the link to our User Guide and the list of acceptable keywords (as listed above) to view in-app guidance.


### Find Command <a name="find_command"></a>


### Picture Command <a name="picture_command"></a>

The Picture command allows users to add a profile picture to their contacts. The user can enter the command `picture INDEX` which would open a file browser to let the user choose any .jpg or .png file to be used as the profile picture, provided `INDEX` is a valid index

Represented below is the sequence diagram when `picture INDEX` is executed. For illustration purposes, `INDEX` refers to any valid index starting from 1 to the number of contacts in the displayed list.

![PictureSequenceDiagram](images/PictureSequenceDiagram.png)

Note that the picture command depends on the MainWindow in the UI package as it retrieves the file from a file chooser.



--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops** <a name="docs"></a>

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements** <a name="requirements"></a>

### Product scope <a name="scope"></a>

**Target user profile**:

* avid social media user
* prefers desktop apps over other types
* prefers typing to mouse interactions
* needs to manage a significant number of contacts
* wants to catch up with their contacts’ activities quickly 
* wants to connect with their followers/friends on various social media platforms through an all-in-one dashboard

**Value proposition**: 

Our product serves as an integrated dashboard for a user to retrieve the social media activities and account information of his/her contacts. This makes it seamless for the user to interact with his/her contacts instead of having to access each social media account that the contact owns.

### User stories <a name="user-stories"></a>

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

<br/>

_Core Functionalities_

|Priority| As a / an …​                              | I want to …​                                                                    | So that I can…​                                                                                      |
|--------| -------------------------------------------- | ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
|`* * *` | beginner user                                | add contacts                                                                       | access my contacts' details                                                                             |
|`* * *` | beginner user                                | delete contacts                                                                    | remove irrelevant entries                                                                               |
|`* * *` | beginner user                                | access the social media handles of my contacts                                     | have quicker access to my contacts' social media pages                                                  |
|`* * *` | forgetful user                               | save my contacts' social media handles                                             | easily access my contact's social media account without having to recall the exact handle               |
|`* * *` | user                                         | visit the social media site when I click on the handle                             | easily go to the social media site, without having to switch to the browser while remembering the handle|
|`* *`   | user                                         | browse a list of all my contacts                                                   | view all my contacts at a glance                                                                        |
|`*`     | beginner user                                | update contacts                                                                    | modify existing social media handles and add new ones when they are created                             |

<br/>

_Guide for New Users_

|Priority| As a / an …​                              | I want to …​                                                                    | So that I can…​                                                                                      |
|--------| -------------------------------------------- | ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
|`* *`   | new user                                     | view the User Guide                                                                | learn how to use SociaLite comprehensively                                                              |
|`* *`   | new user                                     | view sample contacts when I initialise the app                                     | try out the features without having to add actual data                                                  |
|`* *`   | new / returning user                         | access in-app guidance for a specific command                                      | (re)learn the syntax of a selected command without having to open the User Guide via a browser          |
|`* *`   | new user adopting the app for my own use     | purge all data                                                                     | delete sample contacts and add real data                                                                |

<br/>

_Organization of Contacts_

| Priority | As a / an …​       | I want to …​                                        | So that I can…​                                               |
| -------- | ----------------- | -------------------------------------------------- | ------------------------------------------------------------ |
| `* *`    | organized user    | create tags to group my contacts                   | organize my list of contacts                                 |
| `* *`    | organized user    | edit tags as and when required                     | repurpose such pre-existing tags                       |
| `* *`    | organized user    | query a group of contacts                          | have greater ease of access to my frequent contacts and efficiently contact people for similar purposes |
| `*`      | organized user    | delete tags associated with contacts               | de-clutter my contact list when the category is no longer relevant |
| `*`      | intermediate user | filter contacts based on social media platform     | find out whose social media contacts I have not gotten and request it from them |
| `*`      | user              | navigate through my command history                | make slight adjustments to my last command if I happen to have a typo |

<br/>

_Ease of Accessibility_

|Priority| As a / an …​                              | I want to …​                                                                    | So that I can…​                                                                                      |
|--------| -------------------------------------------- | ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
|`* * *` | user with many contacts                      | find a contact by name, tag or handle                                              | easily find a contact without having to scroll through my entire contact list
|`* *`   | avid social media user with many connections | be redirected to my chosen contact's social media platform                         | avoid initialising every social media platform and search for his/her account manually                  |
|`*`     | expert user                                  | customise the information presented to me when the app is initialised              | view the social media contacts of my close friends quickly without keying in additional prompts         |
|`*`     | user                                         | view a dashboard of significant events associated with my contact                  | be reminded of these dates                                                                              |
|`*`     | user                                         | forward relevant details of an existing contact                                    | easily share such information upon request                                                              |

<br/>

_Customization of Contacts_

|Priority| As a / an …​                              | I want to …​                                                                    | So that I can…​                                                                                      |
|--------| -------------------------------------------- | ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
|`*`     | user                                         | add a profile picture for my contacts                                              | better recognize their appearance                                                                       |
|`*`     | user                                         | add notes about contacts                                                           | recall specific items about my contacts                                                                 |
|`*`     | user                                         | add dates of special occasions (birthdays/appointments) associated with my contact | view important information of my contacts                                                               |
|`*`     | user                                         | set reminders for special occasions associated with my contact                     | be alerted of these events                                                                              |


<br/>


### Use Cases <a name="use-cases"></a>

(For all use cases below, the **System** is `SociaLite` and the **Actor** is the `user`, unless specified otherwise)


**Use Case 01: Add a person**

*MSS*

1.  User requests to add a new contact
2.  SociaLite adds the contact

    Use case ends.

*Extensions*

* 1a. User's input does not conform with the specified format.
    
    * 1a1. SociaLite shows an error message.
      
        Use case resumes at step 1.

<br/>

**Use Case 02: List contacts in SociaLite**

*MSS*

1.  User requests to list contacts
2.  SociaLite shows a list of all contacts

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

<br/>

**Use Case 03: Find a contact by name, tags or social media platform**

*MSS*

1.  User requests to locate a specific contact by name, tags or social media platform.
2.  SociaLite shows a list of contacts that matches the given criteria

    Use case ends.

*Extensions*

* 2a. The list is empty as there are no matches found.

  Use case ends.

<br/>

**Use Case 04: Access contact's social media page**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User requests to open the chosen contact's social media page

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 2b. The contact does not have social media handles.

  Use case ends.

<br/>

**Use Case 05: Edit entries in SociaLite**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User requests to edit the information (name, phone number, remark, tag, date, platform) of a specific contact in the list
4.  SociaLite updates the contact with the specified input

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

* 3b. User's input does not conform with the specified format.

    * 3b1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 06: Delete a person**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User requests to delete a specific contact in the list
4.  SociaLite deletes the contact

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 07: Purge contacts stored in SociaLite**

*MSS*

1.  User requests to clear all existing contacts 
2.  SociaLite clears contact list data from storage

    Use case ends.

<br>

**Use Case 08: Add tags for contacts**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User requests to add tags to a contact
4.  SociaLite updates the contact with the specified input

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

* 3b. User's input does not conform with the specified format.

    * 3b1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 09: Delete all tags from a contact**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User requests to delete all tags associated with a specific contact in the list
4.  SociaLite deletes all tags

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

* 3b. User's input does not conform with the specified format.

    * 3b1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 10: Add date(s) to contact**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User specifies the index of his desired contact, and the dates to be associated with the contact
4.  SociaLite adds the specified dates to the user's contact entry

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

* 3b. The given date(s) do not match the valid date format.

    * 3b1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 11: Modify profile picture for a specific contact**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User specifies the contact and picture to be used
4.  SociaLite adds the chosen picture to the contact's entry

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 12: Copy contact information of a contact**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User specifies the contact whose information is to be copied
4.  SociaLite copies the information to the clipboard

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 13: Pin contact card on the top of person list**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User specifies contact to be pinned at the top of the list
4.  SociaLite pins the contact at the top of the list

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

* 3b. The specified contact is already pinned. 

  Use case ends.

<br/>

**Use Case 14: Unpin contact card from the top of person list**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User specifies contact to be unpinned from the top of the list
4.  SociaLite unpins the contact from the top of the list

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

* 3b. The specified contact is not pinned. 

  Use case ends.

<br/>

**Use Case 15: Add remark for a specific contact**

*MSS*

1.  User requests to **list contacts (UC02)** or **find contact (UC03)**
2.  SociaLite returns a list of contacts according to the UC called
3.  User specifies a remark to be added to the specified contact's entry
4.  SociaLite adds a remark to the specified contact's entry

    Use case ends.

*Extensions*

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. SociaLite shows an error message.

      Use case resumes at step 2.

<br/>

**Use Case 16: View User Guide**

*MSS*

1.  User requests to view User Guide
2.  SociaLite displays a link to User Guide and instructions to obtain in-app guidance for valid commands

    Use case ends.

<br/>

**Use Case 17: View in-app guidance for selected commands**

*MSS*

1.  User requests to view in-app guidance for valid commands
2.  SociaLite returns an overview and quick guide of the command given as input

    Use case ends.

*Extensions*

* 1a. The keyword given as input is invalid.

    * 1a1. SociaLite launches HelpWindow for **User Guide (UC16)** by default

      Use case ends.

<br/>

**Use Case 18: Exit application**

*MSS*

1.  User types in command to exit application
2.  SociaLite closes

    Use case ends.


*{More to be added}*

<br/>



### Non-Functional Requirements <a name="nfr"></a>

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  Should be able to keep track of at least 1000 past commands without noticeable lag when tracking past commands.
4.  Should be intuitive enough for users of all technical backgrounds to operate.
5.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary <a name="glossary"></a>

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Social Media**:  Various communication platforms such as Instagram, Facebook, Twitter, TikTok, Telegram

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
