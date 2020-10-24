package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.tag.ReadOnlyTagTree;

/**
 * Represents a storage for {@link seedu.address.model.tag.ReadOnlyTagTree}
 */
public interface TagTreeStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTagTreeFilePath();

    /**
     * Returns TagTree data as a {@link ReadOnlyTagTree}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTagTree> readTagTree() throws DataConversionException, IOException;

    /**
     * @see #readTagTree()
     */
    Optional<ReadOnlyTagTree> readTagTree(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTagTree} to the storage.
     *
     * @param tagTree cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTagTree(ReadOnlyTagTree tagTree) throws IOException;

    /**
     * @see #saveTagTree(ReadOnlyTagTree)
     */
    void saveTagTree(ReadOnlyTagTree tagTree, Path filePath) throws IOException;

}
