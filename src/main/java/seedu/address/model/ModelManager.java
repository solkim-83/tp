package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;
import seedu.address.model.tag.TagTreeImpl;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final Calendar calendar;
    private final UserPrefs userPrefs;
    private final TagTree tagTree;
    private final RemindersImpl reminders;
    private final ContactTagIntegrationManager contactTagIntegrationManager;

    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;
    private final FilteredList<Event> filteredEvents;
    private final SortedList<Event> sortedEvents;
    private final FilteredList<Reminder> filteredReminders;
    private final SortedList<Reminder> sortedReminders;

    /**
     * Initializes a ModelManager with the given addressBook, calendar, tagTree, reminders and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyCalendar calendar,
                        ReadOnlyTagTree tagTree,
                        ReadOnlyUserPrefs userPrefs,
                        ReadOnlyReminders reminders) {
        super();
        requireAllNonNull(addressBook, tagTree, userPrefs);

        logger.fine("Initializing with AddressBook: " + addressBook + "\n"
                + ", Calendar: " + calendar + "\n"
                + ", TagTree: " + tagTree + "\n"
                + ", Reminders: " + reminders + "\n"
                + " and UserPrefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.calendar = new Calendar(calendar);
        this.tagTree = new TagTreeImpl(tagTree);
        this.reminders = new RemindersImpl(reminders);
        this.userPrefs = new UserPrefs(userPrefs);

        contactTagIntegrationManager = new ContactTagIntegrationManager(this.addressBook, this.tagTree);

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);

        filteredEvents = new FilteredList<>(this.calendar.getEventList());
        sortedEvents = new SortedList<>(filteredEvents);

        filteredReminders = new FilteredList<>(this.reminders.getRemindersList());
        sortedReminders = new SortedList<>(filteredReminders);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getCalendarFilePath() {
        return userPrefs.getCalendarFilePath();
    }

    @Override
    public void setCalendarFilePath(Path calendarFilePath) {
        requireNonNull(calendarFilePath);
        userPrefs.setCalendarFilePath(calendarFilePath);
    }

    @Override
    public Path getTagTreeFilePath() {
        return userPrefs.getTagTreeFilePath();
    }

    @Override
    public void setTagTreeFilePath(Path tagTreeFilePath) {
        requireNonNull(tagTreeFilePath);
        userPrefs.setTagTreeFilePath(tagTreeFilePath);
    }

    @Override
    public Path getRemindersFilePath() {
        return userPrefs.getRemindersFilePath();
    }

    @Override
    public void setRemindersFilePath(Path remindersFilePath) {
        requireNonNull(remindersFilePath);
        userPrefs.setTagTreeFilePath(remindersFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setCalendar(ReadOnlyCalendar calendar) {
        this.calendar.resetData(calendar);
    }

    @Override
    public ReadOnlyCalendar getCalendar() {
        return calendar;
    }

    @Override
    public void setTagTree(ReadOnlyTagTree tagTree) {
        this.tagTree.copy(tagTree);
    }

    @Override
    public ReadOnlyTagTree getTagTree() {
        return tagTree;
    }

    @Override
    public void setReminders(ReadOnlyReminders reminders) {
        this.reminders.resetData(reminders);
    }

    @Override
    public ReadOnlyReminders getReminders() {
        return reminders;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return calendar.hasEvent(event);
    }

    @Override
    public boolean hasClashingEvent(Event event) {
        requireNonNull(event);
        return calendar.hasClashingEvent(event);
    }

    @Override
    public boolean hasTag(Tag tag) {
        return contactTagIntegrationManager.hasTag(tag);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        return reminders.hasReminder(reminder);
    }

    @Override
    public void deletePerson(Person target) {
        contactTagIntegrationManager.deletePerson(target);
        calendar.deletePersonAssociation(target);
    }

    @Override
    public void deleteEvent(Event target) {
        calendar.removeEvent(target);
        reminders.deleteReminderOfEvent(target);
    }

    @Override
    public void deleteTag(Tag tag) {
        assert tag != null;
        contactTagIntegrationManager.deleteTag(tag);
    }

    @Override
    public void deleteTagRecursive(Tag tag) {
        assert tag != null;
        contactTagIntegrationManager.deleteTagRecursive(tag);
    }

    @Override
    public void deleteReminder(Reminder reminder) {
        assert reminder != null;
        reminders.removeReminder(reminder);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addEvent(Event event) {
        calendar.addEvent(event);
    }

    @Override
    public void addReminder(Reminder reminder) {
        reminders.addReminder(reminder);
    }

    @Override
    public void addSubTagTo(Tag superTag, Tag subTag) {
        tagTree.addSubTagTo(superTag, subTag);
    }

    @Override
    public void removeChildTagFrom(Tag parentTag, Tag childTag) {
        tagTree.removeSubTagFrom(parentTag, childTag);
    }

    @Override
    public boolean isSubTagOf(Tag superTag, Tag subTag) {
        return tagTree.isSubTagOf(superTag, subTag);

    }

    @Override
    public void addPersonToTag(Tag tag, Person person) {
        addressBook.addPersonToTag(tag, person);
    }

    @Override
    public void removePersonFromTag(Tag tag, Person person) {
        contactTagIntegrationManager.removePersonFromTag(tag, person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        contactTagIntegrationManager.setPerson(target, editedPerson);
        calendar.setPersonAssociation(target, editedPerson);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        calendar.setEvent(target, editedEvent);
        reminders.updateReminder(target, editedEvent);
    }

    @Override
    public void sortPerson(Comparator<Person> comparator) {
        sortedPersons.comparatorProperty().setValue(comparator);
    }

    @Override
    public void permaSortContacts(Comparator<Person> comparator) {
        addressBook.sortPerson(comparator);
    }

    public void sortEvent(Comparator<Event> comparator) {
        sortedEvents.comparatorProperty().setValue(comparator);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getSortedFilteredPersonList() {
        return sortedPersons;
    }

    @Override
    public ObservableList<Event> getSortedFilteredEventList() {
        return sortedEvents;
    }

    @Override
    public ObservableList<Reminder> getSortedFilteredReminderList() {
        return sortedReminders;
    }


    // Person-tag related methods

    @Override
    public Set<Person> getPersonsWithTag(Tag tag) {
        return addressBook.getPersonsWithTag(tag);
    }

    @Override
    public Set<Tag> getPersonTags() {
        return addressBook.getTags();
    }

    @Override
    public Set<Tag> getSuperTags() {
        return tagTree.getSuperTags();
    }

    @Override
    public Set<Tag> getChildTags(Tag tag) {
        return tagTree.getSubTagsOf(tag);
    }

    @Override
    public Set<Tag> getSubTagsRecursive(Tag tag) {
        return tagTree.getSubTagsRecursive(tag);
    }

    @Override
    public Set<Person> getPersonsRecursive(Tag tag) {
        return contactTagIntegrationManager.getAllPersonsUnderTag(tag);
    }

    @Override
    public void deletePersonsByTag(Tag tag) {
        //clears all deleted persons from events associating them.
        contactTagIntegrationManager.getAllPersonsUnderTag(tag).stream()
                                    .forEach(person -> {
                                        calendar.deletePersonAssociation(person);
                                    });
        contactTagIntegrationManager.deleteTagAndDirectContacts(tag);
    }

    // Filter/sort related methods

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public void deleteObsoleteReminders() {
        reminders.deleteObsoleteReminders();
    };


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && calendar.equals(other.calendar)
                && userPrefs.equals(other.userPrefs)
                && tagTree.equals(other.tagTree)
                && reminders.equals(other.reminders)
                && contactTagIntegrationManager.equals(other.contactTagIntegrationManager)
                && filteredPersons.equals(other.filteredPersons)
                && sortedPersons.equals(other.sortedPersons)
                && filteredEvents.equals(other.filteredEvents)
                && sortedEvents.equals(other.sortedEvents)
                && filteredReminders.equals(other.filteredReminders)
                && sortedReminders.equals(other.sortedReminders);
    }

}
