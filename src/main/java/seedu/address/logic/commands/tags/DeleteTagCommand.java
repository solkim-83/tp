package seedu.address.logic.commands.tags;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.DELETE.toString();
    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_INVALID_TAG = "Tag does not exist!";
    public static final String MESSAGE_DELETE_SUCCESS = "%s has been deleted!";
    public static final String MESSAGE_DELETE_RECURSIVE_SUCCESS = "%s and sub-tags have been deleted!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE + " "
            + ": Deletes a tag. If the " + PREFIX_RECURSIVE + " field is 1, it also "
            + "deletes all sub-tags.\n\n"
            + "Parameters:\n"
            + PREFIX_TAG + "TAG\n"
            + PREFIX_RECURSIVE + "BOOLEAN\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " " + PREFIX_TAG + "CS2103 "
            + PREFIX_RECURSIVE + "true";

    private final Tag tagToDelete;
    private final BooleanInput isRecursive;

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
            model.deleteTagRecursive(tagToDelete);
            return new CommandResult(MESSAGE_DELETE_RECURSIVE_SUCCESS);
        } else {
            model.deleteTag(tagToDelete);
            return new CommandResult(MESSAGE_DELETE_SUCCESS);
        }

    }
}