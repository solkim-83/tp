package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Deletes the specified tag from the system.
 * If {@code isRecursive} is true, then all sub-tags of the tag is deleted as well.
 * If {@code isRecursive} is false, then all parent-tags of {@code tagToDelete} will be reconnected with the set of
 * child-tags of {@code tagToDelete}.
 * Parent tags of the {@code tagToDelete} that do not have any contacts tagged and have {@code tagToDelete}
 * as the only child-tag will also be deleted.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.DELETE.toString();
    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_INVALID_TAG = "Tag does not exist!";
    public static final String MESSAGE_DELETE_SUCCESS = "%s has been deleted!";
    public static final String MESSAGE_DELETE_RECURSIVE_SUCCESS = "%s and sub-tags have been deleted!\n\n"
            + "Deleted sub-tags are %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE + " "
            + ": Deletes a tag. If the " + PREFIX_RECURSIVE + " field is 1, it also "
            + "deletes all sub-tags.\n\n"
            + "Parameters:\n"
            + PREFIX_TAG + "TAG\n"
            + "[" + PREFIX_RECURSIVE + "BOOLEAN]\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " " + PREFIX_TAG + "CS2103 "
            + PREFIX_RECURSIVE + "true";

    private final Tag tagToDelete;
    private final BooleanInput isRecursive;

    /**
     * Creates a DeleteTagCommand object with the specified {@code tagToDelete} and
     * {@code isRecursive} (for determining if the sub-tags should be deleted).
     */
    public DeleteTagCommand(Tag tagToDelete, BooleanInput isRecursive) {
        this.tagToDelete = tagToDelete;
        this.isRecursive = isRecursive;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTag(tagToDelete)) {
            throw new CommandException(MESSAGE_INVALID_TAG);
        }

        if (isRecursive.getBooleanValue()) {
            Set<Tag> tagSetRecursiveToBeDeleted = model.getSubTagsRecursive(tagToDelete);
            model.deleteTagRecursive(tagToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_RECURSIVE_SUCCESS, tagToDelete,
                    tagSetRecursiveToBeDeleted));
        } else {
            model.deleteTag(tagToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, tagToDelete));
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DeleteTagCommand)) {
            return false;
        } else {
            DeleteTagCommand other = (DeleteTagCommand) o;
            return other.tagToDelete.equals(tagToDelete)
                    && other.isRecursive.equals(isRecursive);
        }
    }
}
