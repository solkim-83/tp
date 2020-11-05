package seedu.address.logic.parser.tags;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPERTAG_ONLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.tags.FindTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Parses input arguments and creates a new FindTagCommand object.
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of an FindagCommand and returns it for execution.
     * Looks for the name of the added tag under PREFIX_NAME, and looks for indices and tag parameters.
     * There must be at least one index or sub-tag specified.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        assert args != null;

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_SUPERTAG_ONLY);

        NameContainsKeywordsPredicate findPredicate = new NameContainsKeywordsPredicate();

        boolean hasKeywordInput = argumentMultimap.getValue(PREFIX_TAG).isPresent();
        if (hasKeywordInput) {
            List<String> trimmedKeywordsAsList = parseKeywordsField(argumentMultimap.getValue(PREFIX_TAG).get());
            findPredicate.setKeywords(trimmedKeywordsAsList);
        }

        boolean hasSuperTagInput = argumentMultimap.getValue(PREFIX_SUPERTAG_ONLY).isPresent();
        Optional<BooleanInput> showSuperTagOnly = hasSuperTagInput
                /*
                * it may seem intuitive to do
                * argumentMultimap.getValue(PREFIX_RECURSIVE).map(ParserUtil::parseBooleanInput)
                * but the exception would not be handled naturally
                */
                ? Optional.of(ParserUtil.parseBooleanInput(argumentMultimap.getValue(PREFIX_RECURSIVE).get()))
                : Optional.empty();

        if (!hasKeywordInput && !hasSuperTagInput) {
            throw new ParseException("placeholder");
        }

        return new FindTagCommand(findPredicate, showSuperTagOnly);

    }

    private List<String> parseKeywordsField(String keywords) {
        return Arrays.asList(keywords.trim().split("\\+"));
    }

}
