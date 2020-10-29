package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RemindersImpl;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminders;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.TagTreeImpl;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.CalendarStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonCalendarStorage;
import seedu.address.storage.JsonRemindersStorage;
import seedu.address.storage.JsonTagTreeStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.RemindersStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.TagTreeStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        CalendarStorage calendarStorage = new JsonCalendarStorage(userPrefs.getCalendarFilePath());
        TagTreeStorage tagTreeStorage = new JsonTagTreeStorage(userPrefs.getTagTreeFilePath());
        RemindersStorage remindersStorage = new JsonRemindersStorage(userPrefs.getRemindersFilePath());

        storage = new StorageManager(addressBookStorage, calendarStorage,
                userPrefsStorage, tagTreeStorage, remindersStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from
     * {@code storage} (storage's AddressBook, Calendar and TagTree)
     * and {@code userPrefs}. <br>
     *
     * If {@code storage}'s AddressBook/Calendar is not found;
     * The data from the sample AddressBook/Calendar will be used instead.
     *
     * If {@code storage}'s TagTree is not found;
     * An empty TagTree is used instead.
     *
     * If errors occur when reading {@code storage}'s AddressBook/Calendar/TagTree;
     * An empty AddressBook/Calendar/TagTree will be used instead.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {

        ReadOnlyAddressBook initialAddressBook = addressBookFromStorage(storage);
        ReadOnlyCalendar initialCalendar = calendarFromStorage(storage);
        ReadOnlyTagTree initialTagTree = tagTreeFromStorage(storage);
        ReadOnlyReminders initialReminders = remindersFromStorage(storage);

        return new ModelManager(initialAddressBook, initialCalendar, initialTagTree, userPrefs, initialReminders);
    }

    // private methods below just to split up logic for initModelManager

    /**
     * Refer to {@link #initModelManager(Storage, ReadOnlyUserPrefs)} for specifications
     * @param storage storage to be read from
     * @return {@link ReadOnlyAddressBook}
     */
    private ReadOnlyAddressBook addressBookFromStorage(Storage storage) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialAddressBook;
        try {
            addressBookOptional = storage.readAddressBook();
            if (addressBookOptional.isEmpty()) {
                logger.info("AddressBook file not found. "
                        + "Will be starting with a sample AddressBook");
            }
            initialAddressBook = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("AddressBook file not in the correct format. "
                    + "Will be starting with an empty AddressBook");
            initialAddressBook = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the AddressBook file. "
                    + "Will be starting with an empty AddressBook");
            initialAddressBook = new AddressBook();
        }
        return initialAddressBook;
    }

    /**
     * Refer to {@link #initModelManager(Storage, ReadOnlyUserPrefs)} for specifications
     * @param storage storage to be read from
     * @return {@link ReadOnlyCalendar}
     */
    private ReadOnlyCalendar calendarFromStorage(Storage storage) {
        Optional<ReadOnlyCalendar> calendarOptional;
        ReadOnlyCalendar initialCalendar;
        try {
            calendarOptional = storage.readCalendar();
            if (calendarOptional.isEmpty()) {
                logger.info("Calendar file not found. "
                        + "Will be starting with a sample Calendar");
            }
            initialCalendar = calendarOptional.orElseGet(SampleDataUtil::getSampleCalendar);
        } catch (DataConversionException e) {
            logger.warning("Calendar file not in the correct format. "
                    + "Will be starting with an empty Calendar");
            initialCalendar = new Calendar();
        } catch (IOException e) {
            logger.warning("Problem while reading from the Calendar file. "
                    + "Will be starting with an empty Calendar");
            initialCalendar = new Calendar();
        }
        return initialCalendar;
    }

    /**
     * Refer to {@link #initModelManager(Storage, ReadOnlyUserPrefs)} for specifications
     * @param storage storage to be read from
     * @return {@link ReadOnlyTagTree}
     */
    private ReadOnlyTagTree tagTreeFromStorage(Storage storage) {
        Optional<ReadOnlyTagTree> tagTreeOptional;
        ReadOnlyTagTree initialTagTree;
        try {
            tagTreeOptional = storage.readTagTree();
            if (tagTreeOptional.isEmpty()) {
                logger.info("TagTree file not found. "
                        + "Will be starting with an empty TagTree");
            }
            initialTagTree = tagTreeOptional.orElse(new TagTreeImpl());
        } catch (DataConversionException e) {
            logger.warning("TagTree file not in the correct format. "
                    + "Will be starting with an empty TagTree");
            initialTagTree = new TagTreeImpl();
        } catch (IOException e) {
            logger.warning("Problem while reading from the TagTree file. "
                    + "Will be starting with an empty TagTree");
            initialTagTree = new TagTreeImpl();
        }
        return initialTagTree;
    }

    /**
     * Refer to {@link #initModelManager(Storage, ReadOnlyUserPrefs)} for specifications
     * @param storage storage to be read from
     * @return {@link ReadOnlyTagTree}
     */
    private ReadOnlyReminders remindersFromStorage(Storage storage) {
        Optional<ReadOnlyReminders> remindersOptional;
        ReadOnlyReminders initialReminders;
        try {
            remindersOptional = storage.readReminders();
            if (remindersOptional.isEmpty()) {
                logger.info("Reminders file not found. "
                        + "Will be starting with an empty Reminders storage");
            }
            initialReminders = remindersOptional.orElse(new RemindersImpl());
        } catch (DataConversionException e) {
            logger.warning("Reminders file not in the correct format. "
                    + "Will be starting with an empty Reminders storage");
            initialReminders = new RemindersImpl();
        } catch (IOException e) {
            logger.warning("Problem while reading from the TagTree file. "
                    + "Will be starting with an empty TagTree");
            initialReminders = new RemindersImpl();
        }
        return initialReminders;
    }

    // private methods for initModelManager ends


    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Athena " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Athena ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

}
