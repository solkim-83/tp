package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ListTagCommand.INDICATOR_SUPERTAG;
import static seedu.address.logic.commands.ListTagCommand.parsePersonSetIntoString;

public class ViewTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.VIEW.toString();
    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_INVALID_TAG = "No information can be found for the tag %s!";
    public static final String HEADER_CONTACTS_DIRECTLY_TAGGED = "Contacts directly tagged: ";
    public static final String HEADER_SUB_TAGS = "All sub-tags: ";
    public static final String HEADER_RELATED_CONTACTS = "Related contacts: ";
    public static final String INDICATOR_NO_SUB_TAGS = "no sub-tags found";

    private final Tag tagToView;

    public ViewTagCommand(Tag tagToView) {
        this.tagToView = tagToView;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean isValidTag = model.getPersonTags().contains(tagToView) || model.getSuperTags().contains(tagToView);
        if (!isValidTag) {
            throw new CommandException(String.format(MESSAGE_INVALID_TAG, tagToView));
        }

        return new CommandResult(constructTagDetailString(model, tagToView));


    }

    private static String constructTagDetailString(Model model, Tag tag) {
        StringBuilder sb = new StringBuilder();

        boolean isSuperTag = model.getSuperTags().contains(tag);
        sb.append(tag + (isSuperTag ? INDICATOR_SUPERTAG : "") + ':');
        sb.append(HEADER_CONTACTS_DIRECTLY_TAGGED + parsePersonSetIntoString(model.getPersonsWithTag(tag)));
        sb.append(HEADER_SUB_TAGS + parseTagSetIntoString(model.getSubTagsRecursive(tag)));

        Set<Person> relatedContacts = new HashSet<>(model.getPersonsRecursive(tag));
        relatedContacts.removeAll(model.getPersonsWithTag(tag));
        sb.append(HEADER_RELATED_CONTACTS + parsePersonSetIntoString(relatedContacts));

        return sb.toString();
    }

    private static String parseTagSetIntoString(Set<Tag> tagSet) {
        return tagSet.stream()
                .map(tag -> tag.toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "{ " + string + " }")
                .orElse(INDICATOR_NO_SUB_TAGS);
    }



}
