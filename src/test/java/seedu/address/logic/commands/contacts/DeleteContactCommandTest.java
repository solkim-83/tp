package seedu.address.logic.commands.contacts;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteContactCommand}.
 */
public class DeleteContactCommandTest {

    private Model model = new ModelManagerBuilder().withAddressBook(getTypicalAddressBook()).build();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getSortedFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(indexes);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                "\n\n" + personToDelete);

        ModelManager expectedModel = new ModelManagerBuilder().withAddressBook(model.getAddressBook()).build();
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedFilteredPersonList().size() + 1);

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(outOfBoundIndex);
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(indexes);

        assertCommandFailure(deleteContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getSortedFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(indexes);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                "\n\n" + personToDelete);

        Model expectedModel = new ModelManagerBuilder().withAddressBook(model.getAddressBook()).build();
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(outOfBoundIndex);
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(indexes);

        assertCommandFailure(deleteContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> indexes1 = new ArrayList<>();
        indexes1.add(INDEX_FIRST_PERSON);
        DeleteContactCommand deleteFirstCommand = new DeleteContactCommand(indexes1);
        ArrayList<Index> indexes2 = new ArrayList<>();
        indexes2.add(INDEX_SECOND_PERSON);
        DeleteContactCommand deleteSecondCommand = new DeleteContactCommand(indexes2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteContactCommand deleteFirstCommandCopy = new DeleteContactCommand(indexes1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getSortedFilteredPersonList().isEmpty());
    }
}
