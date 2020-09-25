---
layout: page
title: User Guide
---

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

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

## Features

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

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
If you are missing some of the information, like ADDRESS, you can just end the field with a "." (e.g. "a/.")
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/. p/1234567 t/criminal`

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Sorting your contacts : `sort`

View your contacts in a sorted manner

Format: `sort INDEX`

* Sorts through all contacts currently on screen and lists them according to the user-requested order
* INDEX DEFINITIONS:  
  1: By time added (Default)
  
  2: By alphabetical order of their names
  
  3: By alphabetical order of their address
  
  4: By alphabetical order of their first tag

Examples:
* `sort 2` would sort all currently displayed contacts by their names in alphabetical order


### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a contact : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]… [rt/TAG]…`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Tags that are not mentioned will *NOT* be affected.
* `t/TAG` adds `TAG` to the user.
* `rt/TAG` removes `TAG` from the user .
* Tag removal is done before new tags are added.
* You can remove all the person’s tags by typing `rt/*`.

Examples:
* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` 
and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/CS2030` Edits the name of the 2nd person to be `Betsy Crower` and adds the tag `CS2030`.
* `edit 3 t/CS2103 rt/*` Removes all tags that contact at index `3` has and then adds the tag `CS2103` to it.

### Finding a contact : `find`

Finds persons whose names contain any of the given keywords. Also supports search with additional specifiers such as 
phone number or email.

Format: `find [n/KEYWORDS]… [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

* Search field must contain at least one of the optional fields.
* The search is case-insensitive. e.g `hans` will match `Hans`
* For name keywords, only full words will be matched. e.g. `Han` will not match `Hans`
* The `t/TAG` specifier must use an existing tag and does not support partial tag-name searches.
* The order of the name keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* For search without additional specifiers, persons matching at least one keyword will be returned (i.e. `OR` search).
e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* If additional specifiers are included, only contacts whose specified field contains the specifier details
 will be returned. `find n/John a/Serangoon` will return only contacts whose names contain `John` **and** with 
 `Serangoon` as part of the address.

Examples:
* `find n/John` returns `john` and `John Doe`
* `find n/alex david` returns `Alex Yeoh`, `David Li`
* `find a/Serangoon` returns all contacts with an address that contains `Serangoon` 
* `find n/alex david e/gmail` returns `Alex Tan e/...@gmail.com` and `David Lim e/...@gmail.com` but not 
`Alex Yeoh e/...@hotmail.com` 


### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Adding an event : `addEvent`

Adds an event to the event list.

Format: `addEvent d/DESCRIPTION at/DATE_TIME`

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
* addEvent d/CS2103 Team meeting at/12-12-1234 12:34
* addEvent at/12-12-12 12:34 d/CS2103 Team meeting


### Delete an event : `deleteEvent`

Deletes the specified event from the event list.

Format: `deleteEvent INDEX`

* `viewEvents` (command explained below) should be used first to display a list of events to be referenced with an INDEX value.
* Deletes the event at the specified `INDEX`.
* The index refers to the index number shown in the displayed event list.
* The index must be a positive integer 1, 2, 3, ...

Examples:
* `viewEvents` followed by `deleteEvent 2` deletes the 2nd event in the event list

### Editing an event : `editEvent`

Edits an existing event in the event list. DATE_TIME format follows the addEvent command.

Format: `editEvent INDEX [d/DESCRIPTION] [at/DATE_TIME]
[p/ATTENDEE_NAME]… [rp/ATTENDEE_NAME]…`

* `viewEvents` (command explained below) should be used first to display a list of events to be referenced with an INDEX value.
* Edits the event at the specified INDEX. The index must be a positive integer 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* `p/ATTENDEE_NAME` adds a contact with `ATTENDEE_NAME` to the event.
* `rp/ATTENDEE_NAME` removes a contact with `ATTENDEE_NAME` from the event.
* ATTENDEE_NAME must be exactly the same as the name stored in contacts. Case-sensitive.
* You can remove all the attendees for the event by using rp/*.

Examples:
* `edit 1 d/CS2101 Tutorial at/23-10-1234 12:30` Edits the details and datetime of the 1st event to be CS2101 Tutorial and 23-10-1234 12:30 respectively.
* `edit 2 at/23-10-1234 12:30` Edits the time of the 2nd event to be 23-10-1234 12:30  E
* `edit 1 p/Amanda p/Ethan rp/John rp/Jesse`  Adds the contacts with the names: Amanda, Ethan to the event attendees. Removes the contacts with the names: John, Jesse from the event attendees.

### Search for events : `searchEvents`

* The search is case-insensitive. e.g hans will match Hans
* If the event contains the particular keyword, the name of the event will appear.
* Only full words will be matched e.g. Han will not match Hans

Examples:
* `searchEvents Meeting` returns `CS2103 Meeting` and `CS2101 meeting`
* `searchEvents seminar` returns `CS Seminar` and  `seminar 1`


Format: `searchEvents KEYWORD`

### Viewing all saved events : `viewEvents`

Shows a list of all events saved in the calendar.

Format: `viewEvents`


### Archiving data files `[coming in v2.0]`

_{explain the feature here}_

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
**Add Event** | `addEvent d/DESCRIPTION at/DATE_TIME`<br> e.g., `addEvent d/CS2103 Team meeting at/12-12-1234 12:34`
**Delete Event** | `deleteEvent INDEX`<br> e.g., `deleteEvent 2`
**Edit Event** | `editEvent INDEX [d/DESCRIPTION] [at/DATE_TIME] [p/ATTENDEE_NAME]… [rp/ATTENDEE_NAME]…`<br> e.g., `edit 2 at/23-10-1234 12:30 p/Amanda`
**search Events** | `searchEvents KEYWORD`<br> e.g., `find Seminar`
**view Events** | `viewEvents`
