package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureEvent;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminders.RemindEventCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ModelManagerBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains integration tests (interaction with the Model) for {@code RemindEventCommand}.
 */
public class RemindEventCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManagerBuilder().build();
    }

    @Test
    public void execute_newReminder_success() {
        int daysInAdvance = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm");
        String time = LocalDateTime.now().plusDays(daysInAdvance).format(formatter);
        Event validEvent = new EventBuilder().withDescription("Meeting")
                .withTime(time).build();

        Reminder testReminder = new Reminder(validEvent, daysInAdvance);
        model.addEvent(validEvent);

        Model expectedModel = new ModelManagerBuilder().build();
        expectedModel.addEvent(validEvent);
        expectedModel.addReminder(testReminder);

        RemindEventCommand remindEventCommand= new RemindEventCommand(Index.fromZeroBased(0), daysInAdvance);

        assertCommandSuccess(remindEventCommand, model,
                String.format(RemindEventCommand.MESSAGE_REMIND_EVENT_SUCCESS, daysInAdvance) + validEvent.toString(),
                expectedModel);
    }

    @Test
    public void execute_targetEventInvalidIndex_throwsCommandException() {
        RemindEventCommand remindEventCommand= new RemindEventCommand(Index.fromZeroBased(0), 0);
        assertCommandFailureEvent(remindEventCommand,
                model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

}
