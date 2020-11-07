package seedu.address.logic.parser.events;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.events.ViewEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class ViewEventCommandParser implements Parser<ViewEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewEventCommand
     * and returns a ViewEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ViewEventCommand parse(String args) throws ParseException {
        try {
            Index targetIndex = ParserUtil.parseIndex(args);
            return new ViewEventCommand(targetIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
