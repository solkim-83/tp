package seedu.address.logic.parser.events;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.events.FindEventCommand;
import seedu.address.model.event.DescriptionContainsKeywordsPredicate;

public class FindEventCommandParserTest {

    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validDescription_returnsFindCommand() {
        // no leading and trailing whitespaces
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate();
        predicate.setKeywords(Arrays.asList("CS2103", "Meeting"));
        FindEventCommand expectedFindEventCommand = new FindEventCommand(predicate);
        assertParseSuccess(parser, "CS2103 Meeting", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2103 \n \t Meeting  \t", expectedFindEventCommand);
    }
}
