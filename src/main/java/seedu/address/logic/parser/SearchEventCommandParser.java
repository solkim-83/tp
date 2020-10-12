package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DescriptionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchEventCommand object
 */
public class SearchEventCommandParser implements Parser<SearchEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchEventCommand
     * and returns a SearchEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchEventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchEventCommand.MESSAGE_USAGE));
        }

        String[] eventKeywords = trimmedArgs.split("\\s+");

        return new SearchEventCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList(eventKeywords)));
    }

}
