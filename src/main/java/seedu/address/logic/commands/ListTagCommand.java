package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.LIST.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_NO_TAGS_FOUND = "No tag assignments were found!";

    public static final String MESSAGE_TAGS_FOUND = "Listed all tags and contacts directly under these tags:";

    public static final String INDICATOR_SUPERTAG = "(supertag)";

    public static final String INDICATOR_NO_CONTACTS_TAGGED = "no contacts tagged";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Set<Tag> tagPersonSet = model.getPersonTags();
        Set<Tag> superTagSet = model.getSuperTags();
        String message = constructTagSummaryMessage(model, tagPersonSet, superTagSet);
        return new CommandResult(message);
    }

    private static String constructTagSummaryMessage(Model model, Set<Tag> tagPersonSet, Set<Tag> superTagSet) {
        Set<Tag> fullTagSet = new HashSet<>(tagPersonSet);
        fullTagSet.addAll(superTagSet);
        return fullTagSet.stream()
                .map(tag -> tag.toString() + (superTagSet.contains(tag) ? INDICATOR_SUPERTAG : "")
                        + ": " + parsePersonSetIntoString(model.getPersonsWithTag(tag)))
                .reduce((s1, s2) -> s1 + '\n' + s2)
                .map(string -> MESSAGE_TAGS_FOUND + '\n' + string)
                .orElse(MESSAGE_NO_TAGS_FOUND);
    }

    protected static String parsePersonSetIntoString(Set<Person> set) {
        return set.stream()
                .map(t -> t.getName().toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "{ " + string + " }")
                .orElse("");
    }

}