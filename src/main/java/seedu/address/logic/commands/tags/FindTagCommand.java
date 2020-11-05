package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Finds and lists all tags in the system-display that contain any of the keywords used in the search.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.FIND.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_NO_TAGS_FOUND = "No tag assignments were found!";

    public static final String MESSAGE_TAGS_FOUND = "Listed all tags and contacts directly under these tags:";

    public static final String INDICATOR_SUPERTAG = " (supertag)";

    public static final String INDICATOR_NO_CONTACTS_TAGGED = "no contacts tagged";

    private final String tagToFind;
    private final Optional<BooleanInput> superTagOnly;

    public FindTagCommand(String tagToFind, Optional<BooleanInput> superTagOnly) {
        this.tagToFind = tagToFind;
        this.superTagOnly = superTagOnly;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Set<Tag> tagPersonSet = model.getPersonTags();
        Set<Tag> superTagSet = model.getSuperTags();
        String message = constructFilteredTagSummaryMessage(model, tagPersonSet, superTagSet);
        return new CommandResult(message);
    }

    /**
     * Returns one String containing a summary of all tags present in the system.
     * For a tag to be present, it has to have at least one {@code Person} with the tag OR has sub-tags.
     * Summary of each tag contains only the tag name and all contacts directly tagged with the tag.
     */
    protected String constructFilteredTagSummaryMessage(Model model, Set<Tag> tagPersonSet, Set<Tag> superTagSet) {
        Set<Tag> fullTagSet = new HashSet<>();
        superTagOnly.ifPresentOrElse(bool -> {
            if (bool.getBooleanValue()) {
                fullTagSet.addAll(tagPersonSet);
            } else {
                fullTagSet.addAll(superTagSet);
            }
        }, () -> { fullTagSet.addAll(tagPersonSet); fullTagSet.addAll(superTagSet);});
        fullTagSet.addAll(superTagSet);
        return fullTagSet.stream()
                .filter(tag -> tag.containsInName(tagToFind))
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
