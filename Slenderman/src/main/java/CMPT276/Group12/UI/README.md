Game UI:
Menu super class, needs to be able to detect input with the mouse (preferably also the keys, but that can be a later thing) and output that action to the attached Controller as a String, i.e. "exit", "resume", "start", etc, please just document what you decide on. Menu should also implement JPanel, or another solution for displaying without too many third party libs.

The PlayerHUD, as it is in the UML and design mockup has a menu btn in the top left, but this might be in bad taste, so you can decide whether to implement it or to just have text saying "ESC: MENU" or something. I also do not have anything in mind for the TIME component, at the moment, but will probably use the Date class. I will update on this later this week.

PaseMenu should overlay atop the game. 

MainMenu is the splash screen of the game.