package seedu.address.model.event.ContactAssociation;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person
 * To be used only as part of associating contacts with events
 */
public class FauxPerson {

    public final Name displayName;
    public final Integer hashCode;

    /**
     * Constructs a {@code FauxPerson}.
     *
     * @param person A valid Person.
     */
    FauxPerson(Person person) {
        requireNonNull(person);
        displayName = person.getName();
        hashCode = person.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FauxPerson) // instanceof handles nulls
                && displayName.equals(((FauxPerson) other).displayName) // state check
                && hashCode.equals(((FauxPerson) other).hashCode); // state check
    }

    @Override
    public String toString() {
        return displayName.toString();
    }
}
