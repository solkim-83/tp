package seedu.address.model.util;

import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A container for Tag specific utility functions and messages
 */
public class TagUtil {

    public static final String MESSAGE_NO_TAGS_FOUND = "No tag assignments were found!";
    public static final String MESSAGE_TAGS_FOUND = "Listed all tags and contacts directly under these tags:";
    public static final String MESSAGE_INVALID_SEARCH_FIELD = "Tag queries should be have no spaces.";
    public static final String INDICATOR_SUPERTAG = " (supertag)";
    public static final String INDICATOR_NO_CONTACTS_TAGGED = "no contacts tagged";

    /**
     * Returns a string that combines the {@code Person} set by commas, then adds curly braces.
     * If the set is empty, returns {@code messageIfNoneFound}.
     */
    public static String parsePersonSetIntoString(Set<Person> set, String messageIfNoneFound) {
        return set.stream()
                .map(t -> t.getName().toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "{ " + string + " }")
                .orElse(messageIfNoneFound);
    }

    /**
     * Returns a String of comma separated sub-tags within curly braces.
     */
    public static String parseTagSetIntoString(Set<Tag> tagSet, String messageIfEmpty) {
        return tagSet.stream()
                .map(Tag::toString)
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "{ " + string + " }")
                .orElse(messageIfEmpty);
    }

}
