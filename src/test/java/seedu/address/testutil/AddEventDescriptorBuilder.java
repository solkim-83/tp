package seedu.address.testutil;

import seedu.address.logic.commands.events.AddEventCommand.AddEventDescriptor;
import seedu.address.model.event.Description;
import seedu.address.model.event.Time;

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

    public AddEventDescriptor build() {
        return addEventDescriptor;
    }
}
