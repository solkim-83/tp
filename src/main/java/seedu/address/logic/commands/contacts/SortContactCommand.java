package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
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
            + ": Sorts the currently displayed contact list"
            + "by the index command entered:\n\n"
            + "1: sorts by lexicographical order of their names\n"
            + "2: sorts by lexicographical order of their address\n"
            + "3: sorts by lexicographical order of their email\n\n"
            + "Parameters:\nINDEX (must be between 1 and 3)\n\n"
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
