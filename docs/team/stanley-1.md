---
layout: page
title: Stanley Tan's Project Portfolio Page
---

### Project: SociaLite

**SociaLite** is a desktop app for **connecting avid social media users with their contacts’ social media pages**, optimized for use via a command line interface (CLI).
At the same time, it has a graphical user interface (GUI) created with JavaFX. 
It is written in Java, and has about 10 kLoC.


A summary of my contributions to the project is detailed below.



* **Altered Feature**: Redefined criteria for duplicate persons to be detected.
  * What it does:
    * SociaLite now detects duplicate entries by comparing the contacts' phone number instead of name.
    * Users can add contacts who coincidentally have the same names, without a DuplicatePersonException being thrown.
  * Justification:
    * The original AB-3 code detects duplicate entries within the address book by comparing the names (in string) of contacts that already exist in the database.
    * However, there can be instances where users meet acquaintances who coincidentally share the same name but are separate persons altogether.
    * Hence, the phone number (which is a compulsory field in SociaLite) is now used as a determinant for duplicate persons as no two persons have the same phone number.
  * Highlights:
    * This alteration addresses the inconvenience that users might have in adding contacts with similar names.
    * This allows users to add more contacts to their database.

<br> 

* **Revamped Feature**: Enhanced ability for users to obtain help or in-app guidance.
  * What it does: 
    * Allows users to access in-app guidance by typing in `help <COMMAND>` where `<COMMAND>` refers to a command of the user's choice.
  * Justification:
    * This command allows users to quickly refer to in-app guidance and perform their desired function correctly, without having to open the User Guide.
  * Highlights:
    * Serves as an umbrella command which abstracts away the appearance of Help Messages for all other valid commands.

<br>

* **New Feature**: Added support for Facebook and Instagram social media handles
  * What it does: 
    * Allows users to add Facebook and Instagram social media handles for a contact.
  * Justification:
    * Users can conveniently add and access a contact's Facebook and Instagram pages as SociaLite supports these two commonly used platforms.
  * Highlights:
    * Adhered to teammate's (Xiaozhi's) pre-designed framework for storing social media handles when enhancing support for Facebook and Instagram handles.
    * Scalable feature which can support other social media platforms in the future.

<br>

* **New Feature**: Added the ability to undo/redo previous commands.
  * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
  * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.
  * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*

* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.

<br>

* **Code contributed**: [RepoSense link]()

<br>

* **Project management**:
  * Designed interim UI mock-up for `v1.2` which has since been adapted in subsequent iterations.
  * Managed release `v1.2` on GitHub.
  * Acted as minute-taker to take note and summarize points raised in our team meetings held twice a week.

<br>

* **Documentation**:
  * User Guide:
    * Supported team members in the initial revamp of User Guide to match SociaLite
      * Drafted documentation for the features on our initial Google Doc
    * Major changes for `v1.3`: [\#70](https://github.com/AY2122S1-CS2103T-F11-4/tp/pull/70)
      * Added documentation for the feature `help COMMAND`
      * Updated all sections and features to match our new input format for SociaLite
      * Added around 10 missing commands and examples to Command Summary.
    * Performed major overhaul of UG for enhanced readability following PE-D review: [\#138](https://github.com/AY2122S1-CS2103T-F11-4/tp/pull/138)
      * Added Navigation Menu and consolidated Table of Commands (both with hyperlinks for users to access their desired section/feature efficiently)
      * Created different pathways to cater to different user demographics.
      * Revamped the layout and organization of all features - related commands are now found in the same section of the UG
      * Re-designed how format and examples are presented to readers for all features through standardized notations.
      * Updated FAQ section.
  * Developer Guide:
    * Aided in conceptualization of Product Scope, including the definition of our target user profile and value proposition.
    * Added and organized majority of User Stories and Use Cases.
    * Reviewed and suggested improvements for team members' additions to Developer Guide.

<br>

* **Community**:
  * Reviewed 29 PRs in total. Selection of PRs with non-trivial review comments: [\#77](https://github.com/AY2122S1-CS2103T-F11-4/tp/pull/77), [\#129](https://github.com/AY2122S1-CS2103T-F11-4/tp/pull/129)
  * Reported 14 bugs and suggestions for other teams in the class 
    * [Unable to change password](https://github.com/AY2122S1-CS2103T-W13-2/tp/issues/186)
    * [HelpWindow does not show User Guide on subsequent attempts](https://github.com/AY2122S1-CS2103T-W13-2/tp/issues/138), 
    * [Lack of guidance on adding multiple tags](https://github.com/AY2122S1-CS2103T-W13-2/tp/issues/159)
    
