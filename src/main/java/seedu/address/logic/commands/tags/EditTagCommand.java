package seedu.address.logic.commands.tags;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class EditTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.EDIT.toString();
    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    private static final String MESSAGE_REMOVE_CONTACT_DOES_NOT_HAVE_TAG =
            "One of the specified indices to remove does not have %s as a tag";
    private static final String MESSAGE_ADD_CONTACT_HAS_TAG =
            "One of the specified indices to add already has %s as a tag";
    private static final String MESSAGE_REMOVE_TAG_NOT_PRESENT =
            "One of the specified tags to remove is already not a child-tag of %s";
    private static final String MESSAGE_ADD_TAG_PRESENT =
            "One of the specified tags to add is already a child-tag of %s";
    private static final String MESSAGE_SUCCESS =
            "%s has successfully been edited!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Edits an existing tag in Athena. You can add and/or remove contacts from a tag, and/or "
            + "add and/or remove child-tags from the same tag.\n\n"
            + "Parameters:\n"
            + PREFIX_NAME + "TAG_NAME\n"
            + "[" + PREFIX_INDEX + "INDEX_TO_ADD]...\n"
            + "[" + PREFIX_REMOVE_INDEX + "INDEX_TO_REMOVE]...\n"
            + "[" + PREFIX_TAG + "SUB_TAG_TO_ADD]...\n"
            + "[" + PREFIX_REMOVE_TAG + "SUB_TAG_TO_REMOVE]...\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_REMOVE_INDEX + "9 "
            + PREFIX_TAG + "newchildtag "
            + PREFIX_REMOVE_TAG + "oldchildtag";

    private final Tag tagToEdit;
    private final Set<Index> indexSetToAdd;
    private final Set<Index> indexSetToRemove;
    private final Set<Tag> tagSetToAdd;
    private final Set<Tag> tagSetToRemove;

    public EditTagCommand(Tag tagToEdit, Set<Index> indexSetToAdd, Set<Index> indexSetToRemove,
            Set<Tag> tagSetToAdd, Set<Tag> tagSetToRemove) {
        requireAllNonNull(tagToEdit, indexSetToAdd, indexSetToRemove, tagSetToAdd, tagSetToRemove);

        this.tagToEdit = tagToEdit;
        this.indexSetToAdd = indexSetToAdd;
        this.indexSetToRemove = indexSetToRemove;
        this.tagSetToAdd = tagSetToAdd;
        this.tagSetToRemove = tagSetToRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkInputValidity(model);

        removeTagFromPersonsAtIndices(model);
        addTagToPersonsAtIndices(model);

        removeChildTagsFromTag(model);
        addChildTagsFromTag(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, tagToEdit));
    }

    private void checkInputValidity(Model model) throws CommandException {
        boolean hasPersonToRemoveWithoutTag = getPersonsAtIndices(model, indexSetToRemove).stream()
                .anyMatch(person -> !person.getTags().contains(tagToEdit));
        if (hasPersonToRemoveWithoutTag) {
            throw new CommandException(String.format(MESSAGE_REMOVE_CONTACT_DOES_NOT_HAVE_TAG, tagToEdit));
        }

        boolean hasPersonToAddWithTag = getPersonsAtIndices(model, indexSetToAdd).stream()
                .anyMatch(person -> person.getTags().contains(tagToEdit));
        if (hasPersonToAddWithTag) {
            throw new CommandException(String.format(MESSAGE_ADD_CONTACT_HAS_TAG, tagToEdit));
        }

        boolean hasChildTagToRemoveNotPresent = tagSetToRemove.stream()
                .anyMatch(tag -> !model.getChildTags(tagToEdit).contains(tag));
        if (hasChildTagToRemoveNotPresent) {
            throw new CommandException(String.format(MESSAGE_REMOVE_TAG_NOT_PRESENT, tagToEdit));
        }

        boolean hasChildTagToAddAlreadyPresent = tagSetToAdd.stream()
                .anyMatch(tag -> model.getChildTags(tagToEdit).contains(tag));
        if (hasChildTagToAddAlreadyPresent) {
            throw new CommandException(String.format(MESSAGE_ADD_TAG_PRESENT, tagToEdit));
        }
    }

    private static Set<Person> getPersonsAtIndices(Model model, Set<Index> indices) {
        return indices.stream()
                .map(index -> index.getZeroBased())
                .map(index -> model.getSortedFilteredPersonList().get(index))
                .collect(Collectors.toSet());

    }

    private void removeTagFromPersonsAtIndices(Model model) throws CommandException {
        Set<Person> personSetToRemove = getPersonsAtIndices(model, indexSetToRemove);
        personSetToRemove.stream().forEach(person -> model.removePersonFromTag(tagToEdit, person));
    }

    private void addTagToPersonsAtIndices(Model model) throws CommandException {
        Set<Person> personSetToAdd = getPersonsAtIndices(model, indexSetToAdd);
        personSetToAdd.stream().forEach(person -> model.addPersonToTag(tagToEdit, person));
    }

    private void removeChildTagsFromTag(Model model) throws CommandException {
        Set<Tag> originalChildTagSet = model.getChildTags(tagToEdit);
        tagSetToRemove.stream().forEach(tag -> model.removeChildTagFrom(tagToEdit, tag));
    }

    private void addChildTagsFromTag(Model model) throws CommandException {
        Set<Tag> originalChildTagSet = model.getChildTags(tagToEdit);
        tagSetToAdd.stream().forEach(tag -> model.addSubTagTo(tagToEdit, tag));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EditTagCommand)) {
            return false;
        } else {
            EditTagCommand other = (EditTagCommand) o;
            return other.indexSetToAdd.equals(indexSetToAdd)
                    && other.indexSetToRemove.equals(indexSetToRemove)
                    && other.tagSetToAdd.equals(tagSetToAdd)
                    && other.tagSetToRemove.equals(tagSetToRemove);
        }
    }

}
