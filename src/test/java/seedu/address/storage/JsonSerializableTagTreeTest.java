package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TagTreeUtil.buildTestTree;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.tag.TagTree;

public class JsonSerializableTagTreeTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTagTreeTest");
    private static final Path VALID_TAG_TREE_FILE = TEST_DATA_FOLDER.resolve("sampleValidTagTree.json");
    private static final Path INVALID_TAG_TREE_FILE = TEST_DATA_FOLDER.resolve("invalidTagTree.json");

    @Test
    public void toModelType_validTagTreeFile_success() throws Exception {
        JsonSerializableTagTree dataFromFile = JsonUtil.readJsonFile(VALID_TAG_TREE_FILE,
                JsonSerializableTagTree.class).get();
        TagTree tagTreeFromFile = dataFromFile.toModelType();
        TagTree expectedTagTree = buildTestTree();
        assertEquals(expectedTagTree, tagTreeFromFile);
    }

    @Test
    public void toModelType_invalidTagTreeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTagTree dataFromFile = JsonUtil.readJsonFile(INVALID_TAG_TREE_FILE,
                JsonSerializableTagTree.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelType());
    }


}
