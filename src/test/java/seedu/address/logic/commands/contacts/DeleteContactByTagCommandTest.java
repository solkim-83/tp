package seedu.address.logic.commands.contacts;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteContactCommand}.
 */
public class DeleteContactByTagCommandTest {

    private Model model = new ModelManagerBuilder().withAddressBook(getTypicalAddressBook()).build();

    @Test
    public void execute_deleteContactByTag_success() {
        Tag tagForDeletion = new Tag("friends");
        DeleteContactByTagCommand deleteContactByTagCommand = new DeleteContactByTagCommand(tagForDeletion);

        String expectedMessage = DeleteContactByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS + tagForDeletion.tagName;


        ModelManager expectedModel = new ModelManagerBuilder().withAddressBook(model.getAddressBook()).build();
        Person personToDelete = model.getSortedFilteredPersonList().get(0);
        Person secondPersonToDelete = model.getSortedFilteredPersonList().get(1);
        Person thirdPersonToDelete = model.getSortedFilteredPersonList().get(3);
        expectedModel.deletePerson(personToDelete);
        expectedModel.deletePerson(secondPersonToDelete);
        expectedModel.deletePerson(thirdPersonToDelete);

        assertCommandSuccess(deleteContactByTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistantTag_throwsCommandException() {
        Tag nonExistantTag = new Tag("nonexistantTagthatshouldntbeinATypicalAddressbook");
        DeleteContactByTagCommand deleteContactByTagCommand = new DeleteContactByTagCommand(nonExistantTag);

        assertCommandFailure(deleteContactByTagCommand, model, deleteContactByTagCommand.MESSAGE_NON_EXISTENT_TAG);
    }

}
