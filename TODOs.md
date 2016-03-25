> ## Use XStream to store game configuration and game options. ##

It will be very nice to use XStream for save/load and for epoch description. Both player and epochs are plain data objects without "graphical" and other "big" fields (spatials, images) and may be easy configured via XML by hands.
**Already used in** SHPlayerInfo, SHEpochInfo, SHLevelInfo.

> ## Use JavaScript for scripting the game (e.g. event handling). ##

It will be very useful add scripting support at least for game console to let dynamically change command behavior and maybe even dynamically add/remove commands (e.g. via add/remove console command.)

Also it is suitable move game initializing into some startup script (**_needs further investigation_**).

Scripts for event handlers are one of the future features (maybe not in this project).

> ## Migrate to JME3 ##
Use embeded NiftyGUI for UI instead of GBUI.
RESOLUTION: Not in this project.

> ## Migrate to TestNG + Mockito ##
Correct tests. USe mock objects instead of graph of stubs or real objects.

RESOLUTION:Done.

> ## Use Spring IoC for dependecy injection ##
After migrating to TestNG+Mockito and making code more testable and nice looking.

RESOLUTION: Done;