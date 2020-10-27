package seedu.address.logic.parser.tags;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tags.EditTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class EditTagCommandParser implements Parser<EditTagCommand> {

    @Override
    public EditTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX, PREFIX_REMOVE_INDEX,
                        PREFIX_TAG, PREFIX_REMOVE_TAG);

        boolean hasTagSpecified = argumentMultimap.getValue(PREFIX_NAME).isPresent();
        if (!hasTagSpecified) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
        }
        Tag tagToEdit = ParserUtil.parseTag(argumentMultimap.getValue(PREFIX_NAME).get());

        Set<Index> indexSetToAdd = ParserUtil.parseIndices(argumentMultimap.getAllValues(PREFIX_INDEX));
        Set<Index> indexSetToRemove = ParserUtil.parseIndices(argumentMultimap.getAllValues(PREFIX_REMOVE_INDEX));

        Set<Tag> tagSetToAdd = ParserUtil.parseTags(argumentMultimap.getAllValues(PREFIX_TAG));
        Set<Tag> tagSetToRemove = ParserUtil.parseTags(argumentMultimap.getAllValues(PREFIX_REMOVE_TAG));

        return new EditTagCommand(tagToEdit, indexSetToAdd, indexSetToRemove, tagSetToAdd, tagSetToRemove);
    }
}
