package seedu.address.logic.commands.reminders;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;

/**
 * Deletes a person identified using it's displayed index from Athena.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.DELETE.toString();

    public static final String COMMAND_TYPE = CommandType.REMINDER.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Deletes the reminders identified by the index number used in the reminders list.\n\n"
            + "Parameters: INDEX (must be a positive integer)\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private final Index targetIndex;

    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reminder> reminders = model.getSortedFilteredReminderList();

        if (targetIndex.getZeroBased() >= reminders.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        Reminder reminderToDelete = reminders.get(targetIndex.getZeroBased());
        model.deleteReminder(reminderToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && targetIndex == (((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
