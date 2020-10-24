package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.tag.ReadOnlyTagTree;


/**
 * Class to access TagTree data stored as a json file on the hard disk.
 */
public class JsonTagTreeStorage implements TagTreeStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTagTreeStorage.class);

    private Path filePath;

    public JsonTagTreeStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTagTreeFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTagTree> readTagTree() throws DataConversionException, IOException {
        return readTagTree(filePath);
    }

    /**
     * Similar to {@link #readTagTree()}
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyTagTree> readTagTree(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableTagTree> jsonTagTree = JsonUtil.readJsonFile(
                filePath, JsonSerializableTagTree.class);
        if (!jsonTagTree.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTagTree.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }

    }

    @Override
    public void saveTagTree(ReadOnlyTagTree tagTree) throws IOException {
        saveTagTree(tagTree, filePath);
    }

    /**
     * Similar to {@link #saveTagTree(ReadOnlyTagTree)}
     *
     * @param tagTree tag tree to be saved. Cannot be null.
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveTagTree(ReadOnlyTagTree tagTree, Path filePath) throws IOException {
        requireNonNull(tagTree);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTagTree(tagTree), filePath);
    }

}
