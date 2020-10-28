package seedu.address.logic.parser.tags;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.tags.DeleteTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a DeleteTagCommand object.
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code args} in the context of a DeleteTagCommand and returns it for execution.
     * Looks for a tag that matches given tag name under PREFIX_TAG and deletes it.
     * If a boolean input is given and the boolean input is true, then a recursive delete is done for all sub-tags too.
     * If no boolean input is provided, the default is taken to be false.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        assert args != null;

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_RECURSIVE);

        boolean hasTagInput = argumentMultimap.getValue(PREFIX_TAG).isPresent();
        if (!hasTagInput) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        Tag tagToDelete = ParserUtil.parseTag(argumentMultimap.getValue(PREFIX_TAG).get());

        boolean hasBooleanInput = argumentMultimap.getValue(PREFIX_RECURSIVE).isPresent();
        BooleanInput isRecursive;
        if (hasBooleanInput) {
            isRecursive = ParserUtil.parseBooleanInput(argumentMultimap.getValue(PREFIX_RECURSIVE).get());
        } else {
            isRecursive = BooleanInput.isFalse();
        }

        return new DeleteTagCommand(tagToDelete, isRecursive);
    }



}
