# Repository #
Repository consists of three folders: brancehs, trunk, tags.

### _TRUNK_ ###
Here work copy of project is located. 'Work' means it is completed, can be compiled, runned and doesn't have obvious bugs. It is intolerable to commit here partly completed task or code, that didn't checked on bugs by programmer himself.

### _TAGS_ ###
All builds and releases (which are actually builds) are placed into this folder.

### _BRANCHES_ ###
Programmer have to create separate branch for any continuous task. He commits to branch all changes, complete task, tests all elementary bugs and ONLY AFTER that merge it with trunk.

Elementary bugfixes and tasks that takes NO MORE than ONE commit programmer may commint directly into trunk without creation any branch.

# Commiting #
Every commint MUST have a commentary, where major changes are enumerated (what was added, fixed, optimized). Comments must be even for the least important commints (if you dont know what write in the commint it most likey the commit is useless).

In commits (and in CHANGELOG.txt) desirable to use such labels:
  * `[+`] - added new elements
  * `[-`] - removed some elements
  * `[*`] - some changes were made
  * `[f`] - some bug was fixed (you should write the number of bug)

# Project Structure #

Project consists of such files:
  * README.txt, where a brief overview of project is placed, enumerated its members and some other important information (e.g. installation guide).
  * CHANGELOG.txt, where all major changes between releases are described.
  * DEV\_CHANGELOG.txt, where major changes between internal builds are described.
  * QATASKS.txt (for inter-released builds), where QA tasks for only this build are denoted.