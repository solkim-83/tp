package seedu.address.model.tag.exceptions;

import seedu.address.model.tag.Tag;

public class TagCyclicDependencyException extends RuntimeException {

    public TagCyclicDependencyException(Tag subTag, Tag superTag) {
        super(String.format("Cyclic dependency detected! Avoid adding %s as a sub-tag of %s", superTag, subTag));
    }

}
