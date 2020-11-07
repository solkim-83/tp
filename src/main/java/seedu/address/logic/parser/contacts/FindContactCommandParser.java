package seedu.address.logic.parser.contacts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.contacts.FindContactCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContactContainsFieldsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindContactCommand object
 */
public class FindContactCommandParser implements Parser<FindContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindContactCommand
     * and returns a FindContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        ContactContainsFieldsPredicate findPredicate = new ContactContainsFieldsPredicate();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedNameKeywords = parseNonTagPredicateField(argMultimap.getValue(PREFIX_NAME).get());
            findPredicate.setNameKeywords(Arrays.asList(trimmedNameKeywords.split("\\s+")));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String trimmedPhoneKeyword = parseNonTagPredicateField(argMultimap.getValue(PREFIX_PHONE).get());
            findPredicate.setPhoneKeyword(trimmedPhoneKeyword);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String trimmedEmailKeyword = parseNonTagPredicateField(argMultimap.getValue(PREFIX_EMAIL).get());
            findPredicate.setEmailKeyword(trimmedEmailKeyword);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String trimmedAddressKeyword = parseNonTagPredicateField(argMultimap.getValue(PREFIX_ADDRESS).get());
            findPredicate.setAddressKeyword(trimmedAddressKeyword);
        }
        findPredicate.setTagKeywords(parseTagPredicateFields(argMultimap.getAllValues(PREFIX_TAG)));

        if (findPredicate.isEmptyPredicate()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
        }

        return new FindContactCommand(findPredicate);
    }

    /**
     * Returns a trimmed predicateField. Throws a ParseException if the field is invalid.
     */
    private String parseNonTagPredicateField(String predicateField) throws ParseException {
        if (!ContactContainsFieldsPredicate.isValidPredicateField(predicateField)) {
            throw new ParseException(ContactContainsFieldsPredicate.NON_TAG_CONSTRAINTS);
        } else {
            return predicateField.trim();
        }
    }

    /**
     * Returns the same list of tags. Throws a ParseException if any of the tags violate Tag requirements.
     */
    private List<String> parseTagPredicateFields(List<String> tagList) throws ParseException {
        for (String tagString : tagList) {
            if (!Tag.isValidTagName(tagString)) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }
        }
        return tagList;
    }

}
