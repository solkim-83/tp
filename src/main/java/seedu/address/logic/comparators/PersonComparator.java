package seedu.address.logic.comparators;

import java.util.Comparator;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;


public class PersonComparator {

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

    private static final Comparator<Person> EMAIL_COMPARATOR = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getEmail().value.compareToIgnoreCase(o2.getEmail().value);
        }
    };

    /**
     * Returns the appropriate comparator for the model manager to sort with
     */
    public static Comparator<Person> chooseComparator(Index index) throws CommandException {
        int input = index.getOneBased();
        switch (input) {
        case 1:
            return NAME_COMPARATOR;
        case 2:
            return ADDRESS_COMPARATOR;
        case 3:
            return EMAIL_COMPARATOR;
        default:
            throw new CommandException("Index should be between 1 to 3");
        }
    }
}
