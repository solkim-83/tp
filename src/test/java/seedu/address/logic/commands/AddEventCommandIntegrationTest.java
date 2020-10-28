package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureEvent;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.events.AddEventCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.tag.TagTreeImpl;
import seedu.address.testutil.AddEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), getTypicalCalendar(), new TagTreeImpl(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() {
        Event validEvent = new EventBuilder().withDescription("Meeting").build();

        Model expectedModel = new ModelManager(
                new AddressBook(), model.getCalendar(), new TagTreeImpl(), new UserPrefs());
        expectedModel.addEvent(validEvent);

        AddEventCommand.AddEventDescriptor validEventDescriptor = new AddEventDescriptorBuilder()
                .withDescription(validEvent.getDescription())
                .withTime(validEvent.getTime()).build();

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
