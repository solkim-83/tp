package seedu.address.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ModelStub;


public class AddEventCommandTest {

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        AddEventCommand.AddEventDescriptor validEventDescriptor = new AddEventDescriptorBuilder()
                .withDescription(validEvent.getDescription())
                .withTime(validEvent.getTime())
                .setWildCardAdd().build();

        CommandResult commandResult = new AddEventCommand(validEventDescriptor).execute(modelStub);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new EventBuilder().build();
        AddEventCommand.AddEventDescriptor validEventDescriptor = new AddEventDescriptorBuilder()
                .withDescription(validEvent.getDescription())
                .withTime(validEvent.getTime())
                .setWildCardAdd().build();

        AddEventCommand addEventCommand = new AddEventCommand(validEventDescriptor);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class, AddEventCommand.MESSAGE_DUPLICATE_EVENT, () ->
                addEventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Event meeting = new EventBuilder().withDescription("Meeting").build();
        Event consultation = new EventBuilder().withDescription("Consultation").build();

        AddEventCommand.AddEventDescriptor meetingDescriptor = new AddEventDescriptorBuilder()
                .withDescription(meeting.getDescription())
                .withTime(meeting.getTime())
                .setWildCardAdd().build();
        AddEventCommand.AddEventDescriptor consultationDescriptor = new AddEventDescriptorBuilder()
                .withDescription(consultation.getDescription())
                .withTime(consultation.getTime())
                .setWildCardAdd().build();

        AddEventCommand addMeetingCommand = new AddEventCommand(meetingDescriptor);
        AddEventCommand addConsultationCommand = new AddEventCommand(consultationDescriptor);

        // same object -> returns true
        assertTrue(addMeetingCommand.equals(addMeetingCommand));

        // same values -> returns true
        AddEventCommand addMeetingCommandCopy = new AddEventCommand(meetingDescriptor);
        assertTrue(addMeetingCommand.equals(addMeetingCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand.equals(null));

        // different event -> returns false
        assertFalse(addMeetingCommand.equals(addConsultationCommand));
    }

    /**
     * A Model stub that contains a single event.
     * Contains a empty sortedPersons list to enable {@code getSortedFilteredPersonList()} as add event will use it.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;
        private final SortedList<Person> sortedPersons = new SortedList<>(
                new FilteredList<>(FXCollections.unmodifiableObservableList(FXCollections.observableArrayList())));

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public ObservableList<Person> getSortedFilteredPersonList() {
            return sortedPersons;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     * Contains a empty sortedPersons list to enable {@code getSortedFilteredPersonList()} as add event will use it.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        private final ArrayList<Event> eventsAdded = new ArrayList<>();
        private final SortedList<Person> sortedPersons = new SortedList<>(
                new FilteredList<>(FXCollections.unmodifiableObservableList(FXCollections.observableArrayList())));

        @Override
        public ObservableList<Person> getSortedFilteredPersonList() {
            return sortedPersons;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public boolean hasClashingEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::clash);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
