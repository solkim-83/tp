package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.Comparator;

/**
 * Sorts the contacts in Athena's address book permanently.
 * Index entered determines the specific order.
 */
public class PermaSortCommand extends Command {

    public static final String COMMAND_WORD = "psort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort your contacts permanently "
            + "by the index command entered  "
            + "1 will be sort by alphabetical order of their names\n"
            + "2 will be sort by alphabetical order of their address\n"
            + "3 will be sort by alphabetical order of their first tag\n"
            + "Parameters: INDEX (must be between 1 and 3) "
            + "Example: " + COMMAND_WORD + " 1 ";

    private static final Comparator<Person> NAME_COMPARATOR = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName);
        }
    };

    private static final Comparator<Person> ADDRESS_COMPARATOR = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAddress().value.compareToIgnoreCase(o2.getAddress().value);
        }
    };

    private static final Comparator<Person> TAG_COMPARATOR = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            if (o1.getTags().size() == 0 && o2.getTags().size() != 0) {
                return 1;
            }
            if (o1.getTags().size() != 0 && o2.getTags().size() == 0) {
                return -1;
            }
            if (o1.getTags().size() == 0 && o2.getTags().size() == 0) {
                return o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName);
            }
            return o1.getTags().iterator().next().tagName
                    .compareToIgnoreCase(o2.getTags().iterator().next().tagName);
        }
    };

    private final Index index;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortAddressbook(chooseComparator(index));

        return new CommandResult(indexMessage(index));
    }

    private void requireNonNull(Model model) {
    }

    /**
     * Returns the appropriate comparator for the model manager to sort with
     */
    public Comparator<Person> chooseComparator(Index index) {
        int input = index.getOneBased();
        switch (input) {
            case 2:
                return ADDRESS_COMPARATOR;
            case 3:
                return TAG_COMPARATOR;
            default:
                return NAME_COMPARATOR;
        }
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
                return "Sorted by primary tag in alphabetical order";
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
        if (!(other instanceof PermaSortCommand)) {
            return false;
        }

        // state check
        PermaSortCommand e = (PermaSortCommand) other;
        return index.equals(e.index);
    }



    }