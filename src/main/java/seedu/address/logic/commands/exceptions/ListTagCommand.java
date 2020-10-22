package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.LIST.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_NO_TAGS_FOUND = "No tag assignments were found!";

    public static final String MESSAGE_TAGS_FOUND = "Listed all tags and their direct sub-tags";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Set<Tag> tagSet = model.getTags();
        String message = constructTagSummaryMessage(model, tagSet);
        return new CommandResult(message);
    }

    private static String constructTagSummaryMessage(Model model, Set<Tag> set) {
        return set.stream()
                .map(tag -> tag.toString() + ": " + parsePersonSetIntoString(model.getPersonsWithTag(tag)))
                .reduce((s1, s2) -> s1 + '\n' + s2)
                .map(string -> MESSAGE_TAGS_FOUND + '\n' + string)
                .orElse(MESSAGE_NO_TAGS_FOUND);
    }

    private static String parsePersonSetIntoString(Set<Person> set) {
        return set.stream()
                .map(t -> t.toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "[ " + string + " ]")
                .orElseThrow(() -> new IllegalStateException("Illegal state: tag with no assignment!"));
    }

}