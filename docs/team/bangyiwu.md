---
layout: page
title: Wu Bangyi's Project Portfolio Page
---

## Project: Athena

Athena is a **desktop app for managing contacts and events, optimized for use via a Command Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI).

Given below are my contributions to the project.

* **New Feature**: Added the sorting function for contacts.
  * What it does: Allows the user to temporarily sort the displayed contacts list. User can sort based on alphabetical
  order of names, address and email.
  * Justification: This feature improves the product's usability because a user will be granted a more organized view 
  when they need it. This helps the user to search and view their contacts quickly.
  * Highlights: The code created in this function set the base for the sorting function implemented in events.
  
* **New Feature**: Added the permanent sorting function for contacts.
  * What it does: Allows the user to permanently sort the displayed contacts list. User can sort based on alphabetical
  order of names, address and email. Rather than just displaying a sorted list, this will sort the contacts storage
  itself.
  * Justification: This feature improves the product's usability because a user may not wish to re-sort their contacts 
  every time they open their apps. 
  
* **New Feature**: Added the Add Reminder function.
  * What it does: Allows the user to set custom reminders for their events X day ahead of their events.
  * Justification: This feature improves the product significantly as it makes Athena a much more active application.
  Instead of just being a passive display, it can now actively remind the user of current reminders.
  * Highlights: The code created when implementing the reminder function sets the base for all future reminder function.
  It mimics the storage system used by contacts and events, ensuring that any further development on reminders can be
  done easily. 

* **New Feature**: Added the List Reminder function.
  * What it does: Allows the user to list all their custom reminders in the feedback panel.
  * Justification: This feature makes the add reminder feature functional as it allows the users to actually look at 
  the reminders they have created.
  
  <div style="page-break-after: always;"></div>
  
* **New Feature**: Added the Delete reminder function
  * What it does: Allows the user to delete their custom reminders.
  * Justification: This feature improves the product's usability as sometimes users may not need a reminder anymore.
  Obsolete reminders for events that have already passed are also deleted automatically. 

* **New Feature**: Added the Reminders Pop Up Window.
  * What it does: When the user opens up the application, if the user has active reminders, Athena creates a pop up
  window listing all of these active reminders. It will not go away till the user actively closes it.
  * Justification: This feature improves the product's reminder feature significantly as it makes reminders more
  effective. They get more intrusive and really alerts people of their reminders.
  
* **New Feature**: Added the Delete Contacts by Tag function.
  * What it does: The user is now able to delete all contacts tagged under a specific tag the user inputs.
  * Justification: This feature improves the product's usability as one of Athena's key appeal is how it handles a large
  group of contacts. This allows the user to purge all unwanted contacts in one command. It also allows the user to do
  a pseudo-selecting of contacts before they delete selected contacts.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=bangyiwu)

* **Project management**:
  * Creating and editing the user stories chart
  * Standardized application screenshots and annotations

* **Enhancements to existing features**:
  * Added the ability to add contacts without filling in all the fields.[\#79](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/79)
      * What it does: Allows the user to add contact with some fields left empty. The empty fields will hold a temporary
      placeholder till the user decides to edit those information in.
      * Justification: This feature improves the product's usability because a user usually won't have all the information
      when creating contacts. It's unrealistic for them to have all the fields ready when they just want to create 
      a contact quickly.
      * Highlights: This enhancement eased the creation of contacts and made testing and demonstrations much faster.

* **Documentation**:
  * User Guide:
    * Added documentation for the reminder-related function [\#184](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/184)
    * Added documentation for the deletebytag function [\#273](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/273)
    * Edited and added Ui.png. (First iteration)
    * Added documentation for the sorting function [\#148](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/148)
    * Did cosmetic tweaks to fix documentation issues raised in PED: [\#226](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/226)
    * Drew and uploaded the new application logo
    
    <div style="page-break-after: always;"></div>
    
  * Developer Guide:
    * Added implementation details of the reminder-related functions. [\#273](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/273)
    * Added implementation details of the sorting feature. [\#273](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/273)    

* **Community**:
  * PRs reviewed (with non-trivial review comments): e.g. [\#242](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/242), 
  [\#185](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/185), 
  [\#183](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/183)
  * Reported bugs and suggestions for other teams in the class (examples: https://github.com/bangyiwu/ped/issues)
  * Reported bugs and suggestions for our own team member's implementations, e.g. PR [\#247](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/247) was created because of a bug I found
  * Some parts of the sorting feature I added was adopted by the event team ([\#66]())

