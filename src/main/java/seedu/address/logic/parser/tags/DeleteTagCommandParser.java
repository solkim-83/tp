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

public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

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
