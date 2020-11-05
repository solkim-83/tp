package seedu.address.logic.commands.tags;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.testutil.ModelManagerBuilder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.tags.FindTagCommand.INDICATOR_SUPERTAG;
import static seedu.address.logic.commands.tags.FindTagCommand.parsePersonSetIntoString;
import static seedu.address.model.ContactTagIntegrationManagerTest.PERSON_CS1231S_1;
import static seedu.address.model.ContactTagIntegrationManagerTest.PERSON_CS1231S_2;
import static seedu.address.model.ContactTagIntegrationManagerTest.buildTestIntegrationAddressBook;
import static seedu.address.testutil.TagTreeUtil.TAG_COMPUTING;
import static seedu.address.testutil.TagTreeUtil.TAG_NUS;
import static seedu.address.testutil.TagTreeUtil.buildTestTree;

public class FindTagCommandTest {

    private static final String STRING_IF_EMPTY = "empty string rep";

    public static Model createTestModel() {
        return new ModelManagerBuilder()
                .withAddressBook(buildTestIntegrationAddressBook())
                .withTagTree(buildTestTree()).build();
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
    public void constructUnfilteredTagSummaryMessage_validTags_success() {
        Model model = createTestModel();
        FindTagCommand command = new FindTagCommand(Optional.empty(), Optional.empty());
        String message = command.constructFilteredTagSummaryMessage(model, model.getPersonTags(), model.getSuperTags());

        // Check if supertag identification is shown
        assertTrue(message.contains(TAG_NUS + INDICATOR_SUPERTAG));

        // Check if persons are shown
        assertTrue(message.contains(parsePersonSetIntoString(model.getPersonsWithTag(TAG_COMPUTING), STRING_IF_EMPTY)));
    }

    @Test
    public void findTagByFullMatch_success() {
        Model model = createTestModel();
    }

}
