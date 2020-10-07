package seedu.address.model.event;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    Description description;
    Time time;

    public Event(Description description, Time time) {
        this.description = description;
        this.time = time;
    }

    public Description getDescription() {
        return description;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
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
                .append(getTime());
        return builder.toString();
    }
}
