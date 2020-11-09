package seedu.address.logic.commands.contacts;

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
 * Sorts the persons in Athena's address book permanently.
 * Index entered determines the specific order.
 */
public class PermaSortContactCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.PERMASORT.toString();

    public static final String COMMAND_TYPE = CommandType.CONTACT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Sorts the currently displayed contact list permanently"
            + "by the index command entered:\n\n"
            + "1: sorts by lexicographical order of their names\n"
            + "2: sorts by lexicographical order of their address\n"
            + "3: sorts by lexicographical order of their email\n\n"
            + "Parameters:\nINDEX (must be between 1 and 3)\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " 1 ";

    public static final String MESSAGE_INVALID_INDEX = "Invalid index entered, "
            + "refer to below for the command's proper usage: "
            + MESSAGE_USAGE;

    private final Index index;

    /**
     * @param index the order in which to sort the address book
     */
    public PermaSortContactCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (index.getOneBased() < 0 || index.getOneBased() > 3) {
            throw new CommandException(PermaSortContactCommand.MESSAGE_INVALID_INDEX);
        }
        model.permaSortContacts(PersonComparator.chooseComparator(index));

        return new CommandResult(indexMessage(index));
    }

    private void requireNonNull(Model model) {
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
            return MESSAGE_INVALID_INDEX;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PermaSortContactCommand)) {
            return false;
        }

        // state check
        PermaSortContactCommand e = (PermaSortContactCommand) other;
        return index.equals(e.index);
    }
}
