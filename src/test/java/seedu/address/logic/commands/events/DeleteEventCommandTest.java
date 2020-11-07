package seedu.address.logic.commands.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureEvent;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Event;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model = new ModelManagerBuilder().withCalendar(getTypicalCalendar()).build();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getSortedFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(indexes);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, "\n\n" + eventToDelete);

        ModelManager expectedModel = new ModelManagerBuilder().withCalendar(model.getCalendar()).build();
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedFilteredEventList().size() + 1);

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(outOfBoundIndex);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(indexes);

        assertCommandFailureEvent(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventToDelete = model.getSortedFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(indexes);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS, "\n\n" + eventToDelete);
        Model expectedModel = new ModelManagerBuilder().withCalendar(model.getCalendar()).build();
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of calendar list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCalendar().getEventList().size());

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(outOfBoundIndex);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(indexes);

        assertCommandFailureEvent(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> indexes1 = new ArrayList<>();
        indexes1.add(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteFirstCommand = new DeleteEventCommand(indexes1);
        ArrayList<Index> indexes2 = new ArrayList<>();
        indexes2.add(INDEX_SECOND_EVENT);
        DeleteEventCommand deleteSecondCommand = new DeleteEventCommand(indexes2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(indexes1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);

        assertTrue(model.getSortedFilteredEventList().isEmpty());
    }
}
