package seedu.address.model.event;

import seedu.address.model.event.ContactAssociation.FauxPerson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an Events in Calendar.
 */
public class Event {

    // Identity fields
    private final Description description;

    // Data fields
    private final Time time;
    private final Set<FauxPerson> associatedPersons = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(Description description, Time time, Set<FauxPerson> associatedPersons) {
        requireAllNonNull(description, time);
        this.description = description;
        this.time = time;
        this.associatedPersons.addAll(associatedPersons);
    }

    public Description getDescription() {
        return description;
    }

    public Time getTime() {
        return time;
    }

    public Set<FauxPerson> getAssociatedPersons() {
        return associatedPersons;
    }

    /**
     * Returns true if both events have the same description
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, time);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getTime().equals(getTime());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" At: ")
                .append(getTime())
                .append(" Attending: ");
        getAssociatedPersons().forEach(builder::append);
        return builder.toString();
    }
}
