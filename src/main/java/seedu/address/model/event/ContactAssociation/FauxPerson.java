package seedu.address.model.event.ContactAssociation;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Person;

/**
 * Represents a limited version of a Person
 * To be used only as part of associating contacts with events
 */
public class FauxPerson {

    public static final String MESSAGE_CONSTRAINTS_NAME = "Display name should not be empty";
    public static final String MESSAGE_CONSTRAINTS_HASHCODE = "Hashcode is not a valid integer number";

    public final String displayName;
    public final Integer hashCode;

    /**
     * Constructs a {@code FauxPerson}.
     *
     * @param displayName name of this faux person
     * @param hashCode hashCode of actual person this is representing
     */
    public FauxPerson(String displayName, Integer hashCode) {
        requireNonNull(displayName);
        requireNonNull(hashCode);
        this.displayName = displayName;
        this.hashCode = hashCode;
    }

    /**
     * Constructs a {@code FauxPerson}.
     *
     * @param person Person to be converted into a FauxPerson
     */
    public FauxPerson(Person person) {
        requireNonNull(person);
        this.displayName = person.getName().toString();
        this.hashCode = person.hashCode();
    }

    /**
     * Checks if a given string is a valid display name.
     * @param displayName string
     * @return boolean
     */
    public static boolean isValidDisplayName(String displayName) {
        if (displayName.length() < 1) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a given string can be converted into an integer to represent hashcode
     * @param hashCode string
     * @return boolean
     */
    public static boolean isValidHashCode(String hashCode) {
        try {
            Integer.parseInt(hashCode);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
        return displayName;
    }
}
