package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.RemindersImpl;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.reminder.Reminder;


/**
 * An Immutable Reminders that is serializable to JSON format.
 */
@JsonRootName(value = "reminders")
class JsonSerializableReminders {

    public static final String MESSAGE_DUPLICATE_EVENT = "Reminders list contains duplicate reminder(s).";

    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableReminders} with the given events.
     */
    @JsonCreator
    public JsonSerializableReminders(@JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        this.reminders.addAll(reminders);
    }

    /**
     * Converts a given {@code ReadOnlyReminders} into this class for json use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableReminders}.
     */
    public JsonSerializableReminders(ReadOnlyReminders source) {
        reminders.addAll(source.getRemindersList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this reminders into the model's {@code RemindersImpl} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RemindersImpl toModelType() throws IllegalValueException {
        RemindersImpl remindersImpl = new RemindersImpl();
        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (remindersImpl.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            remindersImpl.addReminder(reminder);
        }
        return remindersImpl;
    }

}
