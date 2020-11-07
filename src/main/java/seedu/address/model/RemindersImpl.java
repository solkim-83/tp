package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueRemindersList;

/**
 * Wraps all data at the calendar level
 * Duplicates are not allowed (by .isSameReminder comparison)
 */
public class RemindersImpl implements ReadOnlyReminders {

    private final UniqueRemindersList reminders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        reminders = new UniqueRemindersList();
    }

    public RemindersImpl() {}

    /**
     * Creates an RemindersImpl using the Reminders in the {@code toBeCopied}
     */
    public RemindersImpl(ReadOnlyReminders toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the reminder list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    /**
     * Resets the existing data of this {@code RemindersImpl} with {@code newData}.
     */
    public void resetData(ReadOnlyReminders newData) {
        requireNonNull(newData);

        setReminders(newData.getRemindersList());
    }

    /// reminder-level operations

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in the calendar.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a reminder to the calendar.
     * The reminder must not already exist in the calendar.
     */
    public void addReminder(Reminder r) {
        reminders.add(r);
    }

    /**
     * Replaces the given reminder {@code target} in the list with {@code editedReminder}.
     * {@code target} must exist in the calendar.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireNonNull(editedReminder);

        reminders.setReminder(target, editedReminder);
    }

    /**
     * Removes {@code key} from this {@code RemindersImpl}.
     * {@code key} must exist in the calendar.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }

    /**
     * Removes all reminders for events that have expired.
     */
    public void deleteObsoleteReminders() {
        ArrayList<Reminder> toBeDeleted = new ArrayList<Reminder>();
        for (Reminder r: reminders) {
            if (r.getEventToRemind().getTime().getTime().isBefore(LocalDateTime.now())) {
                toBeDeleted.add(r);
            }
        }
        if (toBeDeleted != null) {
            toBeDeleted.stream()
                        .forEach(reminder -> removeReminder(reminder));
        }
    }

    /**
     * When an event is being deleted, the associated reminder should be deleted as well.
     * This is what the command aims to achieve.
     */
    public void deleteReminderOfEvent(Event eventToDelete) {
        Reminder toDelete = null;
        for (Reminder r: reminders) {
            if (r.getEventToRemind().equals(eventToDelete)) {
                toDelete = r;
            }
        }
        if (toDelete != null) {
            removeReminder(toDelete);
        }
    }

    /**
     * When an event is being edited, the associated reminder should be edited as well.
     * This is what the command aims to achieve.
     */
    public void updateReminder(Event targetEvent, Event editedEvent) {
        Reminder toDelete = null;
        for (Reminder r: reminders) {
            if (r.getEventToRemind().equals(targetEvent)) {
                toDelete = r;
            }
        }
        if (toDelete != null) {
            removeReminder(toDelete);
            int daysInAdvance = (int) ChronoUnit.DAYS.between(toDelete.getEventToRemind().getTime().time.toLocalDate(),
                    toDelete.getReminderDate().time.toLocalDate());
            Reminder editedReminder = new Reminder(editedEvent, daysInAdvance);

            addReminder(editedReminder);
        }

    }

    //// util methods

    @Override
    public String toString() {
        return reminders.asUnmodifiableObservableList().size() + " reminders";
        // TODO: refine later
    }

    @Override
    public ObservableList<Reminder> getRemindersList() {
        return reminders.asUnmodifiableObservableList();
    }

    @Override
    public boolean hasRemindersDue() {
        return reminders.hasRemindersDue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemindersImpl // instanceof handles nulls
                && reminders.equals(((RemindersImpl) other).reminders));
    }

    @Override
    public int hashCode() {
        return reminders.hashCode();
    }


}
