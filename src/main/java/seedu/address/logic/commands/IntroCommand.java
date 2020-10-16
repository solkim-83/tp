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
