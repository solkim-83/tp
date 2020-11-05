---
layout: page
title: Hendey Fan's Project Portfolio Page
---

## Project: Athena

Athena is a **desktop app for managing contacts and events, optimized for use via a Command Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI).

Given below are my contributions to the project.

* **New Feature**: Added the ability to add events with `add -e` command.
  * What it does: Allows users to create events with a description, time and, optionally, attendees from the contact list.
  * Justification: This was created as part of our intention for Athena to manage events in addition to contacts.
  * Highlight: Contacts integration happened after the initial creation of the basic event commands (add, delete and edit).
  This change affected not just the overall event structure and this command, but the other commands as well.
  They had to be changed (mentioned below under: Enhancements to existing features), and in the case of edit event command, quite significantly as there are a lot more details which can be edited.
   
* **New Feature**: Added `delete -e` command.
  * What it does: Allows users to delete already existing events.
  * Justification: Prevents the event list from being full of events that are no longer necessary.
  
* **New Feature**: Added `edit -e` command.
  * What it does: Allows for users to edit any of the details of already existing events.
  * Justification: This allows for users to change only specific details of events that they want.
  Without this, users will have to delete an old event and create a complete new event again if there is a change. Hence, saving time in the process.
  
* **New Feature**: Added ability for events to be saved into a .json file and to read from it when Athena launches.
  * What it does: Events created are able to be stored when the app closes and be loaded again when the app reopens.
  * Justification: Allows for events to be kept from session to session.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=linkedink)

* **Enhancements to existing features**:
  * Updated delete contact command (delete -c) to delete the contact from any event the contact is attending as well.
  * Updated delete contact command (delete -c) to be able to delete multiple contacts with a list of indices.
  * Added ModelManagerBuilder class for the different test cases to use.
  * Added sample events under SampleDataUtil class.
  
* **Documentation**:
  * User Guide:
    * Updated the documentation for commands `add -e`, `delete -e` and `edit -e`.
    * Updated the `clear -e` command with a warning and clear picture showing the effects of the command.

* **Community**:
  * Reported bugs and suggestions for other teams in the class. ([Link](https://github.com/LinkedInk/ped/issues))
  * Helped a teammate with some issues he faced for his reminder feature with some suggested changes. ([Link](https://github.com/bangyiwu/tp/pull/1))