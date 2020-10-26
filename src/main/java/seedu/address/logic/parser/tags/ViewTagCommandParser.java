package seedu.address.logic.parser.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.parseTags;

import java.util.List;

import seedu.address.logic.commands.tags.ViewTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewTagCommand object
 */
public class ViewTagCommandParser implements Parser<ViewTagCommand> {

    @Override
    public ViewTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        List<String> tagStringSet = argumentMultimap.getAllValues(PREFIX_TAG);
        if (tagStringSet.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTagCommand.MESSAGE_USAGE));
        }

        return new ViewTagCommand(parseTags(argumentMultimap.getAllValues(PREFIX_TAG)));
    }



}
