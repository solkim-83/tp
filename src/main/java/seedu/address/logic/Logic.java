package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.reminder.Reminder;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Executes the introduction command and returns the result.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    CommandResult executeIntro() throws CommandException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the Calendar
     *
     * @see seedu.address.model.Model#getCalendar()
     */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns the Reminders
     *
     * @see seedu.address.model.Model#getReminders()
     */
    ReadOnlyReminders getReminders();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<Event> getFilteredEventList();

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<Reminder> getFilteredReminderList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' calendar file path.
     */
    Path getCalendarFilePath();

    /**
     * Returns the user prefs' reminders file path.
     */
    Path getRemindersFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
