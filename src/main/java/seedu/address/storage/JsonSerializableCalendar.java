package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.event.Event;


/**
 * An Immutable Calendar that is serializable to JSON format.
 */
@JsonRootName(value = "calendar")
class JsonSerializableCalendar {

    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCalendar} with the given events.
     */
    @JsonCreator
    public JsonSerializableCalendar(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyCalendar} into this class for json use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCalendar}.
     */
    public JsonSerializableCalendar(ReadOnlyCalendar source) {
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this calendar into the model's {@code Calendar} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Calendar toModelType() throws IllegalValueException {
        Calendar calendar = new Calendar();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (calendar.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            calendar.addEvent(event);
        }
        return calendar;
    }

}
