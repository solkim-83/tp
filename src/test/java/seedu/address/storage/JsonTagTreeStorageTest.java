package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TagTreeUtil.TAG_COMPUTING;
import static seedu.address.testutil.TagTreeUtil.TAG_CS2040S_NOT_TREE;
import static seedu.address.testutil.TagTreeUtil.buildTestTree;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.TagTree;
import seedu.address.model.tag.TagTreeImpl;


public class JsonTagTreeStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTagTreeStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTagTree_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTagTree(null));
    }

    private Optional<ReadOnlyTagTree> readTagTree(String filePath) throws Exception {
        return new JsonTagTreeStorage(Paths.get(filePath)).readTagTree(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertTrue(readTagTree("NonExistentFile.json").isEmpty());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTagTree("notJsonFormatTagTree.json"));
    }

    @Test
    public void readTagTree_invalidTagTree_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTagTree("invalidTagTree.json"));
    }

    @Test
    public void readAndSaveTagTree_validTagTree_success() throws Exception {
        Path filePath = testFolder.resolve("tempTagTree.json");
        TagTree original = buildTestTree();
        JsonTagTreeStorage jsonTagTreeStorage = new JsonTagTreeStorage(filePath);

        // Save in new file and read back
        jsonTagTreeStorage.saveTagTree(original, filePath);
        ReadOnlyTagTree readBack = jsonTagTreeStorage.readTagTree(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite existing file, and read back
        TagTree editedTree = new TagTreeImpl();
        editedTree.copy(readBack);
        editedTree.addSubTagTo(TAG_COMPUTING, TAG_CS2040S_NOT_TREE);
        jsonTagTreeStorage.saveTagTree(editedTree, filePath);
        readBack = jsonTagTreeStorage.readTagTree(filePath).get();
        assertNotEquals(original, readBack);

    }

    @Test
    public void saveTagTree_nullTagTree_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTagTree(null, "inconsequentialName.json"));
    }

    /**
     * Saves {@code tagTree} at the specified {@code filePath}.
     */
    private void saveTagTree(ReadOnlyTagTree tagTree, String filePath) {
        try {
            new JsonTagTreeStorage(Paths.get(filePath))
                    .saveTagTree(tagTree, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTagTree_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTagTree(new TagTreeImpl(), null));
    }



}
