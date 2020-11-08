package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.events.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Time;


/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details.
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setDescription(event.getDescription());
        descriptor.setTime(event.getTime());
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Sets the {@code personsToAdd} of the {@code EditEventDescriptor} that we are building.
     * Integer inputs will be directly converted and replace current personsToAdd. Any mistakes will carry over.
     */
    public EditEventDescriptorBuilder setPersonsToAdd(Integer... positions) {
        ArrayList<Index> indexes = new ArrayList<>();
        for (Integer position : positions) {
            indexes.add(Index.fromZeroBased(position));
        }
        descriptor.setPersonsToAdd(indexes);
        return this;
    }

    /**
     * Sets the {@code personsToRemove} of the {@code EditEventDescriptor} that we are building.
     * Integer inputs will be directly converted and replace current personsToRemove. Any mistakes will carry over.
     */
    public EditEventDescriptorBuilder setPersonsToRemove(Integer... positions) {
        ArrayList<Index> indexes = new ArrayList<>();
        for (Integer position : positions) {
            indexes.add(Index.fromZeroBased(position));
        }
        descriptor.setPersonsToRemove(indexes);
        return this;
    }

    /**
     * Sets the {@code wildCardAdd} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder setWildCardAdd() {
        descriptor.setWildCardAdd();
        return this;
    }

    /**
     * Sets the {@code wildCardRemove} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder setWildCardRemove() {
        descriptor.setWildCardRemove();
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
