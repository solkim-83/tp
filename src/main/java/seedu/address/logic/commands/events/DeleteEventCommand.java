package seedu.address.logic.commands.events;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Deletes an event identified using its displayed index from the Athena.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.DELETE.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Deletes the event identified by the index number used in the displayed event list.\n\n"
            + "Parameters: EVENT_INDEX_LIST (must be positive integer(s))\n\n"
            + "Examples:\n"
            + COMMAND_WORD + " " + COMMAND_TYPE + " 1" + "\n"
            + COMMAND_WORD + " " + COMMAND_TYPE + " 1,2";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event(s): %1$s";

    private final ArrayList<Index> targetIndexes;

    public DeleteEventCommand(ArrayList<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getSortedFilteredEventList();

        StringBuilder eventsStringBuilder = new StringBuilder();

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }

            Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteEvent(eventToDelete);
            eventsStringBuilder.append("\n\n" + eventToDelete);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventsStringBuilder.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && targetIndexes.equals(((DeleteEventCommand) other).targetIndexes)); // state check
    }
}
