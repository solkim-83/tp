package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.tag.ReadOnlyTagTree;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private CalendarStorage calendarStorage;
    private UserPrefsStorage userPrefsStorage;
    private TagTreeStorage tagTreeStorage;
    private RemindersStorage remindersStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage,
                          CalendarStorage calendarStorage,
                          UserPrefsStorage userPrefsStorage,
                          TagTreeStorage tagTreeStorage,
                          RemindersStorage remindersStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.calendarStorage = calendarStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.tagTreeStorage = tagTreeStorage;
        this.remindersStorage = remindersStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read AddressBook data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write AddressBook to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    // ================ Calendar methods ==============================

    @Override
    public Path getCalendarFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {
        return readCalendar(calendarStorage.getCalendarFilePath());
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read Calendar data from file: " + filePath);
        return calendarStorage.readCalendar(filePath);
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {
        saveCalendar(calendar, calendarStorage.getCalendarFilePath());
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar, Path filePath) throws IOException {
        logger.fine("Attempting to write Calendar to data file: " + filePath);
        calendarStorage.saveCalendar(calendar, filePath);
    }


    // ================ TagTree methods ==============================

    @Override
    public Path getTagTreeFilePath() {
        return tagTreeStorage.getTagTreeFilePath();
    }

    @Override
    public Optional<ReadOnlyTagTree> readTagTree() throws DataConversionException, IOException {
        return readTagTree(tagTreeStorage.getTagTreeFilePath());
    }

    @Override
    public Optional<ReadOnlyTagTree> readTagTree(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read TagTree data from file: " + filePath);
        return tagTreeStorage.readTagTree(filePath);
    }

    @Override
    public void saveTagTree(ReadOnlyTagTree tagTree) throws IOException {
        saveTagTree(tagTree, tagTreeStorage.getTagTreeFilePath());
    }

    @Override
    public void saveTagTree(ReadOnlyTagTree tagTree, Path filePath) throws IOException {
        logger.fine("Attempting to write TagTree to data file: " + filePath);
        tagTreeStorage.saveTagTree(tagTree, filePath);
    }

    // ================ TagTree methods ==============================

    @Override
    public Path getRemindersFilePath() {
        return remindersStorage.getRemindersFilePath();
    }

    @Override
    public Optional<ReadOnlyReminders> readReminders() throws DataConversionException, IOException {
        return readReminders(remindersStorage.getRemindersFilePath());
    }

    @Override
    public Optional<ReadOnlyReminders> readReminders(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read Reminders data from file: " + filePath);
        return remindersStorage.readReminders(filePath);
    }

    @Override
    public void saveReminders(ReadOnlyReminders reminders) throws IOException {
        saveReminders(reminders, remindersStorage.getRemindersFilePath());
    }

    @Override
    public void saveReminders(ReadOnlyReminders reminders, Path filePath) throws IOException {
        logger.fine("Attempting to write reminders to data file: " + filePath);
        remindersStorage.saveReminders(reminders, filePath);
    }

}
