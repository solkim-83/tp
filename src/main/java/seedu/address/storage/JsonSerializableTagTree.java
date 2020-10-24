package seedu.address.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.ReadOnlyTagTree;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;
import seedu.address.model.tag.TagTreeImpl;

/**
 * An Immutable TagTree that is serializable to JSON format.
 */
@JsonRootName(value = "tagtree")
class JsonSerializableTagTree {

    private final Map<JsonAdaptedTag, Set<JsonAdaptedTag>> tagSubTagMap = new HashMap<>();

    /**
     * Constructs a {@code JsonSerializableTagTree} with the given map.
     */
    @JsonCreator
    public JsonSerializableTagTree(@JsonProperty("tagSubTagMap") Map<JsonAdaptedTag,
            Set<JsonAdaptedTag>> tagSubTagMap) {
        this.tagSubTagMap.putAll(tagSubTagMap);
    }

    /**
     * Converts a given {@code ReadOnlyTagTree} into this class for json use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTagTree}.
     */
    public JsonSerializableTagTree(ReadOnlyTagTree source) {
        tagSubTagMap.putAll(convertTagMapToJsonTagMap(source.getTagSubTagMap()));
    }

    /**
     * Converts the tag set map into a {@code JsonAdaptedTag} set map.
     */
    private Map<JsonAdaptedTag, Set<JsonAdaptedTag>> convertTagMapToJsonTagMap(Map<Tag, Set<Tag>> tagMap) {
        HashMap<JsonAdaptedTag, Set<JsonAdaptedTag>> jsonMap = new HashMap<>();
        tagMap.entrySet().stream().forEach(entry -> jsonMap.put(
                new JsonAdaptedTag(entry.getKey()),
                entry.getValue().stream().map(JsonAdaptedTag::new).collect(Collectors.toSet())));
        return jsonMap;
    }

    /**
     * Converts this tag tree into the model's {@code TagTree} object.
     *
     * @throws IllegalValueException if there were any data constaints violated.
     */
    public TagTree toModelType() throws IllegalValueException {
        HashMap<Tag, Set<Tag>> modifiableTagSubTagMap = new HashMap<>();
        HashMap<Tag, Set<Tag>> modifiableTagSuperTagMap = new HashMap<>();

        for (Map.Entry<JsonAdaptedTag, Set<JsonAdaptedTag>> entry : tagSubTagMap.entrySet()) {
            Tag superTag = entry.getKey().toModelType();
            Set<Tag> subTagSet = new HashSet<>();
            for (JsonAdaptedTag adaptedTag : entry.getValue()) {
                Tag subTag = adaptedTag.toModelType();
                subTagSet.add(subTag);
                modifiableTagSuperTagMap.merge(subTag,
                        new HashSet<>(Set.of(superTag)), (set1, set2) -> {
                        set1.addAll(set2);
                        return set1; });
            }
            modifiableTagSubTagMap.put(superTag, subTagSet);
        }

        return new TagTreeImpl(modifiableTagSubTagMap, modifiableTagSuperTagMap);
    }

}
