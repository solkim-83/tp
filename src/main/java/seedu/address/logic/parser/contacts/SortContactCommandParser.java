package seedu.address.logic.parser.contacts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.contacts.SortContactCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SortContactCommand} object
 */
public class SortContactCommandParser implements Parser<SortContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code SortContactCommand}
     * and returns a {@code SortContactCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortContactCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new SortContactCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContactCommand.MESSAGE_USAGE),
                    ive);
        }

    }
}
