package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.Reminder;

/**
 * Deletes an event identified using its displayed index from the Athena.
 */
public class RemindEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.REMIND.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reminds you of the event \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMIND_EVENT_SUCCESS = "Reminder in %d days set for event: ";

    public static final String MESSAGE_FAILURE = "Please input a date with the prefix in/";

    private final Index targetIndex;
    private final int daysInAdvance;

    public RemindEventCommand(Index targetIndex, int daysInAdvance) {
        this.targetIndex = targetIndex;
        this.daysInAdvance = daysInAdvance;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getSortedFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventForReminder = lastShownList.get(targetIndex.getZeroBased());
        Reminder toAdd = new Reminder(eventForReminder, daysInAdvance);
        Reminder.addReminder(toAdd);

        return new CommandResult(String.format(MESSAGE_REMIND_EVENT_SUCCESS, daysInAdvance) + eventForReminder.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemindEventCommand // instanceof handles nulls
                && targetIndex.equals(((RemindEventCommand) other).targetIndex)); // state check
    }
}

