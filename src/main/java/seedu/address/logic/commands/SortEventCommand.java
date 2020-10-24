package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Sorts the currently displayed persons in a specific order.
 * Index entered determines the specific order.
 */
public class SortEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.SORT.toString();
    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Sorts the currently displayed list of events "
            + "by the index command entered\n"
            + "1 will be sort by alphabetical order of the events' descriptions\n"
            + "2 will be sort by alphabetical order of the events' time\n"
            + "Parameters: INDEX (must be between 1 and 2) "
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " 1 ";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private static final Comparator<Event> DESCRIPTION_COMPARATOR = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            return o1.getDescription().fullDescription.compareToIgnoreCase(o2.getDescription().fullDescription);
        }
    };

    private static final Comparator<Event> TIME_COMPARATOR = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            return o1.getTime().time.compareTo(o2.getTime().time);
        }
    };

    private final Index index;

    /**
     * @param index the order in which to sort the address book
     */
    public SortEventCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortEvent(chooseComparator(index));

        return new CommandResult(indexMessage(index));
    }

    /**
     * Returns the appropriate console message for the index.
     */
    public String indexMessage(Index index) {
        int input = index.getOneBased();
        switch (input) {
        case 1:
            return "Sorted by description in alphabetical order";
        case 2:
            return "Sorted by time in chronological order";
        default:
            return "Invalid index entered, refer to below for the command's proper usage: "
                    + MESSAGE_USAGE;
        }
    }

    /**
     * Returns the appropriate comparator for the model manager to sort with
     */
    public Comparator<Event> chooseComparator(Index index) {
        int input = index.getOneBased();
        switch (input) {
        case 2:
            return TIME_COMPARATOR;
        default:
            return DESCRIPTION_COMPARATOR;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortEventCommand)) {
            return false;
        }

        // state check
        SortEventCommand e = (SortEventCommand) other;
        return index.equals(e.index);
    }

}
