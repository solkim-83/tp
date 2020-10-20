package seedu.address.model.tag;

import java.util.Set;

public abstract class TagTree {

    public abstract Set<Tag> getSubTagsOf(Tag tag);

    public abstract void addSubTagTo(Tag superTag, Tag subTag);
    public abstract void removeSubTagFrom(Tag superTag, Tag subTag);

    public abstract void deleteTag(Tag tag);

    public void deleteTagAndAllSubTags(Tag tag) {
        getSubTagsOf(tag).stream().forEach(subTag -> deleteTagAndAllSubTags(subTag));
        deleteTag(tag);
    }

    public void addSubTagsTo(Tag tag, Set<Tag> tagSetToAdd) {
        for (Tag tagToAdd : tagSetToAdd) {
            addSubTagTo(tag, tagToAdd);
        }
    }

    public void removeSubTagsFrom(Tag tag, Set<Tag> tagSetToRemove) {
        for (Tag tagToRemove : tagSetToRemove) {
            removeSubTagFrom(tag, tagToRemove);
        }
    }


}
