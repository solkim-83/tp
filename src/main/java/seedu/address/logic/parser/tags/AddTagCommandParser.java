package seedu.address.logic.parser.tags;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tags.AddTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AddTagCommandParser {

    public AddTagCommand parse(String args) throws ParseException {
        assert args != null;

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX, PREFIX_TAG);

        boolean hasNoSpecifiedNewTagName = argumentMultimap.getValue(PREFIX_NAME).isEmpty();
        if (hasNoSpecifiedNewTagName) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        boolean areIndicesEmpty = argumentMultimap.getAllValues(PREFIX_INDEX).isEmpty();
        boolean areTagsEmpty = argumentMultimap.getAllValues(PREFIX_TAG).isEmpty();
        if (areIndicesEmpty && areTagsEmpty) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_FAILURE));
        }

        Tag tagToAdd = ParserUtil.parseTag(argumentMultimap.getValue(PREFIX_TAG).get());

        Set<Index> indicesSet = new HashSet<>();
        List<String> listOfStringIndices = argumentMultimap.getAllValues(PREFIX_INDEX);
        for (String stringIndex : listOfStringIndices) {
            indicesSet.add(ParserUtil.parseIndex(stringIndex));
        }

        Set<Tag> tagSet = ParserUtil.parseTags(argumentMultimap.getAllValues(PREFIX_TAG));

        return new AddTagCommand(tagToAdd, tagSet, indicesSet);

    }


}
