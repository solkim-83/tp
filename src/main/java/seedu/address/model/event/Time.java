package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event's Time in the Calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime (String)}
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS = "Date time format not accepted, the following are accepted:\n"
            + "dd-MM-yyyy HH:mm";

    public final LocalDateTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param timeInput A valid time.
     */
    public Time(String timeInput) {
        requireNonNull(timeInput);
        checkArgument(isValidTime(timeInput), MESSAGE_CONSTRAINTS);
        this.time = parse(timeInput);
    }

    /**
     * Constructs a {@code Time}.
     *
     * @param timeInput A valid time.
     */
    public Time(LocalDateTime timeInput) {
        requireNonNull(timeInput);
        this.time = timeInput;
    }

    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Returns if a given string is a valid time.
     */
    // TODO: change this checker whenever more formats are added in the method below
    public static boolean isValidTime(String timeInput) {
        try {
            LocalDateTime.parse(timeInput, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // TODO: add more formats to be parsed here, add them to the valid checking method above
    public static LocalDateTime parse(String timeInput) {
        return LocalDateTime.parse(timeInput, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    // toDisplayString controls the format of time displayed in the GUI panel
    // TODO: make display prettier/more relevant to the user
    public String toDisplayString() {
        return time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    /**
     * Returns a string that can be parsed to make another Time object of the same time value
     * @return string representing part of a command
     */
    // toString() controls the format of time displayed in the response box
    // as well as the format saved in calendar.json file
    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}
