package seedu.address.logic.commands.tags;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;

/**
 * Finds and lists all tags in the system-display that contain any of the keywords used in the search.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.FIND.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    @Override
    public CommandResult execute(Model model) {

        return new CommandResult(String.format(""));

    }

}
