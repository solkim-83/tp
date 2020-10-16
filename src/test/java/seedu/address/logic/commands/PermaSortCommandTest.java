//package seedu.address.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Person;
//
///**
// * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
// * {@code DeleteCommand}.
// */
//public class PermaSortCommandTest {
//
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//
////    @Test
////    public void execute__sortAddressBook_success() {
////
////    }
//
////    @Test
////    public void execute_invalidIndexSortAddressBook_throwsCommandException() {
////        PermaSortCommand permaSortCommand = new PermaSortCommand(Index.fromZeroBased(5));
////
////        assertCommandFailure(permaSortCommand, model, permaSortCommand.MESSAGE_INVALID_INDEX);
////    }
//
//}
