package seedu.address.logic.parser.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.events.EditEventCommand;
import seedu.address.logic.commands.events.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.*;

/**
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_DATETIME, PREFIX_PERSON,
                        PREFIX_REMOVE_PERSON);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE), pe);
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editEventDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_DATETIME).get()));
        }

        parsePersonsToEdit(argMultimap.getAllValues(PREFIX_PERSON))
                .ifPresent(editEventDescriptor::setPersonsToAdd);
        parsePersonsToEdit(argMultimap.getAllValues(PREFIX_REMOVE_PERSON))
                .ifPresent(editEventDescriptor::setPersonsToAdd);

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }

    /**
     * Parses {@code Collection<String> indexes} into a {@code ArrayList<Index>}
     * if {@code indexes} is non-empty.
     */
    private Optional<ArrayList<Index>> parsePersonsToEdit(Collection<String> indexes) throws ParseException {
        requireNonNull(indexes);

        if (indexes.isEmpty()) {
            return Optional.empty();
        }

        final ArrayList<Index> indexArrayList = new ArrayList<>();
        for (String index : indexes) {
            indexArrayList.add(ParserUtil.parseIndex(index));
        }
        indexArrayList.sort((current, other) -> current.getZeroBased() - other.getOneBased());

        // TODO : test code pls delete
        System.out.println("index array list" + indexArrayList.toString());

        return Optional.of(indexArrayList);
    }
}
