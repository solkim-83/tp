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

        Optional<NameContainsKeywordsPredicate> findPredicate = Optional.empty();
        Optional<BooleanInput> showSuperTagOnly = Optional.empty();

        boolean hasKeywordInput = argumentMultimap.getValue(PREFIX_TAG).isPresent();
        if (hasKeywordInput) {
            String trimmedKeyword = parseKeywordsField(argumentMultimap.getValue(PREFIX_TAG).get());
            NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate();
            predicate.setKeyword(trimmedKeyword);
            findPredicate = Optional.of(predicate);
        }

        boolean hasSuperTagInput = argumentMultimap.getValue(PREFIX_SUPERTAG_ONLY).isPresent();
        if (hasSuperTagInput) {
            showSuperTagOnly = Optional.of(
                    ParserUtil.parseBooleanInput(argumentMultimap.getValue(PREFIX_SUPERTAG_ONLY).get()));
        }

        if (!hasKeywordInput && !hasSuperTagInput) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        return new FindTagCommand(findPredicate, showSuperTagOnly);

    }

    private String parseKeywordsField(String keyword) throws ParseException {
        if (!NameContainsKeywordsPredicate.isValidKeyword(keyword)) {
            throw new ParseException(NameContainsKeywordsPredicate.NON_TAG_CONSTRAINTS);
        } else {
            return keyword.trim();
        }
    }

}
