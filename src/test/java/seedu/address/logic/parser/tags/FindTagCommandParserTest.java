package seedu.address.logic.parser.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPERTAG_ONLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.model.util.TagUtil.MESSAGE_INVALID_SEARCH_FIELD;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.tags.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.NameContainsKeywordsPredicate;

public class FindTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE);

    private static final NameContainsKeywordsPredicate PREDICATE_VALID = new NameContainsKeywordsPredicate();

    static {

        PREDICATE_VALID.setKeyword("tag");

    }

    private static final Optional<NameContainsKeywordsPredicate> KEYWORD_EMPTY = Optional.empty();
    private static final Optional<NameContainsKeywordsPredicate> KEYWORD_VALID = Optional.of(PREDICATE_VALID);
    private static final Optional<BooleanInput> SUPERTAG_EMPTY = Optional.empty();
    private static final Optional<BooleanInput> SUPERTAG_VALID = Optional.of(BooleanInput.isTrue());

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // missing both parts
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidTagInput_failure() {

        // invalid prefix used
        assertParseFailure(parser, " " + PREFIX_TAG + "@ #", MESSAGE_INVALID_SEARCH_FIELD);

    }

    @Test
    public void parse_invalidBooleanInput_failure() {

        // invalid BooleanInput used
        assertParseFailure(parser, " " + PREFIX_SUPERTAG_ONLY + "@ #", BooleanInput.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_validTagInputOnly_success() throws ParseException {

        FindTagCommand expectedCommand = new FindTagCommand(KEYWORD_VALID, SUPERTAG_EMPTY);
        assertEquals(expectedCommand, parser.parse("find -t t/tag"));

    }

    @Test
    public void parse_validSuperTagInputOnly_success() throws ParseException {

        FindTagCommand expectedCommand = new FindTagCommand(KEYWORD_EMPTY, SUPERTAG_VALID);
        assertEquals(expectedCommand, parser.parse("find -t st/1"));

    }

    @Test
    public void parse_validTagInputAndSuperTagInput_success() throws ParseException {

        FindTagCommand expectedCommand = new FindTagCommand(KEYWORD_VALID, SUPERTAG_VALID);
        assertEquals(expectedCommand, parser.parse("find -t t/tag st/1"));

    }

}
