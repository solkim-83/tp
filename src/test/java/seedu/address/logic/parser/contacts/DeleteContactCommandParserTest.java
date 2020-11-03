package seedu.address.logic.parser.contacts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contacts.DeleteContactCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteContactCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteContactCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteContactCommandParserTest {

    private DeleteContactCommandParser parser = new DeleteContactCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new DeleteContactCommand(indexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE));
    }
}
