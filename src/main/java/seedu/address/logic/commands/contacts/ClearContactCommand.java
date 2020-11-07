package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
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
        // This implementation is inefficient but it will remove all persons from both the contact and event list.
        // model.deletePerson removes the person from any event they attend
        ObservableList<Person> personObservableList = FXCollections.observableArrayList();
        // make a copy of the list as the direct list from getPersonList is affected by deletePerson
        personObservableList.setAll(model.getAddressBook().getPersonList());
        for (Person person : personObservableList) {
            model.deletePerson(person);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
