package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureEvent;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.events.AddEventCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManagerBuilder().withCalendar(getTypicalCalendar()).build();
    }

    @Test
    public void execute_newEvent_success() {
        Event validEvent = new EventBuilder().withDescription("Meeting").build();

        Model expectedModel = new ModelManagerBuilder().withCalendar(model.getCalendar()).build();
        expectedModel.addEvent(validEvent);

        assertCommandSuccess(new AddEventCommand(validEvent), model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event eventInList = model.getCalendar().getEventList().get(0);
        assertCommandFailureEvent(new AddEventCommand(eventInList), model, AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
