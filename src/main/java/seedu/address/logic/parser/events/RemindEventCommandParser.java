package seedu.address.logic.parser.events;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMIND_IN;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.reminders.RemindEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new RemindEventCommand object
 */
public class RemindEventCommandParser implements Parser<RemindEventCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the RemindEventCommand
     * and returns an RemindEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemindEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMIND_IN);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMIND_IN)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindEventCommand.MESSAGE_FAILURE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemindEventCommand.MESSAGE_USAGE), ive);
        }

        int daysInAdvance = Integer.parseInt(argMultimap.getValue(PREFIX_REMIND_IN).orElse("-1"));

        return new RemindEventCommand(index, daysInAdvance);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
