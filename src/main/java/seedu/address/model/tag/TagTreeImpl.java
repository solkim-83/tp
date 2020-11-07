package seedu.address.model.tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import seedu.address.model.tag.exceptions.TagCyclicDependencyException;

/**
 * A concrete implementation of the TagTree. It uses two HashMaps to keep track of the two-way relationship of tags.
 */
public class TagTreeImpl extends TagTree {

    private static final String MESSAGE_NOT_VALID_SUBTAG = "%s is not a valid subtag of %s";
    private static final String MESSAGE_NOT_VALID_SUPERTAG = "%s is not a valid supertag of %s";
    private static final String MESSAGE_CYCLIC_RELATIONSHIP = "%s is already a subtag of %s. "
            + "Avoid cyclic relationships!";

    private Map<Tag, Set<Tag>> tagSubTagMap;
    private Map<Tag, Set<Tag>> tagSuperTagMap;

    /**
     * Creates a clean TagTreeImpl.
     */
    public TagTreeImpl() {
        tagSubTagMap = new HashMap<>();
        tagSuperTagMap = new HashMap<>();
    }

    /**
     * Copies the argument {@code toCopy} tree into this tree.
     */
    public TagTreeImpl(ReadOnlyTagTree toCopy) {
        super();
        copy(toCopy);
    }

    /**
     * Creates a TagTreeImpl with the given {@code tagSubTagMap} and {@code tagSuperTagMap}.
     */
    public TagTreeImpl(Map<Tag, Set<Tag>> tagSubTagMap, Map<Tag, Set<Tag>> tagSuperTagMap) {
        this.tagSubTagMap = tagSubTagMap;
        this.tagSuperTagMap = tagSuperTagMap;
    }

    @Override
    public void copy(ReadOnlyTagTree toCopy) {
        tagSubTagMap = new HashMap<>();
        tagSuperTagMap = new HashMap<>();

        toCopy.getTagSubTagMap().entrySet().stream().forEach(entry -> {
            // Adds a copy of the key-value mapping to the subtag map.
            tagSubTagMap.put(entry.getKey(), new HashSet<>(Set.copyOf(entry.getValue())));

            // For each sub-tag, adds an equivalent reverse mapping to the super tag map.
            entry.getValue().stream().forEach(subTag -> tagSuperTagMap.merge(
                subTag, new HashSet<>(Set.of(entry.getKey())), (set1, set2) -> {
                    set1.addAll(set2);
                    return set1; })); });
    }

    @Override
    public Set<Tag> getSuperTags() {
        return tagSubTagMap.keySet();
    }

    @Override
    public Map<Tag, Set<Tag>> getTagSubTagMap() {
        Map<Tag, Set<Tag>> tempMap = new HashMap<>();
        tagSubTagMap.entrySet().stream().forEach(entry ->
                tempMap.put(entry.getKey(), Set.copyOf(entry.getValue())));
        return Map.copyOf(tempMap);
    }

    Map<Tag, Set<Tag>> getInternalTagSubTagMap() {
        return tagSubTagMap;
    }

    @Override
    public Set<Tag> getSubTagsOf(Tag tag) {
        return tagSubTagMap.containsKey(tag) ? Set.copyOf(tagSubTagMap.get(tag)) : Set.of();
    }

    @Override
    public Set<Tag> getSuperTagsOf(Tag tag) {
        return tagSuperTagMap.containsKey(tag) ? Set.copyOf(tagSuperTagMap.get(tag)) : Set.of();
    }

    @Override
    public Set<Tag> getSubTagsRecursive(Tag tag) {
        Set<Tag> finalSet = new HashSet<>();
        Consumer<Tag> consumer = new Consumer<Tag>() {
            @Override
            public void accept(Tag tag) {
                if (tagSubTagMap.get(tag) == null) {
                    return;
                }
                finalSet.addAll(tagSubTagMap.get(tag));
                tagSubTagMap.get(tag).forEach(subtag -> accept(subtag));
            }
        };
        consumer.accept(tag);
        return finalSet;
    }

