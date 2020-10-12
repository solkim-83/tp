/*
todo - implement this. implementation should be close to that of HelpCommand, with different text.
this implies that new windows need to be made as well - refer to implementation of HelpWindow.java.
all in all - implement this, IntroWindow.java and IntroWindow.fxml.

this is currently implemented, along with an unconditional call in UiManager.java.

*/

package seedu.address.logic.commands;

import seedu.address.model.Model;

public class IntroCommand extends Command {

    public static final String COMMAND_WORD = "intro";

    public static final String SHOWING_INTRO_MESSAGE = "Opened introduction window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_INTRO_MESSAGE, false, false);
    }

}
