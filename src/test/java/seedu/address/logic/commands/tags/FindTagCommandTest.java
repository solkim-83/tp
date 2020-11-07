package seedu.address.logic.commands.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ContactTagIntegrationManagerTest.PERSON_CS1231S_1;
import static seedu.address.model.ContactTagIntegrationManagerTest.PERSON_CS1231S_2;
import static seedu.address.model.ContactTagIntegrationManagerTest.buildTestIntegrationAddressBook;
import static seedu.address.model.util.TagUtil.INDICATOR_SUPERTAG;
import static seedu.address.model.util.TagUtil.parsePersonSetIntoString;
import static seedu.address.testutil.TagTreeUtil.TAG_ARCHITECTURE;
import static seedu.address.testutil.TagTreeUtil.TAG_ARCHITECTURE_MOD;
import static seedu.address.testutil.TagTreeUtil.TAG_COMPUTING;
import static seedu.address.testutil.TagTreeUtil.TAG_NUS;
import static seedu.address.testutil.TagTreeUtil.buildTestTree;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.tags.FindTagCommandParser;
import seedu.address.model.Model;
import seedu.address.model.tag.NameContainsKeywordsPredicate;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {

    private static final String STRING_IF_EMPTY = "empty string rep";

    public static Model createTestModel() {
        return new ModelManagerBuilder()
                .withAddressBook(buildTestIntegrationAddressBook())
                .withTagTree(buildTestTree()).build();
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate();
        firstPredicate.setKeyword("first");

        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate();
        secondPredicate.setKeyword("second");

        BooleanInput trueInput = BooleanInput.isTrue();

        FindTagCommand findFirstCommand = new FindTagCommand(Optional.of(firstPredicate), Optional.of(trueInput));
        FindTagCommand findSecondCommand = new FindTagCommand(Optional.of(secondPredicate), Optional.of(trueInput));

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(Optional.of(firstPredicate), Optional.of(trueInput));
        assertEquals(findFirstCommandCopy, findFirstCommand);

        // different types -> returns false
        assertNotEquals(findFirstCommand, 1);

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different tag -> returns false
        assertNotEquals(findSecondCommand, findFirstCommand);
    }

    @Test
    public void parsePersonSetIntoString_validSet_success() {
        String message = parsePersonSetIntoString(Set.of(PERSON_CS1231S_1, PERSON_CS1231S_2),
                STRING_IF_EMPTY);
        assertTrue(message.contains(PERSON_CS1231S_1.getName().toString())
                && message.contains(PERSON_CS1231S_2.getName().toString()));
    }

    @Test
    public void parsePersonSetIntoString_emptySet_emptyStringReturned() {
        assertEquals(STRING_IF_EMPTY, parsePersonSetIntoString(Set.of(), STRING_IF_EMPTY));
    }

    @Test
    public void constructUnfilteredTagSummaryMessage_success() {
        Model model = createTestModel();
        FindTagCommand command = new FindTagCommand(Optional.empty(), Optional.empty());
        String message = command.constructFilteredTagSummaryMessage(model, model.getPersonTags(), model.getSuperTags());

        // Check that supertag identification is shown
        assertTrue(message.contains(TAG_NUS + INDICATOR_SUPERTAG));

        // Check that persons are shown
        assertTrue(message.contains(parsePersonSetIntoString(model.getPersonsWithTag(TAG_COMPUTING), STRING_IF_EMPTY)));
    }

    @Test
    public void findTagByFullMatchOnly_success() throws ParseException {
        Model model = createTestModel();
        FindTagCommand command = new FindTagCommandParser().parse(" t/architecturemod");
        String message = command.constructFilteredTagSummaryMessage(model, model.getPersonTags(), model.getSuperTags());

        // Check that tag is found in the message
        assertTrue(message.contains(TAG_ARCHITECTURE_MOD.toString()));

    }

    @Test
    public void findTagByPartialMatchOnly_success() throws ParseException {
        Model model = createTestModel();
        FindTagCommand command = new FindTagCommandParser().parse(" t/architecture");
        String message = command.constructFilteredTagSummaryMessage(model, model.getPersonTags(), model.getSuperTags());

        // Check that tag is found in the message
        assertTrue(message.contains(TAG_ARCHITECTURE.toString()));

        // Check that partially matched tag is found in the message
        assertTrue(message.contains(TAG_ARCHITECTURE_MOD.toString()));

    }

    @Test
    public void findTagBySuperTagFilterOnly_success() throws ParseException {
        Model model = createTestModel();
        FindTagCommand command = new FindTagCommandParser().parse(" st/1");
        String message = command.constructFilteredTagSummaryMessage(model, model.getPersonTags(), model.getSuperTags());

        // Check that super tags are found in the message
        assertTrue(message.contains(TAG_NUS.toString()));

        // Check that regular tags are not found in the message
        assertFalse(message.contains(TAG_ARCHITECTURE_MOD.toString()));
    }

    @Test
    public void findTagByRegularTagFilterOnly_success() throws ParseException {
        Model model = createTestModel();
        FindTagCommand command = new FindTagCommandParser().parse(" st/0");
        String message = command.constructFilteredTagSummaryMessage(model, model.getPersonTags(), model.getSuperTags());

        // Check that super tags are not found in the message
        assertFalse(message.contains(TAG_NUS.toString()));

        // Check that regular tags are found in the message
        assertTrue(message.contains(TAG_ARCHITECTURE_MOD.toString()));
    }

    @Test
    public void findTagByPartialMatchAndSuperTagFilter_success() throws ParseException {
        Model model = createTestModel();
        FindTagCommand command = new FindTagCommandParser().parse(" t/architecture st/1");
        String message = command.constructFilteredTagSummaryMessage(model, model.getPersonTags(), model.getSuperTags());

        // Check that matching super tags are found in the message
        assertTrue(message.contains(TAG_ARCHITECTURE.toString()));

        // Check that matching regular tags are not found in the message
        assertFalse(message.contains(TAG_ARCHITECTURE_MOD.toString()));
    }

}
