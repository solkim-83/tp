package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * Edits an existing tag in Athena.
 * The tag can be assigned new child-tags or have existing child-tags removed.
 * Additionally, edits can be made to the set of contacts containing the tag.
 */
public class EditTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.EDIT.toString();
    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_REMOVE_CONTACT_DOES_NOT_HAVE_TAG =
            "One of the specified indices to remove does not have %s as a tag";
    public static final String MESSAGE_ADD_CONTACT_HAS_TAG =
            "One of the specified indices to add already has %s as a tag";
    public static final String MESSAGE_REMOVE_TAG_NOT_PRESENT =
            "One of the specified tags to remove is already not a child-tag of %s";
    public static final String MESSAGE_ADD_TAG_PRESENT =
            "One of the specified tags to add is already a child-tag of %s";
    public static final String MESSAGE_SUCCESS =
            "%s has successfully been edited!";
    public static final String MESSAGE_CYCLIC_DEPENDENCY_DETECTED =
            "Cyclic dependency detected between %s and %s! "
            + "Do not assign a super-tag as a sub-tag to the tag you are editing.";
    public static final String MESSAGE_NOT_EDITED =
            "None of the edit fields have been properly specified! Please specify at least one";
    public static final String MESSAGE_TAG_TO_EDIT_NOT_PRESENT =
            "%s does not exist in Athena!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Edits an existing tag in Athena. You can add and/or remove contacts from a tag, and/or "
            + "add and/or remove child-tags from the same tag. "
            + "At least one of the optional fields must be specified.\n\n"
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

    /**
     * Creates an EditTagCommand object that edits {@code tagToEdit}.
     * For contacts found at indices in {@code indexSetToAdd}, {@code tagToEdit} is added to these contacts.
     * For contacts found at indices in {@code indexSetToRemove}, {@code tagToEdit} is removed from these contacts.
     * For the child-tag set of {@code tagToEdit}, tags in {@code tagSetToAdd} will be added and tags in
     * {@code tagSetToRemove} will be removed.
     * {@code tagToEdit}, and tags in {@code tagSetToAdd} and {@code tagSetToRemove} must already exist in Athena.
     */
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

    /**
     * Checks the validity of the command with the given model.
     * Contact to have {@code tagToEdit} removed must have the tag prior to execution.
     * Contact to have {@code tagToEdit} added must not have the tag prior to execution.
     * Tags from {@code tagSetToRemove} must already be in the child-tag set of {@code tagToEdit} prior to execution.
     * Tags from {@code tagSetToAdd} must not be in the child-tag set of {@code tagToEdit} prior to execution.
     * Tags from {@code tagSetToAdd} that are to added as child-tag cannot be super-tags of {@code tagToEdit}.
     * @throws CommandException if any of the conditions are violated.
     */
    private void checkInputValidity(Model model) throws CommandException {
        boolean hasPersonToRemoveWithoutTag = getPersonsAtIndices(model, indexSetToRemove).stream()
                .anyMatch(person -> !person.getTags().contains(tagToEdit));

        if (!model.hasTag(tagToEdit)) {
            throw new CommandException(String.format(MESSAGE_TAG_TO_EDIT_NOT_PRESENT, tagToEdit));
        }

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

        for (Tag childTagToAdd : tagSetToAdd) {
            if (model.isSubTagOf(childTagToAdd, tagToEdit)) {
                throw new CommandException(String.format(MESSAGE_CYCLIC_DEPENDENCY_DETECTED, tagToEdit, childTagToAdd));
            }
        }

    }

    /**
     * Returns a set of person objects corresponding to the indices specified by {@code indices}.
     */
    private static Set<Person> getPersonsAtIndices(Model model, Set<Index> indices) {
        return indices.stream()
                .map(index -> index.getZeroBased())
                .map(index -> model.getSortedFilteredPersonList().get(index))
                .collect(Collectors.toSet());
    }

    /**
     * Removes {@code tagToEdit} from all persons specified by indices in {@code indexSetToRemove}.
     */
    private void removeTagFromPersonsAtIndices(Model model) {
        Set<Person> personSetToRemove = getPersonsAtIndices(model, indexSetToRemove);
        personSetToRemove.stream().forEach(person -> model.removePersonFromTag(tagToEdit, person));
    }

    /**
     * Adds {@code tagToEdit} to all persons specified by indices in {@code indexSetToAdd}.
     */
    private void addTagToPersonsAtIndices(Model model) {
        Set<Person> personSetToAdd = getPersonsAtIndices(model, indexSetToAdd);
        personSetToAdd.stream().forEach(person -> model.addPersonToTag(tagToEdit, person));
    }

    /**
     * Removes tags found in {@code tagSetToRemove} from the child-tag set of {@code tagToEdit}.
     */
    private void removeChildTagsFromTag(Model model) {
        tagSetToRemove.stream().forEach(tag -> model.removeChildTagFrom(tagToEdit, tag));
    }

    /**
     * Adds tags found in {@code tagSetToAdd} to the child-tag set of {@code tagToEdit}.
     */
    private void addChildTagsFromTag(Model model) {
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
            return other.tagToEdit.equals(tagToEdit)
                    && other.indexSetToAdd.equals(indexSetToAdd)
                    && other.indexSetToRemove.equals(indexSetToRemove)
                    && other.tagSetToAdd.equals(tagSetToAdd)
                    && other.tagSetToRemove.equals(tagSetToRemove);
        }
    }

}
