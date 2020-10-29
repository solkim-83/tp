package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path calendarFilePath = Paths.get("data" , "calendar.json");
    private Path tagTreeFilePath = Paths.get("data", "tagtree.json");
    private Path remindersFilePath = Paths.get("data", "reminders.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setCalendarFilePath(newUserPrefs.getCalendarFilePath());
        setTagTreeFilePath(newUserPrefs.getTagTreeFilePath());
        setRemindersFilePath(newUserPrefs.getRemindersFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getCalendarFilePath() {
        return calendarFilePath;
    }

    public void setCalendarFilePath(Path calendarFilePath) {
        requireNonNull(calendarFilePath);
        this.calendarFilePath = calendarFilePath;
    }

    public Path getTagTreeFilePath() {
        return tagTreeFilePath;
    }

    public void setTagTreeFilePath(Path tagTreeFilePath) {
        requireNonNull(tagTreeFilePath);
        this.tagTreeFilePath = tagTreeFilePath;
    }

    public Path getRemindersFilePath() {
        return remindersFilePath;
    }

    public void setRemindersFilePath(Path remindersFilePath) {
        requireNonNull(remindersFilePath);
        this.remindersFilePath = remindersFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath)
                && calendarFilePath.equals(o.calendarFilePath)
                && tagTreeFilePath.equals(o.tagTreeFilePath)
                && remindersFilePath.equals(o.remindersFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, calendarFilePath, tagTreeFilePath, remindersFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location for addressbook: " + addressBookFilePath);
        sb.append("\nLocal data file location for calendar: " + calendarFilePath);
        sb.append("\nLocal data file location for tagtree: " + tagTreeFilePath);
        sb.append("\nLocal data file location for reminders: " + remindersFilePath);
        return sb.toString();
    }

}
