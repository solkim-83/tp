package seedu.address.model.tag;

import java.util.Map;
import java.util.Set;

/**
 * Abstract class that keeps track of the structure of {@code Tag} relations.
 * This is in-place of {@code Tag}s keeping track of their own sub-{@code tag}s and super-{@code tag}s.
 * When relationships between {@code Tag}s are modified, the changes are updated here so that all parts of the program
 * have access to the most updated {@code Tag} relations.
 */
public abstract class TagTree implements ReadOnlyTagTree {

    @Override
    public abstract Map<Tag, Set<Tag>> getTagSubTagMap();

    /**
     * Returns all {@code tag}s with at least one sub-tag.
     */
    public abstract Set<Tag> getSuperTags();

    /**
     * Returns all sub-{@code tag}s of the {@code tag} argument.
     *
     * @param tag {@code tag} which sub-{@code tag}s being queried.
     * @return {@code Set} of {@code tag}s containing sub-{@code tag}s of the {@code tag} argument.
     */
    public abstract Set<Tag> getSubTagsOf(Tag tag);

    /**
     * Returns all sub-tags below {@code tag} in the tag-hierarchy.
     * I.e. All sub-tags of {@code tag}, all sub-tags of those sub-tags, etc.
     */
    public abstract Set<Tag> getSubTagsRecursive(Tag tag);

    /**
     * Adds the {@code subTag} as a sub-tag of the {@code superTag}.
     * This method should check for cyclic dependencies and prevent linking a lower-level tag as a
     * parent to a higher-level tag.
     *
     * @param superTag {@code tag} to be assigned a sub-tag.
     * @param subTag {@code tag} to be assigned as a sub-tag.
     */
    public abstract void addSubTagTo(Tag superTag, Tag subTag);

    /**
     * Removes the {@code subTag} from the set of sub-tags belonging to the {@code superTag}.
     *
     * @param superTag {@code tag} to have a sub-tag removed.
     * @param subTag {@code tag} to be removed from the super-tag.
     */
    public abstract void removeSubTagFrom(Tag superTag, Tag subTag);

    /**
     * Deletes a {@code tag} from the tag tree.
     * Implementation should connect all child tags of the argument {@code tag}
     * to all parent tags of the argument {@code tag}s.
     *
     * @param tag Tag to be deleted.
     */
    public abstract void deleteTag(Tag tag);

    /**
     * Returns true if there is a direct path from the {@code superTag} to the {@code subTag} in the tag tree,
     * returns false otherwise.
     */
    public abstract boolean isSubTagOf(Tag superTag, Tag subTag);

    /**
     * Copies the equivalent mapping of the {@code otherTree} to the current tag tree.
     */
    public abstract void copy(ReadOnlyTagTree otherTree);

    /**
     * Deletes the {@code tag} and all its sub-tags from the tag tree.
     *
     * @param tag Tag to start the deletion.
     */
    public void deleteTagAndAllSubTags(Tag tag) {
        getSubTagsOf(tag).stream().forEach(subTag -> deleteTagAndAllSubTags(subTag));
        deleteTag(tag);
    }

    /**
     * Adds all tags in {@code tagSetToAdd} as sub-tags to {@code tag}.
     *
     * @param tag Tag to be assigned with sub-tags.
     * @param tagSetToAdd Set of tags to be added as sub-tags.
     */
    public void addSubTagsTo(Tag tag, Set<Tag> tagSetToAdd) {
        for (Tag tagToAdd : tagSetToAdd) {
            addSubTagTo(tag, tagToAdd);
        }
    }

    /**
     * Removes all tags in {@code tagSetToRemove} as sub-tags from {@code tag}.
     *
     * @param tag Tag to have sub-tags removed.
     * @param tagSetToRemove Set of tags to be removed from a super-tag.
     */
    public void removeSubTagsFrom(Tag tag, Set<Tag> tagSetToRemove) {
        for (Tag tagToRemove : tagSetToRemove) {
            removeSubTagFrom(tag, tagToRemove);
        }
    }


}
