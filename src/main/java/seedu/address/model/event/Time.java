package seedu.address.model.event;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Represents an Event's Time in the Calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime (String)}
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS = "Date time format not accepted, the following are accepted:\n"
            + "dd-MM-yyyy HH:mm\n"
            + "d-M-yy H:m\n"
            + "yyyy-dd-MM HH:mm\n"
            + "yy-d-M H:m\n"
            + "[.][/][-][:] can be used interchangeably to indicate date, time, year of the event.";

    private static final String symbol = "[.][/][-][:]";
    private static final String STANDARD_TIME_PATTERN = "[d" + symbol + "M" + symbol + "yyyy H" + symbol + "m]"
            + "[yyyy" + symbol + "d" + symbol + "M H" + symbol + "m]"
            + "[d" + symbol + "M" + symbol + "yy H" + symbol + "m]"
            + "[yy" + symbol + "d" + symbol + "M H" + symbol + "m]";

    public final LocalDateTime time;

    /**
     * Constructs a {@link Time}.
     *
     * @param timeInput A valid time.
     */
    public Time(String timeInput) {
        requireNonNull(timeInput);
        checkArgument(isValidTime(timeInput), MESSAGE_CONSTRAINTS);
        this.time = parse(timeInput);
    }

    /**
     * Constructs a {@link Time}.
     *
     * @param timeInput A valid {@link LocalDateTime}.
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
    public static boolean isValidTime(String timeInput) {
        try {
            LocalDateTime.parse(timeInput, DateTimeFormatter.ofPattern(STANDARD_TIME_PATTERN));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDateTime parse(String timeInput) {
        return LocalDateTime.parse(timeInput, DateTimeFormatter.ofPattern(STANDARD_TIME_PATTERN));
    }

    // getDisplayName controls the format of time displayed in the GUI panel and in the response.
    public String getDisplayName() {
        StringBuilder builder = new StringBuilder();

        builder.append(time.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH) + " ")
                .append(getDayOfMonthAsString() + " ")
                .append(time.getMonth().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH) + " ")
                .append(time.getYear() + " ")
                .append(time.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase());

        LocalDate now = LocalDate.now();
        long dayDifference = DAYS.between(now, time.toLocalDate());
        if (dayDifference < 0) {
            if (dayDifference < -1) {
                builder.append(" (" + (dayDifference * -1) + " days ago)");
            } else {
                builder.append(" (" + (dayDifference * -1) + " day ago)");
            }
        } else if (dayDifference == 0) {
            builder.append(" (Today)");
        } else if (dayDifference > 0) {
            if (dayDifference > 1) {
                builder.append(" (In " + dayDifference + " days)");
            } else {
                builder.append(" (In " + dayDifference + " day)");
            }
        }

        return builder.toString();
    }

    // private method for use in getDisplayName above
    private String getDayOfMonthAsString() {
        switch (time.getDayOfMonth()) {
        case 1:
            return "1st";
        case 2:
            return "2nd";
        case 3:
            return "3rd";
        case 21:
            return "21st";
        case 22:
            return "22nd";
        case 23:
            return "23rd";
        case 31:
            return "31st";
        default:
            return time.getDayOfMonth() + "th";
        }
    }

    /**
     * Returns a string that can be parsed to make another Time object of the same time value
     * @return string representing part of a command
     */
    // toString() controls the format of time saved in calendar.json file
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
