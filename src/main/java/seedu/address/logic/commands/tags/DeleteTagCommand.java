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

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE + " "
            + ": Deletes a tag. If the " + PREFIX_RECURSIVE + " field is 1, it also "
            + "deletes all sub-tags.\n"
            + "Parameters: "
            + PREFIX_TAG + "TAG "
            + PREFIX_RECURSIVE + "BOOLEAN\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " " + PREFIX_TAG + "CS2103 "
            + PREFIX_RECURSIVE + "true";

    private final Tag tagToDelete;
    private final BooleanInput bool;

    public DeleteTagCommand(Tag tagToDelete, BooleanInput bool) {
        this.tagToDelete = tagToDelete;
        this.bool = bool;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

    }
}