package seedu.address.testutil;

import java.util.HashSet;

import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Time;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    // TODO: incorporate person associations into this builder
    public static final String DEFAULT_DESCRIPTION = "Group Meeting";
    public static final String DEFAULT_TIME = "01-01-2001 01:01";

    private Description description;
    private Time time;

    /**
     * Creates an {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        time = new Time(DEFAULT_TIME);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        description = eventToCopy.getDescription();
        time = eventToCopy.getTime();
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Event} that we are building.
     */
    public EventBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    public Event build() {
        return new Event(description, time, new HashSet<>());
    }

}
