package seedu.address.logic.parser.tags;


import org.junit.jupiter.api.Test;
import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.tags.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteTagCommandParserTest {

    private static final String BOOLEAN_STRING_INVALID = " r/not valid";
    private static final String BOOLEAN_STRING_TRUE = " r/true";
    private static final String BOOLEAN_STRING_FALSE = " r/false";

    private static final BooleanInput BOOLEAN_TRUE = BooleanInput.isTrue();
    private static final BooleanInput BOOLEAN_FALSE = BooleanInput.isFalse();

    private static final String TAG_STRING_VALID = " t/validtag";
    private static final Tag TAG_VALID = new Tag(TAG_STRING_VALID);
    private static final String TAG_STRING_INVALID = " t/invalid tag";

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validInputs_success() throws ParseException {
        DeleteTagCommand expectedCommand = new DeleteTagCommand(TAG_VALID, BOOLEAN_TRUE);
        assertEquals(expectedCommand, parser.parse(TAG_STRING_VALID + BOOLEAN_STRING_TRUE));

        expectedCommand = new DeleteTagCommand(TAG_VALID, BOOLEAN_FALSE);
        assertEquals(expectedCommand, parser.parse(TAG_STRING_VALID +  BOOLEAN_STRING_FALSE));
        // No recursive boolean input given
        assertEquals(expectedCommand, parser.parse(TAG_STRING_VALID));
    }

    @Test
    public void parse_invalidInputs_parseExceptionThrown() {
        // Invalid tag
        assertThrows(ParseException.class, () ->
                parser.parse(TAG_STRING_INVALID + BOOLEAN_STRING_TRUE));

        // Missing tag
        assertThrows(ParseException.class, () ->
                parser.parse(BOOLEAN_STRING_TRUE));
    }



}