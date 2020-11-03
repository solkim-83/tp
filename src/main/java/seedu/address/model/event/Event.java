package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.event.association.FauxPerson;

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
        requireAllNonNull(description, time, associatedPersons);
        this.description = description;
        this.time = time;
        this.associatedPersons.addAll(associatedPersons);
    }

    /**
     * Makes a copy of {@Code toBeCopied}
     */
    public Event(Event toBeCopied) {
        requireAllNonNull(toBeCopied);
        this.description = toBeCopied.description;
        this.time = toBeCopied.time;
        this.associatedPersons.addAll(toBeCopied.associatedPersons);
    }

    public Description getDescription() {
        return description;
    }

    public Time getTime() {
        return time;
    }

    /**
     * Returns an immutable FauxPerson set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<FauxPerson> getAssociatedPersons() {
        return Collections.unmodifiableSet(associatedPersons);
    }

    /**
     * Returns true if both events have the same description and time.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getTime().equals(getTime());
    }

    /**
     * Returns true if both events have the same time.
     */
    public boolean clash(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getTime().equals(getTime());
    }

    /**
     * Deletes instances of {@code fauxPerson} associated to this event.
     */
    public void deleteFauxPerson(FauxPerson toBeDeleted) {
        associatedPersons.remove(toBeDeleted);
    }

    /**
     * Sets associated {@code target} to {@code editedFauxPerson} in this event.
     */
    public void setFauxPerson(FauxPerson target, FauxPerson editedFauxPerson) {
        if (associatedPersons.remove(target)) {
            associatedPersons.add(editedFauxPerson);
        }
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
                && otherEvent.getTime().equals(getTime())
                && otherEvent.getAssociatedPersons().equals(getAssociatedPersons());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append("\nAt: ")
                .append(getTime().getDisplayName());

        if (associatedPersons.size() != 0) {
            builder.append("\n\nPeople attending: ");
            int counter = 1;
            for (FauxPerson fauxPerson : associatedPersons) {
                builder.append("\n" + counter + ") ").append(fauxPerson.displayName);
                counter++;
            }
        }

        return builder.toString();
    }
}
