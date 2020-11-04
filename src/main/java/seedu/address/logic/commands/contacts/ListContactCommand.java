package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;

/**
 * Lists all persons in Athena to the user.
 */
public class ListContactCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.LIST.toString();

    public static final String COMMAND_TYPE = CommandType.CONTACT.toString();

    public static final String MESSAGE_SUCCESS = "Listed all contacts!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
