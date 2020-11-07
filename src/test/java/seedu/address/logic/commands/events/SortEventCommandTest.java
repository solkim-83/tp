package seedu.address.logic.commands.events;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comparators.EventComparator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SortEventCommand}.
 */
public class SortEventCommandTest {

    private Model model = new ModelManagerBuilder().withCalendar(getTypicalCalendar()).build();

    @Test
    public void execute_validSortingOfEventsByAlphabeticalOrder_success() {
        SortEventCommand sortEventCommand = new SortEventCommand(Index.fromZeroBased(0));

        String expectedMessage = "Sorted by description in alphabetical order";

        ModelManager expectedModel = new ModelManagerBuilder().withCalendar(model.getCalendar()).build();

        try {
            expectedModel.sortEvent(EventComparator.chooseComparator(Index.fromZeroBased(0)));
        } catch (CommandException ce) {
            throw new AssertionError("Choosing an event comparator should not fail.", ce);
        }

        assertCommandSuccess(sortEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validSortingOfEventsByChronologicalOrder_success() {
        SortEventCommand sortEventCommand = new SortEventCommand(Index.fromZeroBased(1));

        String expectedMessage = "Sorted by time in chronological order";

        ModelManager expectedModel = new ModelManagerBuilder().withCalendar(model.getCalendar()).build();

        try {
            expectedModel.sortEvent(EventComparator.chooseComparator(Index.fromZeroBased(1)));
        } catch (CommandException ce) {
            throw new AssertionError("Choosing an event comparator should not fail.", ce);
        }

        assertCommandSuccess(sortEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        SortEventCommand sortEventCommand = new SortEventCommand(Index.fromZeroBased(6));

        assertCommandFailure(sortEventCommand, model, "Index should be either 1 or 2!");
    }
}
