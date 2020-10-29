package seedu.address.logic.commands.reminders;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.reminder.Reminder;

/**
 * Lists all upcoming reminders to the user.
 */
public class ListReminderEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.LIST.toString();

    public static final String COMMAND_TYPE = CommandType.REMINDER.toString();

    public static final String MESSAGE_SUCCESS = "Here are your reminders: \n";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.deleteObsoleteReminders();
        ReadOnlyReminders remindersToShow = model.getReminders();
        String remindersList = buildRemindersList(remindersToShow);
        return new CommandResult(MESSAGE_SUCCESS + remindersList);
    }

    /**
     * Builds a string for the listing of reminders.
     */
    public String buildRemindersList(ReadOnlyReminders reminders) {
        String result = "";
        int count = 1;
        for (Reminder reminder: reminders.getRemindersList()) {
            result += count + ". " + reminder.toString() + "\n";
            count++;
        }
        return result;
    }
}
