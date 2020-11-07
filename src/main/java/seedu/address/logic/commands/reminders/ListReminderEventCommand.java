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


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.deleteObsoleteReminders();
        ReadOnlyReminders remindersToShow = model.getReminders();
        String remindersResult = buildRemindersList(remindersToShow);
        return new CommandResult(remindersResult);
    }

    /**
     * Builds a string for the listing of reminders.
     */
    public String buildRemindersList(ReadOnlyReminders reminders) {
        String result = "";
        int count = 1;
        if (reminders.getRemindersList().size() == 0) {
            result = "You currently have no reminders set. \nSet one by using add -r";
        } else {
            result += "Here are your reminders: \n";
            for (Reminder reminder : reminders.getRemindersList()) {
                result += count + ". " + reminder.toString() + "\n";
                count++;
            }
        }
        return result;
    }
}
