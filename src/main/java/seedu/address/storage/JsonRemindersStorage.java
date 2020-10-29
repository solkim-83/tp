package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.reminder.ReadOnlyReminders;

/**
 * A class to access Calendar data stored as a json file on the hard disk.
 */
public class JsonRemindersStorage implements RemindersStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCalendarStorage.class);

    private Path filePath;

    public JsonRemindersStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRemindersFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyReminders> readReminders() throws DataConversionException, IOException {
        return readReminders(filePath);
    }

    /**
     * Similar to {@link #readReminders()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyReminders> readReminders(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableReminders> jsonReminders = JsonUtil.readJsonFile(
                filePath, JsonSerializableReminders.class);
        if (!jsonReminders.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonReminders.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveReminders(ReadOnlyReminders reminders) throws IOException {
        saveReminders(reminders, filePath);
    }

    /**
     * Similar to {@link #saveReminders(ReadOnlyReminders)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveReminders(ReadOnlyReminders reminders, Path filePath) throws IOException {
        requireNonNull(reminders);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableReminders(reminders), filePath);
    }

}
