package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from Athena.
 */
public class DeleteContactCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.DELETE.toString();

    public static final String COMMAND_TYPE = CommandType.CONTACT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n\n"
            + "Parameters: CONTACT_INDEX_LIST (must be positive integer(s))\n\n"
            + "Examples:\n"
            + COMMAND_WORD + " " + COMMAND_TYPE + " 1" + "\n"
            + COMMAND_WORD + " " + COMMAND_TYPE + " 1,2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted contact(s): %1$s";

    private final ArrayList<Index> targetIndexes;

    public DeleteContactCommand(ArrayList<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getSortedFilteredPersonList();

        StringBuilder personsStringBuilder = new StringBuilder();

        for (Index targetIndexes : targetIndexes) {
            if (targetIndexes.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToDelete = lastShownList.get(targetIndexes.getZeroBased());
            model.deletePerson(personToDelete);
            personsStringBuilder.append("\n\n" + personToDelete);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personsStringBuilder.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteContactCommand // instanceof handles nulls
                && targetIndexes.equals(((DeleteContactCommand) other).targetIndexes)); // state check
    }
}
