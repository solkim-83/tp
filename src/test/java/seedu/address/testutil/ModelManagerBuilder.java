package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RemindersImpl;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.TagTreeImpl;

/**
 * A utility class to help with building ModelManager objects.
 * Example usage: <br>
 *     {@code ModelManager model = new ModelManagerBuilder().withAddressBook(new AddressBook()).build();}
 */
public class ModelManagerBuilder {

    private ModelManager model;

    /**
     * Constructs a model manager builder.
     */
    public ModelManagerBuilder() {
        model = new ModelManager(new AddressBook(), new Calendar(),
                new TagTreeImpl(), new UserPrefs(), new RemindersImpl());
    }

    public ModelManagerBuilder(ModelManager model) {
        this.model = model;
    }

    /**
     * Replaces the {@code AddressBook} in the {@code ModelManager} that we are building.
     */
    public ModelManagerBuilder withAddressBook(ReadOnlyAddressBook addressBook) {
        model.setAddressBook(addressBook);
        return this;
    }

    /**
     * Replaces the {@code Calendar} in the {@code ModelManager} that we are building.
     */
    public ModelManagerBuilder withCalendar(ReadOnlyCalendar calendar) {
        model.setCalendar(calendar);
        return this;
    }

    /**
     * Replaces the {@code RTagTree} in the {@code ModelManager} that we are building.
     */
    public ModelManagerBuilder withTagTree(ReadOnlyTagTree tagTree) {
        model.setTagTree(tagTree);
        return this;
    }

    /**
     * Replaces the {@code UserPrefs} in the {@code ModelManager} that we are building.
     */
    public ModelManagerBuilder withUserPrefs(ReadOnlyUserPrefs userPrefs) {
        model.setUserPrefs(userPrefs);
        return this;
    }

    public ModelManager build() {
        return model;
    }
}
