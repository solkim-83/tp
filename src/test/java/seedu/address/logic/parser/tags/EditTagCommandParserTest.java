package seedu.address.logic.parser.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tags.EditTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


public class EditTagCommandParserTest {

    private static final String TAG_NAME_VALID = " n/testtag";
    private static final Tag TAG_VALID = new Tag("testtag");
    private static final String TAG_NAME_INVALID = "n/   ";

    private static final String INDEX_STRING_ONE = " i/1";
    private static final String INDEX_STRING_TWO = " i/2";
    private static final Set<Index> INDEX_SET = Set.of(Index.fromOneBased(1), Index.fromOneBased(2));

    private static final String REMOVE_INDEX_STRING_ONE = " ri/1";
    private static final String REMOVE_INDEX_STRING_TWO = " ri/2";
    private static final String REMOVE_INDEX_STRING_INVALID = " ri/c";
    private static final Set<Index> REMOVE_INDEX_SET = Set.of(Index.fromOneBased(1), Index.fromOneBased(2));

    private static final String TAG_STRING_CS2030 = " t/cs2030";
    private static final String TAG_STRING_CS2040 = " t/cs2040";
    private static final String TAG_STRING_EMPTY = " t/   ";
    private static final Set<Tag> TAG_SET = Set.of(new Tag("cs2030"), new Tag("cs2040"));

    private static final String REMOVE_TAG_STRING_CS2030 = " rt/cs2030";
    private static final String REMOVE_TAG_STRING_CS2040 = " rt/cs2040";
    private static final String REMOVE_TAG_STRING_INVALID = " rt/+-*/";
    private static final Set<Tag> REMOVE_TAG_SET = Set.of(new Tag("cs2030"), new Tag("cs2040"));

    private EditTagCommandParser parser = new EditTagCommandParser();

    @Test
    public void parse_missingName_throwParseException() {
        assertThrows(ParseException.class, () -> parser.parse(REMOVE_TAG_STRING_CS2030 + INDEX_STRING_ONE));
        assertThrows(ParseException.class, () -> parser.parse(REMOVE_TAG_STRING_CS2030 + TAG_NAME_INVALID
                + INDEX_STRING_ONE));
    }

    @Test
    public void parse_noEditArguments_throwParseException() {
        assertThrows(ParseException.class, () -> parser.parse(TAG_NAME_VALID));
        assertThrows(ParseException.class, () -> parser.parse(TAG_NAME_VALID + TAG_STRING_EMPTY));
    }

    @Test
    public void parse_invalidEditArgumentFormat_throwParseException() {
        assertThrows(ParseException.class, () -> parser.parse(TAG_NAME_VALID + REMOVE_INDEX_STRING_INVALID));
        assertThrows(ParseException.class, () -> parser.parse(TAG_NAME_VALID + TAG_STRING_CS2030
                + REMOVE_TAG_STRING_INVALID));
    }

    @Test
    public void parse_validArguments_success() throws ParseException {
        EditTagCommand expectedCommand = new EditTagCommand(TAG_VALID, INDEX_SET, Set.of(), TAG_SET, Set.of());
        EditTagCommand actualCommand = parser.parse(TAG_NAME_VALID + INDEX_STRING_ONE + INDEX_STRING_TWO
                + TAG_STRING_CS2030 + TAG_STRING_CS2040);
        assertEquals(expectedCommand, actualCommand);

        expectedCommand = new EditTagCommand(TAG_VALID, INDEX_SET, REMOVE_INDEX_SET, TAG_SET, REMOVE_TAG_SET);
        assertEquals(expectedCommand, parser.parse(TAG_NAME_VALID + REMOVE_TAG_STRING_CS2030 + TAG_STRING_CS2040
                + REMOVE_TAG_STRING_CS2040 + INDEX_STRING_TWO + INDEX_STRING_ONE + REMOVE_INDEX_STRING_ONE
                + REMOVE_INDEX_STRING_TWO + TAG_STRING_CS2030));
    }

}
