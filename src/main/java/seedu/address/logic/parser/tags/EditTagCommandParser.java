package seedu.address.logic.parser.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tags.EditTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditTagCommand object.
 */
public class EditTagCommandParser implements Parser<EditTagCommand> {

    /**
     * Parses the given string {@code args} in the context of an EditTagCommand and returns it for execution.
     * A tag name must be specified under PREFIX_NAME to indicate the tag to be edited.
     * Separate sets are generated for indices of contacts to be added, indices of contacts to be removed,
     * tags to be added as child-tags, and child-tags to be removed.
     * There must be at least one optional field to edit (i.e. all sets cannot be empty).
     * @throws ParseException if any of the required conditions are not met
     */
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

        boolean isAllSetsEmpty = indexSetToAdd.isEmpty() && indexSetToRemove.isEmpty()
                && tagSetToAdd.isEmpty() && tagSetToRemove.isEmpty();
        if (isAllSetsEmpty) {
            throw new ParseException(EditTagCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTagCommand(tagToEdit, indexSetToAdd, indexSetToRemove, tagSetToAdd, tagSetToRemove);
    }
}
