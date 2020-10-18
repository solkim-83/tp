package seedu.address.model.event;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


/**
 * Represents an Events in Calendar.
 */
public class Event {
    private final Description description;
    private final Time time;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(Description description, Time time, Set<Tag> tags) {
        requireAllNonNull(description, time);
        this.description = description;
        this.time = time;
        this.tags.addAll(tags);
    }

    public Description getDescription() {
        return description;
    }

    public Time getTime() {
        return time;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both events of the have the same description
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, time, tags);
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
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" At: ")
                .append(getTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