    @Override
    public void addSubTagTo(Tag superTag, Tag subTag) {
        assert !superTag.equals(Tag.ALL_TAGS_TAG) && !subTag.equals(Tag.ALL_TAGS_TAG);

        if (isSubTagOf(subTag, superTag)) {
            throw new TagCyclicDependencyException(superTag, subTag);
        }
        addToMapSet(tagSubTagMap, superTag, subTag);
        addToMapSet(tagSuperTagMap, subTag, superTag);
    }

    /**
     * Adds the {@code tagToAdd} to the set of sub-tags assigned to {@code key}.
     * Adds a new HashSet if the {@code key} did not have any sub-tags.
     */
    private void addToMapSet(Map<Tag, Set<Tag>> map, Tag key, Tag tagToAdd) {
        map.merge(key, new HashSet<>(Set.of(tagToAdd)), (set1, set2) -> {
            set1.addAll(set2);
            return set1; });
    }

    @Override
    public void removeSubTagFrom(Tag superTag, Tag subTag) {
        if (subTag.equals(Tag.ALL_TAGS_TAG)) {
            removeSubTagsFrom(superTag, tagSubTagMap.get(superTag));
            return;
        }

        removeEntryFromMap(tagSubTagMap, superTag, subTag);
        removeEntryFromMap(tagSuperTagMap, subTag, superTag);
    }

    /**
     * Removes {@code tagToRemove} from the set corresponding to the {@code key}.
     * If the resulting set is empty, the key-value pair is removed from the map.
     */
    private void removeEntryFromMap(Map<Tag, Set<Tag>> map, Tag key, Tag tagToRemove) {
        map.get(key).remove(tagToRemove);
        if (map.get(key).isEmpty()) {
            map.remove(key);
        }
    }

    @Override
    public void deleteTag(Tag tag) {
        Set<Tag> subTagSet = tagSubTagMap.get(tag);
        boolean hasChildTags = subTagSet != null;
        Set<Tag> superTagSet = tagSuperTagMap.get(tag);
        boolean hasParentTags = superTagSet != null;

        if (hasParentTags && hasChildTags) {
            connectParentWithChildTags(superTagSet, subTagSet);
        }
        if (hasParentTags) {
            superTagSet.forEach(superTag -> removeEntryFromMap(tagSubTagMap, superTag, tag));
        }
        if (hasChildTags) {
            subTagSet.forEach(subTag -> removeEntryFromMap(tagSuperTagMap, subTag, tag));
        }

        tagSuperTagMap.remove(tag);
        tagSubTagMap.remove(tag);
    }

    /**
     * Adds the {@code subTagSet} to existing sub-tags for each tag in the superTagSet.
     */
    private void connectParentWithChildTags(Set<Tag> superTagSet, Set<Tag> subTagSet) {
        superTagSet.forEach(superTag -> addSubTagsTo(superTag, subTagSet));
    }

    /**
     * Returns true if {@code subtag} is below {@code superTag} along the tag hierarchy.
     */
    public boolean isSubTagOf(Tag superTag, Tag subTag) {
        boolean hasNoSubTags = tagSubTagMap.get(superTag) == null;
        if (hasNoSubTags) {
            return false;
        }

        boolean hasSubTagAsDirectChild = tagSubTagMap.get(superTag).contains(subTag);
        if (hasSubTagAsDirectChild) {
            return true;
        }

        return tagSubTagMap.get(superTag).stream().anyMatch(childTag -> isSubTagOf(childTag, subTag));
    }

    @Override
    public String toString() {
        return "Sub-tag map: " + tagSubTagMap + "\nSuper-tag map: " + tagSuperTagMap;
    }

    /**
     * Returns true if the superTag is a direct parent of the subTag. Method mainly used for testing.
     */
    boolean hasDirectSuperTag(Tag subTag, Tag superTag) {
        return tagSuperTagMap.get(subTag) != null && tagSuperTagMap.get(subTag).contains(superTag);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof TagTreeImpl)) {
            return false;
        } else {
            TagTreeImpl otherTree = (TagTreeImpl) object;
            return otherTree.tagSuperTagMap.equals(tagSuperTagMap)
                    && otherTree.tagSubTagMap.equals(tagSubTagMap);
        }
    }

}
