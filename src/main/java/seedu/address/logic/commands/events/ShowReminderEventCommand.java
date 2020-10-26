package seedu.address.logic.commands.events;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ShowReminderEventCommand extends Command {

    public static final String COMMAND_WORD = "intro";

    public static final String SHOWING_REMINDER_MESSAGE = "Opened reminder window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_REMINDER_MESSAGE, false, false);
    }

}
