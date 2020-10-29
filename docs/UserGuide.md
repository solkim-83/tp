---
layout: page
title: User Guide
---

This document is a *User Guide* for Athena, a contact- and event- management desktop application. For more details on
the suitability and functionalities of the application for your usage, please refer to the [Introduction](#Introduction)
section below.

#### Table of Contents

* [Introduction](#Introduction)
* [Quick Start](#Quick-start)
* [Features](#Features)
    * [General](#General)
        * [`help`](#viewing-help) - viewing help
        * [`exit`](#exiting-the-program--exit) - exiting the program
    * [Contact](#Contact)
        * [`add`](#adding-a-contact-add) - adding a contact
        * [`clear`](#clearing-all-contacts--clear) - clearing all contacts
        * [`delete`](#deleting-a-contact--delete) - deleting a contact
        * [`edit`](#editing-a-contact--edit) - editing a contact
        * [`find`](#finding-a-contact--find) - finding a contact
        * [`list`](#listing-all-contacts--list) - listing a contact
        * [`sort`](#sorting-displayed-contacts--sort) - sorting displayed contacts
    * [Event](#event)
        * [`add`](#adding-an-event-add) - adding an event
        * [`clear`](#clearing-all-events--clear) - clearing all events
        * [`delete`](#deleting-an-event--delete) - deleting an event
        * [`edit`](#editing-an-event--edit) - editing an event
        * [`find`](#finding-an-event--find) - finding an event
        * [`list`](#listing-all-events--list) - listing an event
* [FAQ](#FAQ)
* [Command Summary](#Command-summary)

---

## Introduction

Athena is a **desktop app for managing contacts and events, optimized for use via a Command Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI).

Athena is perfect for you if you:
* need to categorise a huge number of contacts or events,
* need to keep track of the people who attend specific events,
* can type fast!

---

## Quick start

1. Ensure you have Java `11` or above installed in your computer.

1. Download the latest `Athena.jar` from [here](https://github.com/AY2021S1-CS2103T-W10-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for Athena.

1. Double-click the file or run it with `java -jar Athena.jar` to start the app. A window similar to the one below
   should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   
   Some commands you can try:

   * **`list`**`-c` : Lists all contacts.

   * **`add`**`-c n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to Athena.

   * **`delete`**`-e 3` : Deletes the 3rd event shown in the current list.

   * **`add`**`-e d/Meeting at/12-12-1234 12:34` : Adds an event named `Meeting` to Athena.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

Athena supports two key functionalities - the management of contacts and events. Thus, commands are tailored to manage 
each of these specific functionalities. In addition, to better categorise contacts, they may also be *tagged* with
specific key-words. Thus, there are **four** types of commands:

* [general](#general) - commands that are not targeted at any specific functionality
* [contact](#contact) - commands that are targeted at contacts, distinguished by `-c`
* [event](#event) - commands that are targeted at events, distinguished by `-e`
* [tag](#tag) - commands that are targeted at tags, distinguished by `-t`

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

</div>

---

### General

#### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

---

### Contact

#### Adding a contact: `add`

Adds a contact to Athena.

Format: `add -c n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
If you are missing some of the information, like ADDRESS, you can just end the field with a "." (e.g. "a/.")
</div>

Examples:
* `add -c n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add -c n/Betsy Crowe t/friend e/betsycrowe@example.com a/. p/1234567 t/criminal`

#### Clearing all contacts : `clear`

Clears all contacts from Athena.

Format: `clear -c`

#### Deleting a contact : `delete`

Deletes the specified contact from Athena.

Format: `delete -c INDEX`

* Deletes the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list -c` followed by `delete -c 2` deletes the 2nd contact in Athena.
* `find -c n/Betsy` followed by `delete -c 1` deletes the 1st contact in the results of the `find` command.

#### Editing a contact : `edit`

Edits an existing person in the address book.

Format: `edit -c INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]… [rt/TAG]…`

* Edits the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Tags that are not mentioned will *NOT* be affected.
* `t/TAG` adds `TAG` to the user.
* `rt/TAG` removes `TAG` from the user.
* Tag removal is done before new tags are added.
* You can remove all the person’s tags by typing `rt/*`.

Examples:
* `edit -c 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st contact to be `91234567` 
and `johndoe@example.com` respectively.
* `edit -c 2 n/Betsy Crower t/CS2030` Edits the name of the 2nd contact to be `Betsy Crower` and adds the tag `CS2030`.
* `edit -c 3 t/CS2103 rt/*` Removes all tags that contact at index `3` has and then adds the tag `CS2103` to it.

#### Finding a contact : `find`

Finds persons whose names contain any of the given keywords. Also supports search with additional specifiers such as 
phone number or email.

Format: `find -c [n/KEYWORDS] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

* Search field must contain at least one of the optional fields.
* The search is case-insensitive for all fields except tags. e.g `hans` will match `Hans`
* For name keywords, only full words will be matched. e.g. `Han` will not match `Hans`
* For name keywords, you can specify multiple words you would like to match. e.g. `n/Hans John`
* For search without additional specifiers, persons matching at least one keyword will be returned (i.e. `OR` search).
* The order of the name keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* The `t/TAG` specifier must use an existing tag and does not support partial tag-name searches.
* If additional specifiers are included, only contacts whose specified field contains the specifier details
 will be returned. `find n/John a/Serangoon` will return only contacts whose names contain `John` **and** with 
 `Serangoon` as part of the address.

Examples:
* `find -c n/John` returns `john` and `John Doe`
* `find -c n/alex david` returns `Alex Yeoh`, `David Li`
* `find -c a/Serangoon` returns all contacts with an address that contains `Serangoon` 
* `find -c n/alex david e/gmail` returns `Alex Tan e/...@gmail.com` and `David Lim e/...@gmail.com` but not 
`Alex Yeoh e/...@hotmail.com` 

#### Listing all contacts : `list`

Shows a list of all contacts in Athena.

Format: `list -c`

#### Sorting displayed contacts : `sort`

Sort all currently displayed contacts in Athena.

Format: `sort -c INDEX`

* Sorts through all contacts currently on screen and lists them according to the user-requested order
* Index definitions:
1. By lexicographical order of their names
1. By lexicographical order of their address
1. By lexicographical order of their email

Examples:
* `sort -c 2` would sort all currently displayed contacts by their names in alphabetical order

---

### Event

#### Adding an event: `add`

Adds an event to the event list.

Format: `add -e d/DESCRIPTION at/DATE_TIME`

DATE_TIME formats currently accepted
* dd-MM-yyyy HH:mm

Legend:

Short form | What it represents
----------- | -----------
dd | Digits for date
MM | Digits for month
yyyy | Digits for year
HH | Digits for hour of the day in 24hr time
mm | Digits for minutes of an hour

Examples:
* `add -e d/CS2103 Team meeting at/12-12-1234 12:34`
* `add -e at/12-12-12 12:34 d/CS2103 Team meeting`

#### Clearing all events : `clear`

Clears all events from Athena's calendar.

Format: `clear -e`

#### Deleting an event : `delete`

Deletes the specified event from the event list.

Format: `delete -e INDEX`

* Deletes the event at the specified `INDEX`.
* The index refers to the index number shown in the displayed event list.
* The index must be a positive integer 1, 2, 3, ...

Examples:
* `list -e` followed by `delete -e 2` deletes the 2nd event in the event list

#### Editing an event : `edit`

Edits an existing event in the event list. DATE_TIME format follows the addEvent command.

Format: `edit -e INDEX [d/DESCRIPTION] [at/DATE_TIME]
[p/ATTENDEE_NAME]… [rp/ATTENDEE_NAME]…`

* Edits the event at the specified INDEX. The index must be a positive integer 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* `p/ATTENDEE_NAME` adds a contact with `ATTENDEE_NAME` to the event.
* `rp/ATTENDEE_NAME` removes a contact with `ATTENDEE_NAME` from the event.
* ATTENDEE_NAME must be exactly the same as the name stored in contacts. Case-sensitive.
* You can remove all the attendees for the event by using `rp/*`.

Examples:
* `edit -e 1 d/CS2101 Tutorial at/23-10-1234 12:30` Edits the details and datetime of the 1st event to be CS2101 Tutorial and 23-10-1234 12:30 respectively.
* `edit -e 2 at/23-10-1234 12:30` Edits the time of the 2nd event to be 23-10-1234 12:30  E
* `edit -e 1 p/Amanda p/Ethan rp/John rp/Jesse`  Adds the contacts with the names: Amanda, Ethan to the event attendees. Removes the contacts with the names: John, Jesse from the event attendees.

#### Finding an event : `find`

Finds events which names contain any of the given keywords.

Format: `find -e KEYWORD`

* The search is case-insensitive. e.g `meeting` will match `Meeting`
* If the event contains the particular keyword in the command, the name of the event will appear.
* Only full words will be matched e.g. `meetin` will not match `meeting`

Examples:
* `find -e Meeting` returns `CS2103 Meeting` and `CS2101 meeting`
* `find -e seminar` returns `CS Seminar` and `seminar 1`

#### Listing all events : `list`

Shows a list of all events saved in Athena's calendar.

Format: `list -e`

---

### Tag

Tags present a new way for you to classify and group your contacts together. Managing your tags properly will 
allow you to perform tag-level actions such as adding all contacts under a tag into an event. 

#### Adding a tag: `add`

Adds a new tag to Athena. Use this when you want to retroactively assign contacts to a tag and/or classify a group 
of tags under one super-tag.

Format: `add -t n/TAG_NAME [i/CONTACT_INDEX]… [t/SUB_TAG]…`

* `TAG_NAME` must be alphanumeric with no spaces.
* `CONTACT_INDEX` refers to the index of a contact as is shown in the contact window.
* At least one of `CONTACT_INDEX` or `SUB_TAG` must be provided.
* `TAG_NAME` must not be a tag that already exists.
* `SUB_TAG`s specified must already exist.

Examples:
* `add -t n/computing i/1 i/2 t/cs2030 t/cs2040` Creates a new tag `computing`. Contacts at indices `1` and `2` 
will be assigned the `computing` tag. `cs2030` and `cs2040` are assigned as sub-tags to `computing`.
* `add -t n/cs2103 i/1` Creates a new tag `cs2103` and assign contact at index `1` the tag `cs2103`.

#### Editing a tag: `edit`

Edits an existing tag in Athena. Use this when you would like to add and/or remove sub-tags from a tag.

Format: `edit -t n/TAG_NAME [t/TAG_ADD]… [rt/TAG_REMOVE]…` 

* `TAG_NAME` specified must be of an existing tag.
* `TAG_ADD`s labelled under `t/` are tags to be added as sub-tags to `TAG_NAME`.
* `TAG_ADD`s must be existing tags not already sub-tags of `TAG_NAME`.
* `TAG_REMOVE`s labelled under `rt/` are tags to be removed as sub-tags from `TAG_NAME`.
* `TAG_REMOVE`s must be existing sub-tags of `TAG_NAME`.

Examples:
* `edit -t n/computing t/cs2030 rt/cs2040` Adds `cs2030` as a sub-tag to `computing` and removes `cs2040` as a sub-tag.

#### Viewing a tag: `view`

View specific details of a tag. Use this when you would like to view full details of a tag.
Details include:
- Direct child tags
- Contacts tagged with a specific tag
- All related sub-tags
- All related contacts (contacts containing sub-tags)

Format: `view -t t/TAG [t/TAG]…`

* `TAG` must be a valid existing tag in Athena.

Example:
* `view -t t/cs2030` Shows all of the above information for the tag `cs2030` only.
* `view -t t/cs2030 t/cs2040` Shows all of the above information for the tags `cs2030` and `cs2040` in a sequential order.

#### Listing a tag: `list`

Lists all tags in the remarks panel. It lists each tag and contacts tagged with the tag.

Example:
* `list -t` 

---

### Reminders

Reminders allows you to set custom reminders for your events. You can call up your reminders in application or
have them pop up whenever Athena starts. 

#### Adding a reminder: `add`

Adds a new reminder for an event.

Format: `add -r [EVENT_INDEX]… [in/DAYS]…`

* `EVENT_INDEX` refers to the index of an event as is shown in the event window.
* `DAYS` refers to the number of days in advance for the reminder to start showing in the pop-up window.

Examples:
* `add -r 3 in/4` Creates a new reminder for the 3rd event, the reminder will start to pop up every time
Athena opens 4 days prior to that event 

#### Listing all reminders: `list`

Lists all reminders in the remarks panel. It lists each reminder and a countdown to their events.

Example:
* `list -r` 

---

### Saving the data

Athena's data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Athena home folder.
The save files that Athena uses are `addressbook.json`, `tagtree.json` and `calendar.json`. The default directory of these files are at 
`{Athena home directory}/data`.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add Contact** | `add -c n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Add Event** | `add -e d/DESCRIPTION at/DATE_TIME`<br> e.g., `addEvent d/CS2103 Team meeting at/12-12-1234 12:34`
**Clear Contacts** | `clear -c`
**Clear Events** | `clear -e`
**Delete Contact** | `delete -c INDEX`<br> e.g., `delete 3`
**Delete Event** | `delete -e INDEX`<br> e.g., `deleteEvent 2`
**Edit Contact** | `edit -c INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Edit Event** | `edit -e INDEX [d/DESCRIPTION] [at/DATE_TIME] [p/ATTENDEE_NAME]… [rp/ATTENDEE_NAME]…`<br> e.g., `editEvent 2 at/23-10-1234 12:30 p/Amanda`
**Exit** | `exit`
**Find Contact** | `find -c KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Find Event** | `find -e KEYWORD`<br> e.g., `findEvent Seminar`
**Help** | `help`
**List Contact** | `list -c`
**List Events** | `list -e`
**Sort Contacts** | `sort -c 1`
**Add Reminder** | `add -r [EVENT_INDEX] [in/DAYS]`
**List Reminders** | `list -r`
