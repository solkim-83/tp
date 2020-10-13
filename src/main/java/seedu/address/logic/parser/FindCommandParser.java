package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContactContainsFieldsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        ContactContainsFieldsPredicate findPredicate = new ContactContainsFieldsPredicate();
        argMultimap.getValue(PREFIX_NAME).ifPresent(nameKeywords -> {
            findPredicate.setNameKeywords(Arrays.asList(nameKeywords.trim().split("\\s+")));
        });
        argMultimap.getValue(PREFIX_PHONE).ifPresent(phoneKeyword ->
                findPredicate.setPhoneKeyword(phoneKeyword.trim()));
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(emailKeyword ->
                findPredicate.setEmailKeyword(emailKeyword.trim()));
        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(addressKeyword ->
                findPredicate.setAddressKeyword(addressKeyword.trim()));
        findPredicate.setTags(argMultimap.getAllValues(PREFIX_TAG));

        if (findPredicate.isEmptyPredicate()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(findPredicate);
    }

}
