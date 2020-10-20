package seedu.address.model.tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class TagTreeImpl extends TagTree {

    private static final String MESSAGE_NOT_VALID_SUBTAG = "%s is not a valid subtag of %s";
    private static final String MESSAGE_NOT_VALID_SUPERTAG = "%s is not a valid supertag of %s";
    private static final String MESSAGE_CYCLIC_RELATIONSHIP = "%s is already a subtag of %s. "
            + "Avoid cyclic relationships!";

    private Map<Tag, Set<Tag>> tagSubTagMap;
    private Map<Tag, Set<Tag>> tagSuperTagMap;

    public TagTreeImpl() {
        tagSubTagMap = new HashMap<>();
        tagSuperTagMap = new HashMap<>();
    }

    @Override
    public Set<Tag> getSubTagsOf(Tag tag) {
        return tagSubTagMap.get(tag);
    }

    @Override
    public void addSubTagTo(Tag superTag, Tag subTag) {
        if (isSubTagOf(subTag, superTag)) {
            throw new IllegalArgumentException(String.format(MESSAGE_CYCLIC_RELATIONSHIP, superTag, subTag));
        }
        addToMapSet(tagSubTagMap, superTag, subTag);
        addToMapSet(tagSuperTagMap, subTag, superTag);
    }

    private void addToMapSet(Map<Tag, Set<Tag>> map, Tag key, Tag tagToAdd) {
        map.merge(key, new HashSet<>(Set.of(tagToAdd)),
                (set1, set2) -> {set1.addAll(set2); return set1;});
    }

    public void removeSubTagFrom(Tag superTag, Tag subTag) {
        if (!tagSubTagMap.get(superTag).contains(subTag)) {
            throw new NoSuchElementException(String.format(MESSAGE_NOT_VALID_SUBTAG, subTag, superTag));
        }
        if (!tagSuperTagMap.get(subTag).contains(superTag)) {
            throw new NoSuchElementException(String.format(MESSAGE_NOT_VALID_SUPERTAG, superTag, subTag));
        }
        removeEntryFromMap(tagSubTagMap, superTag, subTag);
        removeEntryFromMap(tagSuperTagMap, subTag, superTag);
    }

    private void removeEntryFromMap(Map<Tag, Set<Tag>> map, Tag key, Tag tagToRemove) {
        map.get(key).remove(tagToRemove);
        if (map.get(key).isEmpty()) {
            map.remove(key);
        }
    }

    public void deleteTag(Tag tag) {
        Set<Tag> subTagSet = tagSubTagMap.get(tag);
        boolean hasChildTags = !subTagSet.isEmpty();
        Set<Tag> superTagSet = tagSuperTagMap.get(tag);
        boolean hasParentTags = !superTagSet.isEmpty();

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

    private void connectParentWithChildTags(Set<Tag> superTagSet, Set<Tag> subTagSet) {
        superTagSet.forEach(superTag -> addSubTagsTo(superTag, subTagSet));
    }

    private boolean isSubTagOf(Tag superTag, Tag subTag) {
        boolean hasNoSubTags = tagSubTagMap.get(superTag) == null
                || tagSubTagMap.get(superTag).isEmpty();
        if (hasNoSubTags) {
            return false;
        }

        boolean hasSubTagAsDirectChild = tagSubTagMap.get(superTag).contains(subTag);
        if (hasSubTagAsDirectChild) {
            return true;
        }

        return tagSubTagMap.get(superTag).stream().anyMatch(childTag -> isSubTagOf(childTag, subTag));
    }


}
