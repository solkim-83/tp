package seedu.address.logic.parser.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.tags.ViewTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class ViewTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTagCommand.MESSAGE_USAGE);

    private ViewTagCommandParser parser = new ViewTagCommandParser();

    @Test
    public void parse_missingParts_failure() {

        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 e/" + VALID_EMAIL_AMY, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, " \n   \t", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTag_failure() {
        assertParseFailure(parser, " " + PREFIX_TAG + "@ #", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validMessage_success() throws ParseException {
        String input = TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        Set<Tag> setToSearch = Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND));
        ViewTagCommand expectedCommand = new ViewTagCommand(setToSearch);
        assertEquals(expectedCommand, parser.parse(input));

    }


}
