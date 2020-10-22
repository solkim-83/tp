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
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Shows the full details of the specified tags in the system-message field.
 */
public class ViewTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.VIEW.toString();
    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_INVALID_TAG = "No information can be found for the tag %s!";
    public static final String HEADER_CONTACTS_DIRECTLY_TAGGED = "Contacts directly tagged: ";
    public static final String HEADER_SUB_TAGS = "All sub-tags: ";
    public static final String HEADER_RELATED_CONTACTS = "Related contacts: ";
    public static final String INDICATOR_NO_DIRECTLY_TAGGED_CONTACTS = "no directly tagged contacts";
    public static final String INDICATOR_NO_SUB_TAGS = "no sub-tags found";
    public static final String INDICATOR_NO_RELATED_CONTACTS_FOUND =
            "no related contacts found (contacts belonging to sub-tags)";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the details of "
            + "one or more tags specified by the input values."
            + "There must be at least one specified tag.\n"
            + "Parameters: "
            + PREFIX_TAG + "TAG "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " "
            + PREFIX_TAG + "CS2103";

    private final Set<Tag> tagSetToView;

    public ViewTagCommand(Set<Tag> tagSetToView) {
        this.tagSetToView = tagSetToView;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(constructSetTagDetails(model, tagSetToView));
    }

    /**
     * Returns true if the {@code tag} is valid, that is it has at least one sub-tag or at least one
     * {@code person} tagged with it.
     */
    private static boolean isValidTag(Model model, Tag tag) {
        return model.getPersonTags().contains(tag) || model.getSuperTags().contains(tag);
    }

    /**
     * Returns a single String containing the full information of each tag within the {@code tagSet}.
     * For tags that could not be found, it will be stated at the start of the string that these tags could not be found.
     */
    private static String constructSetTagDetails(Model model, Set<Tag> tagSet) {
        String invalidTagString = tagSet.stream()
                .filter(tag -> !isValidTag(model, tag))
                .map(tag -> String.format(MESSAGE_INVALID_TAG, tag))
                .reduce((s1, s2) -> s1 + '\n' + s2)
                .map(string -> string + '\n')
                .orElse("");
        String output = tagSet.stream()
                .filter(tag -> isValidTag(model, tag))
                .map(tag -> constructTagDetailString(model, tag))
                .reduce((sb1, sb2) -> {sb1.append("\n" + sb2); return sb1;})
                .get().toString();
        return invalidTagString + output;
    }

    /**
     * Returns a StringBuilder of the full details of the {@code tag}.
     * First, it includes all {@code Person}s with the {@code tag}.
     * Second, it includes all sub-tags below {@code tag} in the tag hierarchy.
     * Third, it includes all {@code Person}s related to a sub-tag but not tagged with {@code tag}.
     */
    private static StringBuilder constructTagDetailString(Model model, Tag tag) {
        StringBuilder sb = new StringBuilder();

        boolean isSuperTag = model.getSuperTags().contains(tag);
        sb.append(tag + (isSuperTag ? INDICATOR_SUPERTAG : "") + ':');
        sb.append("\n" + HEADER_CONTACTS_DIRECTLY_TAGGED
                + parsePersonSetIntoString(model.getPersonsWithTag(tag), INDICATOR_NO_DIRECTLY_TAGGED_CONTACTS));
        sb.append("\n" + HEADER_SUB_TAGS + parseTagSetIntoString(model.getSubTagsRecursive(tag)));

        Set<Person> relatedContacts = new HashSet<>(model.getPersonsRecursive(tag));
        relatedContacts.removeAll(model.getPersonsWithTag(tag));
        sb.append("\n" + HEADER_RELATED_CONTACTS + parsePersonSetIntoString(relatedContacts,
                INDICATOR_NO_RELATED_CONTACTS_FOUND));

        return sb;
    }

    /**
     * Returns a String of comma separated sub-tags within curly braces.
     */
    private static String parseTagSetIntoString(Set<Tag> tagSet) {
        return tagSet.stream()
                .map(tag -> tag.toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "{ " + string + " }")
                .orElse(INDICATOR_NO_SUB_TAGS);
    }



}
