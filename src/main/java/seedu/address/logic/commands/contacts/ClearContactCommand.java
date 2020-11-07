package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

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
        // This implementation is inefficient but it will remove said person from both the contact and event list.
        // model.deletePerson removes the person from any event they attend
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        for (Person person : addressBook.getPersonList()) {
            model.deletePerson(person);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
