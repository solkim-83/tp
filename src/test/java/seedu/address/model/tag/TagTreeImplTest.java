package seedu.address.model.tag;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TagTreeUtil.SET_FACULTIES;
import static seedu.address.testutil.TagTreeUtil.SET_MODULES;
import static seedu.address.testutil.TagTreeUtil.SET_SCIENCE_COMP_SUPERTAGS;
import static seedu.address.testutil.TagTreeUtil.TAG_ARCHITECTURE;
import static seedu.address.testutil.TagTreeUtil.TAG_COMPUTING;
import static seedu.address.testutil.TagTreeUtil.TAG_CS1231S;
import static seedu.address.testutil.TagTreeUtil.TAG_CS2040S_NOT_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_NOT_IN_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_NUS;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE_COMP;
import static seedu.address.testutil.TagTreeUtil.buildTestTree;

public class TagTreeImplTest {

    @Test
    public void getSubTagsOf_validTag_success() {
        assertEquals(buildTestTree().getSubTagsOf(TAG_NUS), SET_FACULTIES);
    }

    @Test
    public void getSubTagsOf_invalidTag_emptySetReturned() {
        assertEquals(buildTestTree().getSubTagsOf(TAG_NOT_IN_TREE), Set.of());
    }

    @Test
    public void addSubTag_nonCyclicTag_success() {
        TagTreeImpl testTree = buildTestTree();
        testTree.addSubTagTo(TAG_COMPUTING, TAG_CS2040S_NOT_TREE);
        assertTrue(testTree.getSubTagsOf(TAG_COMPUTING).contains(TAG_CS2040S_NOT_TREE));
        assertTrue(testTree.hasDirectSuperTag(TAG_CS2040S_NOT_TREE, TAG_COMPUTING));
    }

    @Test
    public void addSubTag_cyclicTag_errorThrown() {
        assertThrows(IllegalArgumentException.class, () -> buildTestTree().addSubTagTo(TAG_COMPUTING, TAG_NUS));
    }

    @Test
    public void removeSubTag_validTag_success() {
        TagTreeImpl testTree = buildTestTree();
        testTree.removeSubTagFrom(TAG_NUS, TAG_ARCHITECTURE);
        assertFalse(testTree.getSubTagsOf(TAG_NUS).contains(TAG_ARCHITECTURE));
        assertFalse(testTree.hasDirectSuperTag(TAG_ARCHITECTURE, TAG_NUS));
    }

    @Test
    public void removeSubTag_invalidTag_errorThrown() {
        assertThrows(NoSuchElementException.class, () -> buildTestTree().removeSubTagFrom(TAG_NUS, TAG_CS1231S));
    }

    @Test
    public void deleteTag_validTagToRemove_success() {
        TagTreeImpl testTree = buildTestTree();
        testTree.deleteTag(TAG_SCIENCE_COMP);
        assertTrue(SET_SCIENCE_COMP_SUPERTAGS.stream()
                .allMatch(tag -> testTree.getSubTagsOf(tag).containsAll(SET_MODULES)));
        assertTrue(SET_MODULES.stream().allMatch(tag -> testTree.hasDirectSuperTag(tag, TAG_COMPUTING)));
        assertTrue(SET_MODULES.stream().allMatch(tag -> testTree.hasDirectSuperTag(tag, TAG_SCIENCE)));
    }

    @Test
    public void deleteTag_tagInTree_noChange() {
        TagTreeImpl testTree = buildTestTree();
        testTree.deleteTag(TAG_CS2040S_NOT_TREE);
        assertEquals(testTree, buildTestTree());
    }

    @Test
    public void isSubTag_validSubTag_success() {
        TagTreeImpl testTree = buildTestTree();
        assertTrue(testTree.isSubTagOf(TAG_NUS, TAG_COMPUTING));
        assertTrue(testTree.isSubTagOf(TAG_COMPUTING, TAG_CS1231S));
    }

    @Test
    public void isSubTag_invalidSubTag_returnsFalse() {
        assertFalse(buildTestTree().isSubTagOf(TAG_COMPUTING, TAG_CS2040S_NOT_TREE));
    }







}
