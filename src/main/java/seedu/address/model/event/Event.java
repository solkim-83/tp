package seedu.address.model.event;

import java.time.LocalDateTime;

public class Event {
    Description description;
    Time time;

    public Event(Description description, Time time) {
        this.description = description;
        this.time = time;
    }

    String getDescription() {
        return this.description.toString();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" At: ")
                .append(time.toString());
        return builder.toString();
    }
}
