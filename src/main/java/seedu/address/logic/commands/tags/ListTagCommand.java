package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Lists all tags in the system-message field.
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.LIST.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_NO_TAGS_FOUND = "No tag assignments were found!";

    public static final String MESSAGE_TAGS_FOUND = "Listed all tags and contacts directly under these tags:";

    public static final String INDICATOR_SUPERTAG = " (supertag)";

    public static final String INDICATOR_NO_CONTACTS_TAGGED = "no contacts tagged";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Set<Tag> tagPersonSet = model.getPersonTags();
        Set<Tag> superTagSet = model.getSuperTags();
        String message = constructTagSummaryMessage(model, tagPersonSet, superTagSet);
        return new CommandResult(message);
    }

    /**
     * Returns one String containing a summary of all tags present in the system.
     * For a tag to be present, it has to have at least one {@code Person} with the tag OR has sub-tags.
     * Summary of each tag contains only the tag name and all contacts directly tagged with the tag.
     */
    protected static String constructTagSummaryMessage(Model model, Set<Tag> tagPersonSet, Set<Tag> superTagSet) {
        Set<Tag> fullTagSet = new HashSet<>(tagPersonSet);
        fullTagSet.addAll(superTagSet);
        return fullTagSet.stream()
                .map(tag -> tag.toString() + (superTagSet.contains(tag) ? INDICATOR_SUPERTAG : "")
                        + ": " + parsePersonSetIntoString(model.getPersonsWithTag(tag), INDICATOR_NO_CONTACTS_TAGGED))
                .reduce((s1, s2) -> s1 + '\n' + s2)
                .map(string -> MESSAGE_TAGS_FOUND + '\n' + string)
                .orElse(MESSAGE_NO_TAGS_FOUND);
    }

    /**
     * Returns a string that combines the {@code Person} set by commas, then adds curly braces.
     * If the set is empty, returns {@code messageIfNoneFound}.
     */
    protected static String parsePersonSetIntoString(Set<Person> set, String messageIfNoneFound) {
        return set.stream()
                .map(t -> t.getName().toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "{ " + string + " }")
                .orElse(messageIfNoneFound);
    }

}
