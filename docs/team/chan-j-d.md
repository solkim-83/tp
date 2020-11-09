---
layout: page
title: Chan Jun Da's Project Portfolio Page
---
## Project: Athena

Athena - Athena is an integrated contact and events manager. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

Given below are my contributions to the project.

* **New Feature**: Added tag-to-contact backend tracking. 
  * What it does: allows developers to quickly search up all contacts that contain a specific tag. The mapping is updated after every user command that could possibly change a contact's tags. 
  * Justification: This allows for higher level modules to make use of this relationship without creating cyclic dependency between `Person`s and `Tag`s.
  * Highlights: This enhancement is necessary for upcoming features that require queries by tag. The implementation for this feature was difficult as it had to be done in a way that did not radically affect other modules or require other developers to perform the tag-contact updates manually with new commands.
  
* **New Feature**: Added support for parent-child-tag relationship and integration with current contact commands.
  * What it does: allows creation of directional tag-to-tag relationship. It also handles management of tag-to-tag relationship when contacts and tags are edited, and saves it to the hard disk.
  * Justification: This allows future developers to develop higher-level commands that 
       * allows users to categorise their contacts into complex relationship trees
       * perform actions on groups of contacts identified by tags rather than individual contacts. 
  * Highlights: This enhancement was crucial to several upcoming tag-level features that our team intended to implement. This feature was particularly difficult to implement as it required a data structure implementation that supported frequent edits of tag-to-tag relations. 
  Additionally, very specific behavior choices needed to be made regarding several key issues (e.g. deletion of a tag in the middle of the tag tree). There were also many potential issues with the feature that had to be predicted and protected against (e.g. creation of cyclic tag relations).
<div style="page-break-after: always;"></div>

* **New Feature**: Added the following tag commands: `list`, `view`, `add`, `edit` and `delete`
  * Justification: This allows users to fully utilise the parent-child-tag support of Athena by providing users with the tools for creating their own tag-tree relations. It also allows them to perform some actions relating to tags more efficiently. 
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=chan-j-d)

* **Project management**:
  * Set up github team organisation and team repository
  * Managed version `v1.3.trial` release
  * Managed transition from `v.1.3` milestone to `v.1.4` milestone 
  * Updated project `README.md` (Pull request [\#40](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/40))
  * Updated project repository settings in `_config.yml` and `build.gradle` (Pull requests [\#44](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/44), [\#133](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/133))
  
* **Enhancement to existing feature**: Changed `edit -c` to support tag addition and tag removal instead of a replacement of all tags. `rt/*` was implemented to continue to support a quick way to remove all tags of a contact. (Pull request [\#71](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/71))
  * Justification: As future features will rely on each contacts' tags, users choose which tags they would like to add and remove instead of replacing the entire set of tags on each edit as was done in AddressBook-Level3. This is meant to improve user convenience. 
  
* **Enhancement to existing feature**: Changed `find -c` to support searches via additional fields. (Pull request [\#89](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/89))
  * Justification: Original search feature was restricted only to name search. This improvement is to support search by other fields to increase specificity when the number of contacts grows large.

* **Documentation**:
  * User Guide:
    * Updated documentation for `edit -c` and `find -c` commands [\#38](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/38)
    * Added documentation for tag commands `list -t`, `view -t`, `add -t`, `delete -t`, `edit -t` [\#175](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/175)
    * Added documentation for an upcoming feature `viewtree -t`
    * Added specific tag terms to user guide glossary [\#175](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/175)
  * Developer Guide:
    * Removed outdated content in Developer Guide and updated relevant diagrams and commands to fit Athena [\#180](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/180)
        * Updated architecture sequence diagram to use correct command syntax
        * Changed `Model` and `Storage` UML class diagram to match Athena's implementation
    * Added implementation details of contact and tag management [\#180](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/180)
        * Added full Contact and Tag UML class diagram (sub-component of Model)
        * Added UML sequence diagram to illustrate the tag deletion command
    * Added documentation for an upcoming tag tree visualisation feature [\276](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/276)
        * Added activity diagram for proposed tag tree visualisation feature [\#285](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/285)
    * Added _Instructions for Manual Testing_ for important commands I implemented (i.e. `edit -c`, `find -c`, `add -t`, `delete -t`, `edit -t`)
    * Added section for contact and tag management to the _Effort_ appendix

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#66](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/66), [\#67](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/67), 
  [\#110](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/110), [\#120](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/120), [\#148](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/148), [\#169](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/169),
  [\#178](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/178), [\#240](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/240) are the more significant ones
  * Helped with a key implementation of the `sort -c` command (commit [a0a9873](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/66/commits/a0a9873f5c125c1e1fd32e8b42c5eb067765131a) in Pull request [\#66](https://github.com/AY2021S1-CS2103T-W10-4/tp/pull/66/))
