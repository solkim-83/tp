package seedu.address.model.tag;

import java.util.Map;
import java.util.Set;

/**
 * Unmodifiable view of the tag tree.
 */
public interface ReadOnlyTagTree {

    /**
     * Returns an unmodifiable view of all tags and their subtags.
     */
    Map<Tag, Set<Tag>> getTagSubTagMap();

}
