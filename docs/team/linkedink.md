---
layout: page
title: Hendey Fan's Project Portfolio Page
---

## Project: Athena

Athena is a **desktop app for managing contacts and events, optimized for use via a Command Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI).

Given below are my contributions to the project.

* **New Feature**: Added the ability to add events with `add -e` command (Pull request [#67](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/67)).
  * What it does: Allows users to create events with a description, time and, optionally, attendees from the contact list.
  * Justification: This was created as part of our intention for Athena to manage events in addition to contacts.
  * Credits: This feature is based on ideas from add contact command and edit contact command.
   
* **New Feature**: Added the ability to delete events with `delete -e` command (Pull request [#91](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/91)).
  * What it does: Allows users to delete already existing events.
  * Justification: Prevents the event list from being full of events that are no longer necessary.
  * Credits: This feature is based on delete contact command.
  
* **New Feature**: Added the ability to edit events with `edit -e` command (Pull request [#94](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/94)). 
  * What it does: Allows for users to edit any of the details of already existing events.
  * Justification: This allows for users to change only specific details of events that they want.
  Without this, users will have to delete an old event and create a complete new event again if there is a change. Hence, saving time in the process.
  * Credits: This feature is based on ideas from edit contact command.
  
* **New Feature**: Added ability for events to be saved into a .json file and for Athena to read from it (Pull request [#152](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/152)).
  * What it does: Events created are able to be stored when the app closes and be loaded again when the app reopens.
  * Justification: Allows for events to be kept from session to session.
  * Credits: This feature is based on the existing structure for saving the contact list.
  
* **New Feature**: Added ability for users to add and remove contacts as attendees to an event (Pull request [#172](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/172)).
  * What it does: Contacts can now be added to an event as attendees.
  * Justification: Allows for users to track who in their contact list is an attendee of an event.
  * Highlight: Integrating contacts with events happened after the initial implementation of events.
    This meant that the existing implementation, which was not originally designed with this in mind, had to be updated.
    The more significant parts were the event class, add event command and edit event command.
    Saving and reading events into/from a .json file also had significant changes to accommodate the new data.
    The code written was also designed for future expansion as we wanted Athena to automatically remove contacts from events they attend.

* **New Feature**: Added ability to view event details with `view -e` command.
  * What it does: Display the full event details in the result panel of the left of the GUI.
  * Justification: GUI only displays up to 6 attendees (to limit the space one event can take up).
  If there is more than 6 attendees, this command can be used to view the hidden attendees.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=linkedink)

* **Enhancements to existing features**:
  * Updated the GUI to display events (Pull requests [#67](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/67), [#172](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/172), [#231](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/231))
  * Updated delete contact command (delete -c) 
    * To be able to delete multiple contacts with a list of indices (Pull request [#227](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/227))
    * To delete the contact from any event the contact is attending
  * Updated clear contact command (clear -c) 
    * To clear all contacts from events they attend
  
* **Enhancements to testing**:
  * Added ModelManagerBuilder class for the tests to use
  * Added sample events under SampleDataUtil class
  * Wrote tests to increase code coverage by 4.32% (Pull request [#277](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/277))
    * Added tests for new features
    * Updated some existing tests to test with the new features
    
* **Documentation**:
  * User Guide:
    * Added the documentation for commands `add -e`, `delete -e`, `edit -e` and `view -e`
    * Updated `clear -e` command with a warning and clear picture showing the effects of the command
    * Updated `delete -c` and `clear -c` to tell users that contacts will be removed from events they attend
    * Updated UG with a new heading format and fixed some other minor issues (Pull request [#261](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/261))
  * Developer Guide:
    * Added use cases for add, delete, edit and view event(s).
    * Added manual tests for edit, find and delete event(s).
    * Added the section: Event and attendees management.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [#135](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/135), [#229](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/229)
  * Reported bugs and suggestions for other teams in the class ([Link](https://github.com/LinkedInk/ped/issues))
  * Helped a teammate with some issues he faced for his reminder feature with some suggested changes ([Link](https://github.com/bangyiwu/tp/pull/1))
