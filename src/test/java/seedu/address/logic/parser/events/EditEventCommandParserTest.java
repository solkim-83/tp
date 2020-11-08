package seedu.address.logic.parser.events;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADD_PERSON_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.ADD_PERSON_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.ADD_PERSON_WILD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADD_PERSON_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADD_PERSON_WILD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMOVE_PERSON_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMOVE_PERSON_WILD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REMOVE_PERSON_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.REMOVE_PERSON_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.REMOVE_PERSON_WILD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_LUNCH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.events.EditEventCommand;
import seedu.address.logic.commands.events.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Time;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION_LUNCH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DESCRIPTION_DESC_LUNCH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DESCRIPTION_DESC_LUNCH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_TIME_DESC,
                Time.MESSAGE_CONSTRAINTS); // invalid time
        assertParseFailure(parser, "1" + INVALID_ADD_PERSON_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX); // invalid add person
        assertParseFailure(parser, "1" + INVALID_ADD_PERSON_WILD_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX); // invalid add person wild
        assertParseFailure(parser, "1" + INVALID_REMOVE_PERSON_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX); // invalid remove person
        assertParseFailure(parser, "1" + INVALID_REMOVE_PERSON_WILD_DESC,
                ParserUtil.MESSAGE_INVALID_INDEX); // invalid remove person wild

        // invalid description followed by valid time
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + TIME_DESC_LUNCH,
                Description.MESSAGE_CONSTRAINTS);

        // valid time followed by invalid time. The test case for invalid time followed by valid time
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TIME_DESC_LUNCH + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_LUNCH + TIME_DESC_LUNCH + ADD_PERSON_DESC_1
                + REMOVE_PERSON_DESC_2;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_LUNCH)
                .withTime(VALID_TIME_LUNCH).setPersonsToAdd(3, 2, 0).setPersonsToRemove(3, 2, 1, 0).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + TIME_DESC_LUNCH + ADD_PERSON_DESC_1;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTime(VALID_TIME_LUNCH)
                .setPersonsToAdd(3, 2, 0).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_EVENT;
        // description
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_BREAKFAST;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_BREAKFAST).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + TIME_DESC_BREAKFAST;
        descriptor = new EditEventDescriptorBuilder().withTime(VALID_TIME_BREAKFAST).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // add person
        userInput = targetIndex.getOneBased() + ADD_PERSON_DESC_1;
        descriptor = new EditEventDescriptorBuilder().setPersonsToAdd(3, 2, 0).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remove person
        userInput = targetIndex.getOneBased() + REMOVE_PERSON_DESC_1;
        descriptor = new EditEventDescriptorBuilder().setPersonsToRemove(3, 2, 0).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // add person wild
        userInput = targetIndex.getOneBased() + ADD_PERSON_WILD_DESC;
        descriptor = new EditEventDescriptorBuilder().setWildCardAdd().build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remove person wild
        userInput = targetIndex.getOneBased() + REMOVE_PERSON_WILD_DESC;
        descriptor = new EditEventDescriptorBuilder().setWildCardRemove().build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws ParseException {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_BREAKFAST + TIME_DESC_BREAKFAST
                + ADD_PERSON_DESC_1 + REMOVE_PERSON_DESC_1 + DESCRIPTION_DESC_LUNCH + TIME_DESC_LUNCH
                + ADD_PERSON_DESC_2 + REMOVE_PERSON_DESC_2;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_LUNCH).withTime(VALID_TIME_LUNCH)
                .setPersonsToAdd(3, 2, 1, 0).setPersonsToRemove(3, 2, 1, 0).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_TIME_DESC + TIME_DESC_LUNCH;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTime(VALID_TIME_LUNCH).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_TIME_DESC + DESCRIPTION_DESC_LUNCH + TIME_DESC_LUNCH;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_LUNCH)
                .withTime(VALID_TIME_LUNCH).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
