package seedu.address.logic.commands;

import seedu.address.model.Model;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.commons.core.index.Index;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the currently displayed list"
            + "by the index command entered "
            + "1 will be sort by date added\n"
            + "2 will be sort by alphabetical order of their names\n"
            + "3 will be sort by alphabetical order of their address\n"
            + "4 will be sort by alphabetical order of their first tag\n"
            + "Parameters: INDEX (must be between 1 and 4) "
            + PREFIX_SORT + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SORT + "Likes to swim.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to edit the remark
     */
    public SortCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand e = (SortCommand) other;
        return index.equals(e.index);
    }
}