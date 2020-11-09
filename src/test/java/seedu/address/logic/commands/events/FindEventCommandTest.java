package seedu.address.logic.commands.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.event.DescriptionContainsKeywordsPredicate;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManagerBuilder().withCalendar(getTypicalCalendar()).build();
    private Model expectedModel = new ModelManagerBuilder().withCalendar(getTypicalCalendar()).build();

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate = new DescriptionContainsKeywordsPredicate();
        firstPredicate.setKeywords(Collections.singletonList("first"));

        DescriptionContainsKeywordsPredicate secondPredicate = new DescriptionContainsKeywordsPredicate();
        secondPredicate.setKeywords(Collections.singletonList("second"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

}
