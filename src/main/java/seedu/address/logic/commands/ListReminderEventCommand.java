package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.model.Model;
import seedu.address.model.event.Reminder;

/**
 * Lists all upcoming reminders to the user.
 */
public class ListReminderEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.REMINDER.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_SUCCESS = "Here are your reminders: \n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS + Reminder.remindersToShow());
    }
}
