package seedu.address.testutil;

import seedu.address.logic.commands.events.AddEventCommand.AddEventDescriptor;
import seedu.address.model.event.Description;
import seedu.address.model.event.Time;

public class AddEventDescriptorBuilder {

    AddEventDescriptor addEventDescriptor = new AddEventDescriptor();

    public AddEventDescriptorBuilder withDescription(Description description) {
        addEventDescriptor.setDescription(description);
        return this;
    }

    public AddEventDescriptorBuilder withTime(Time time) {
        addEventDescriptor.setTime(time);
        return this;
    }

    public AddEventDescriptor build() {
        return addEventDescriptor;
    }
}
