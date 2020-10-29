package seedu.address.logic.commands.general;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;

/**
 * Displays an introduction window for the user. Note that this command should not exist in the parser.
 */
public class IntroCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.INTRO.toString();

    public static final String SHOWING_INTRO_MESSAGE = "Opened introduction window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_INTRO_MESSAGE, false, false);
    }

}
