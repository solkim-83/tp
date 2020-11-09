---
layout: page
title: Illio Suardi's Project Portfolio Page
---

## Project: Athena

Athena is a **desktop app for managing contacts and events, optimized for use via a Command Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI).

Given below are my contributions to the project.

 
* **New Feature**: Added an introduction window.
  * What it does: Opens an introduction window when Athena detects a first time user opening the application.
  * Justification: This feature improves the product's accessibility for newer users. Athena is an application that uses
  CLI, so it can be overwhelming for users who are unfamiliar with these types of applications or the commands and
  functionalities of Athena. This window includes a short tutorial that includes basic command usage, so users can be
  familiarised with how to use Athena.
  * Highlights: The code necessary for this feature comprises both command-level and GUI-level code. As such, it was
  necessary to understand both how to call the command in a non-standard manner (when the application is opened, as
  opposed to when a user inputs something), and how to display a new, custom window.
 
* **New Feature**: Added the following tag command: `find`
  * What it does: Allows users to search for tags by their names and type
  * Justification: Tagging was initially implemented as a supplementary feature to the contacts. However, as the depth
  of tag-related features grew, it became apparent that the ability to search for certain tags would become necessary.
  * Highlights: A `TagUtil` class was created as a container for commonly used tag-related methods and messages.
  Additionally, implementation of partial keyword matching was necessary for this feature, which could be used in the
  future.
 
* **Enhancement to existing features**: Streamlining of command input and parsing structure
(pull request [\#120](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/120))
  * Justification: At that time, the commands for different functionalities were inconsistent: for instance, `add` added
  contacts and `addEvent` added events. The input structure is now standardised by using a common command word
  (`add` in this case) and a targeted command type (`-c` for contacts and `-e` for events). This also makes it easier to
  add more commands in the future, as the parser is structured more clearly.

<div style="page-break-after: always;"></div>
 
* **Enhancement to existing features**: Refactor command packaging to individual packages 
(pull request [\#158](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/158))
  * Justification: As more commands were added, the `commands` and `parser` packages became increasingly cluttered and
  thus, hard to navigate. Refactoring to individual packages solved this problem.
  
* **Enhancement to existing features**: Updated GUI to display data in a more intuitive manner
(pull request [\#160](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/160))
  * Justification: As the cards displaying contacts and events had relatively little content, they did not occupy
  much space horizontally. Thus, it made for sense for the GUI to display the contacts and events panel horizontally
  rather than vertically, so more cards can be seen on one page.
  
* **Enhancement to existing features**: Updated error messages to be more reader-friendly
(pull requests [\#160](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/160) and
[\#240](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/240))
  * Justification: Previous error messages were displayed on a larger, horizontal window. Text wrapping and whitespace
  was added to make it more reader friendly in the new GUI. Additionally, error messages for invalid command inputs
  were made more explicit.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=fyshhh)
    
* **Project management**:
  * Managed version `v1.3` release
  * Fixed multiple miscellaneous bugs identified in PED [\#240](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/240)
  
* **Documentation**:
  * User Guide:
    * Updated introduction and quick start sections [\#48](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/48),
    [\#49](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/49)
    * Updated command input across multiple iterations [\#88](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/88),
    [\#162](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/162)
    * Implemented multiple formatting fixes for user readability [\#57](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/57),
    [\#59](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/59), [\#162](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/162)
    * Updated incorrect sections pointed out in PED [\#240](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/240)
  * Developer Guide:
    * Updated product scope, user stories and non-functional requirements [\#51](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/51),
    [\#52](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/52)
    * Updated outdated links to relevant code files [\#141](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/141)
    * Updated outdated diagrams throughout entire document [\#283](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/283)
    * Added implementation details of introduction window [\#185](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/185)
    * Added implementation details of commands [\#283](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/283)
    
* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#67](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/67),
  [\#89](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/89), [\#117](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/117),
  [\#120](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/120), [\#123](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/123),
  [\#143](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/143), [\#180](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/180)
  are more significant ones.
  * Reported bugs and suggestions for other teams in the class. ([examples can be found here](https://github.com/fyshhh/ped/issues))
