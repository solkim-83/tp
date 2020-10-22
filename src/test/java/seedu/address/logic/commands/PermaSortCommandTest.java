package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class PermaSortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexSortAddressBook_throwsCommandException() {
        PermaSortContactCommand permaSortContactCommand = new PermaSortContactCommand(Index.fromZeroBased(5));

        assertCommandFailure(permaSortContactCommand, model, PermaSortContactCommand.MESSAGE_INVALID_INDEX);
    }

}
