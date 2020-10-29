package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.tag.ReadOnlyTagTree;


/**
 * API of the Storage component
 */

public interface Storage extends AddressBookStorage, CalendarStorage,
        UserPrefsStorage, TagTreeStorage, RemindersStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getCalendarFilePath();

    @Override
    Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException;

    @Override
    void saveCalendar(ReadOnlyCalendar calendar) throws IOException;

    Path getRemindersFilePath();

    @Override
    Optional<ReadOnlyReminders> readReminders() throws DataConversionException, IOException;

    @Override
    void saveReminders(ReadOnlyReminders reminders) throws IOException;

    Path getTagTreeFilePath();

    @Override
    Optional<ReadOnlyTagTree> readTagTree() throws DataConversionException, IOException;

    @Override
    void saveTagTree(ReadOnlyTagTree tagTree) throws IOException;
}
