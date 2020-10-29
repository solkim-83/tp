package seedu.address.logic.commands.reminders;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;

public class ShowReminderEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.SHOW.toString();

    public static final String COMMAND_TYPE = CommandType.REMINDER.toString();

    public static final String SHOWING_REMINDER_MESSAGE = "Opened reminder window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_REMINDER_MESSAGE, false, false);
    }

}
