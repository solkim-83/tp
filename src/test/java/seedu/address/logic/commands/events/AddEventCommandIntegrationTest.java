package seedu.address.logic.commands.events;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureEvent;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.association.FauxPerson;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManagerBuilder()
                .withAddressBook(getTypicalAddressBook())
                .withCalendar(getTypicalCalendar())
                .build();
    }

    @Test
    public void execute_newEvent_success() {
        int pos = 0;
        Person validPerson = model.getSortedFilteredPersonList().get(pos);
        Set<FauxPerson> attendees = new HashSet<>();
        attendees.add(new FauxPerson(validPerson));

        Event validEvent = new EventBuilder().withDescription("Meeting").withAttendees(attendees).build();

        Model expectedModel = new ModelManagerBuilder().withAddressBook(model.getAddressBook())
                .withCalendar(model.getCalendar()).build();
        expectedModel.addEvent(validEvent);

        ArrayList<Index> personsToAdd = new ArrayList<>();
        personsToAdd.add(Index.fromZeroBased(pos));
        AddEventCommand.AddEventDescriptor validEventDescriptor = new AddEventDescriptorBuilder()
                .withDescription(validEvent.getDescription())
                .withTime(validEvent.getTime())
                .withPersonsToAdd(personsToAdd).build();

        assertCommandSuccess(new AddEventCommand(validEventDescriptor), model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = model.getCalendar().getEventList().get(0);
        AddEventCommand.AddEventDescriptor eventInListDescriptor = new AddEventDescriptorBuilder()
                .withDescription(eventInList.getDescription())
                .withTime(eventInList.getTime()).build();
        assertCommandFailureEvent(new AddEventCommand(eventInListDescriptor),
                model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
