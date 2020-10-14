package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' calendar file path.
     */
    Path getCalendarFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Sets the user prefs' calendar file path.
     */
    void setCalendarFilePath(Path calendarFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setCalendar(ReadOnlyCalendar calendar);

    /** Returns the Calendar */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a event with the same identity as {@code event} exists in the calendar.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given event.
     * The person must exist in the calendar.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given person.
     */
    void addEvent(Event event);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in Athena.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the Athena.
     */
    void setEvent(Event target, Event editedEvent);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getSortedFilteredPersonList();

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void sortPerson(Comparator<Person> comparator);
    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);
}
