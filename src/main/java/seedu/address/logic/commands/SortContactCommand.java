package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comparators.PersonComparator;
import seedu.address.model.Model;

/**
 * Sorts the currently displayed persons in a specific order.
 * Index entered determines the specific order.
 */
public class SortContactCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.SORT.toString();

    public static final String COMMAND_TYPE = CommandType.CONTACT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Sorts the currently displayed list"
            + "by the index command entered "
            + "1 will be sort by alphabetical order of their names\n"
            + "2 will be sort by alphabetical order of their address\n"
            + "3 will be sort by alphabetical order of their email\n"
            + "Parameters: INDEX (must be between 1 and 4) "
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " 1 ";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    /**
     * @param index the order in which to sort the address book
     */
    public SortContactCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortPerson(PersonComparator.chooseComparator(index));

        return new CommandResult(indexMessage(index));
    }

    /**
     * Returns the appropriate console message for the index.
     */
    public String indexMessage(Index index) {
        int input = index.getOneBased();
        switch (input) {
        case 1:
            return "Sorted by name in alphabetical order";
        case 2:
            return "Sorted by address in alphabetical order";
        case 3:
            return "Sorted by email in alphabetical order";
        default:
            return "Invalid index entered, refer to below for the command's proper usage: "
                    + MESSAGE_USAGE;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortContactCommand)) {
            return false;
        }

        // state check
        SortContactCommand e = (SortContactCommand) other;
        return index.equals(e.index);
    }

}
