package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.events.AddEventCommand.AddEventDescriptor;
import seedu.address.model.event.Description;
import seedu.address.model.event.Time;

/**
 * A utility class to help with building AddEventDescriptor objects.
 */
public class AddEventDescriptorBuilder {

    private AddEventDescriptor addEventDescriptor = new AddEventDescriptor();

    /**
     * Sets the {@code Description} of the {@code AddEventDescriptor} that we are building.
     */
    public AddEventDescriptorBuilder withDescription(Description description) {
        addEventDescriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code AddEventDescriptor} that we are building.
     */
    public AddEventDescriptorBuilder withTime(Time time) {
        addEventDescriptor.setTime(time);
        return this;
    }

    /**
     * Sets the {@code personsToAdd} of the {@code AddEventDescriptor} that we are building.
     */
    public AddEventDescriptorBuilder withPersonsToAdd(ArrayList<Index> personsToAdd) {
        addEventDescriptor.setPersonsToAdd(personsToAdd);
        return this;
    }

    /**
     * Sets the {@code wildCardAdd} of the {@code AddEventDescriptor} that we are building.
     */
    public AddEventDescriptorBuilder setWildCardAdd() {
        addEventDescriptor.setWildCardAdd();
        return this;
    }


    public AddEventDescriptor build() {
        return addEventDescriptor;
    }
}
