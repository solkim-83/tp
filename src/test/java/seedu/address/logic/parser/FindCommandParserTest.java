package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContactContainsFieldsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ContactContainsFieldsPredicate predicate = new ContactContainsFieldsPredicate();
        predicate.setNameKeywords(Arrays.asList("Alice", "Bob"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        ContactContainsFieldsPredicate predicate = new ContactContainsFieldsPredicate();
        predicate.setPhoneKeyword("91234");
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " " + PREFIX_PHONE + "91234", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_PHONE + "\n \t 91234  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ContactContainsFieldsPredicate predicate = new ContactContainsFieldsPredicate();
        predicate.setTagKeywords(List.of("CS2103", "CS2030"));
        FindCommand expectedFindCommand = new FindCommand(predicate);
        assertParseSuccess(parser, " " + PREFIX_TAG + "CS2103 " + PREFIX_TAG + "CS2030", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_TAG + " \n \t CS2103  \t " + PREFIX_TAG + " \nCS2030",
                expectedFindCommand);
    }

    @Test
    public void parse_multipleValidArgs_returnsFindCommand() {
        ContactContainsFieldsPredicate predicate = new ContactContainsFieldsPredicate();
        predicate.setNameKeywords(List.of("Alice", "Bob"));
        predicate.setEmailKeyword("hotmail");
        predicate.setPhoneKeyword("91234");
        String parseString = " " + PREFIX_NAME + "Alice Bob "
                + PREFIX_EMAIL + "hotmail "
                + PREFIX_PHONE + "91234";

        assertParseSuccess(parser, parseString, new FindCommand(predicate));
    }

    @Test
    public void parse_oneEmptyArg_throwsParseException() {
        assertParseFailure(parser, " n/", ContactContainsFieldsPredicate.NON_TAG_CONSTRAINTS);
        assertParseFailure(parser, " n/Alex Bob p/91234 e/",
                ContactContainsFieldsPredicate.NON_TAG_CONSTRAINTS);
        assertParseFailure(parser, " t/CS2103 t/", Tag.MESSAGE_CONSTRAINTS);
    }

}
