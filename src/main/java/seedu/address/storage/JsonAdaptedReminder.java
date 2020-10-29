package seedu.address.storage;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Time;
import seedu.address.model.reminder.Reminder;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String eventDescription;
    private final String eventTime;
    private final String startDateOfReminders;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given Reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("eventDescription") String eventDescription,
                               @JsonProperty("eventTime") String eventTime,
                               @JsonProperty("startDateOfReminders") String startDateOfReminders) {
        this.eventDescription = eventDescription;
        this.eventTime = eventTime;
        this.startDateOfReminders = startDateOfReminders;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        eventDescription = source.getEventToRemind().getDescription().fullDescription;
        eventTime = source.getEventToRemind().getTime().toString();
        startDateOfReminders = source.getReminderDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder.
     */
    public Reminder toModelType() throws IllegalValueException {

        if (eventDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(eventDescription)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(eventDescription);


        if (eventTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(eventTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(eventTime);
        final Event modelEvent = new Event(modelDescription, modelTime, new HashSet<>());

        if (startDateOfReminders == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Starting date of reminders"));
        }
        if (!Time.isValidTime(startDateOfReminders)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final String modelStartDate = startDateOfReminders;

        return new Reminder(modelEvent, modelStartDate);
    }
}
