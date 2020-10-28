package seedu.address.logic.commands.contacts;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelManagerBuilder;


/**
 * Contains unit tests for PermaSortCommand
 */
public class PermaSortContactCommandTest {

    private Model model = new ModelManagerBuilder().withAddressBook(getTypicalAddressBook()).build();

    @Test
    public void execute_validSortingOfInternalList_success() {
        PermaSortContactCommand permaSortContactCommand = new PermaSortContactCommand(Index.fromZeroBased(0));

        String expectedMessage = "Sorted by name in alphabetical order";

        ModelManager expectedModel = new ModelManagerBuilder().withAddressBook(model.getAddressBook()).build();
        expectedModel.permaSortContacts(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().fullName.compareToIgnoreCase(o2.getName().fullName);
            }
        });

        assertCommandSuccess(permaSortContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexSortAddressBook_throwsCommandException() {
        PermaSortContactCommand permaSortContactCommand = new PermaSortContactCommand(Index.fromZeroBased(5));

        assertCommandFailure(permaSortContactCommand, model, PermaSortContactCommand.MESSAGE_INVALID_INDEX);
    }

}
