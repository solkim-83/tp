package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ListTagCommandTest.createTestModel;
import static seedu.address.logic.commands.ViewTagCommand.INDICATOR_NO_DIRECTLY_TAGGED_CONTACTS;
import static seedu.address.logic.commands.ViewTagCommand.INDICATOR_NO_RELATED_CONTACTS_FOUND;
import static seedu.address.logic.commands.ViewTagCommand.MESSAGE_INVALID_TAG;
import static seedu.address.logic.commands.ViewTagCommand.constructSetTagDetails;
import static seedu.address.testutil.TagTreeUtil.TAG_CS2040S_NOT_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_NUS;


public class ViewTagCommandTest {

    @Test
    public void constructSetTagDetails_validAndInvalidTags_success() {
        Model model = createTestModel();
        String message = constructSetTagDetails(model, Set.of(TAG_CS2040S_NOT_TREE, TAG_NUS));
        assertTrue(message.contains(String.format(MESSAGE_INVALID_TAG, TAG_CS2040S_NOT_TREE)));
        assertTrue(message.contains(INDICATOR_NO_DIRECTLY_TAGGED_CONTACTS));
        assertFalse(message.contains(INDICATOR_NO_RELATED_CONTACTS_FOUND));
    }



}