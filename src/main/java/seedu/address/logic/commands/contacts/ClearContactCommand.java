package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears Athena of Persons.
 */
public class ClearContactCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.CLEAR.toString();

    public static final String COMMAND_TYPE = CommandType.CONTACT.toString();

    public static final String MESSAGE_SUCCESS = "Athena's contact list has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
