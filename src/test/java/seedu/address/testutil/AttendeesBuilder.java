package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.association.FauxPerson;
import seedu.address.model.person.Person;

/**
 * Helps build associated persons for {@link Event} class
 */
public class AttendeesBuilder {

    private Set<FauxPerson> associatedPersons;

    /**
     * Creates a AttendeesBuilder with no associated persons
     */
    public AttendeesBuilder() {
        this.associatedPersons = new HashSet<>();
    }

    /**
     * Adds {@code persons} to this builder
     * @param persons associated persons
     * @return this builder
     */
    public AttendeesBuilder withPerson(Person... persons) {
        for (Person person : persons) {
            associatedPersons.add(new FauxPerson(person));
        }
        return this;
    }

    public Set<FauxPerson> build() {
        return this.associatedPersons;
    }
}
