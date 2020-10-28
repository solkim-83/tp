package seedu.address.logic.commands.contacts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.contacts.FindContactCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsFieldsPredicate;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindContactCommand}.
 */
public class FindContactCommandTest {
    private Model model = new ModelManagerBuilder().withAddressBook(getTypicalAddressBook()).build();
    private Model expectedModel = new ModelManagerBuilder().withAddressBook(getTypicalAddressBook()).build();

    @Test
    public void equals() {
        ContactContainsFieldsPredicate firstPredicate = new ContactContainsFieldsPredicate();
        firstPredicate.setNameKeywords(Collections.singletonList("first"));

        ContactContainsFieldsPredicate secondPredicate = new ContactContainsFieldsPredicate();
        secondPredicate.setNameKeywords(Collections.singletonList("second"));

        FindContactCommand findFirstCommand = new FindContactCommand(firstPredicate);
        FindContactCommand findSecondCommand = new FindContactCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindContactCommand findFirstCommandCopy = new FindContactCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test

    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindContactCommand command = new FindContactCommandParser().parse(" n/Kurz Elle Kunz");
        expectedModel.updateFilteredPersonList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getSortedFilteredPersonList());
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        command = new FindContactCommandParser().parse(" e/example");
        expectedModel.updateFilteredPersonList(command.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(getTypicalPersons(), model.getSortedFilteredPersonList());
    }

}
