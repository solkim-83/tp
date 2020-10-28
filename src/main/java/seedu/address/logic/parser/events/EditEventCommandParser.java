package seedu.address.logic.parser.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_PERSON;
import static seedu.address.logic.parser.CliSyntax.SYMBOL_WILDCARD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.events.EditEventCommand;
import seedu.address.logic.commands.events.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_DATETIME, PREFIX_ADD_PERSON,
                        PREFIX_REMOVE_PERSON);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE), pe);
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        // set description for editEventDescriptor
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editEventDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        // set time for editEventDescriptor
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editEventDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_DATETIME).get()));
        }

        // set index of persons to add/remove for editEventDescriptor
        if (argMultimap.getValue(PREFIX_ADD_PERSON).isPresent()) {
            if (argMultimap.getValue(PREFIX_ADD_PERSON).get().equals(SYMBOL_WILDCARD)) {
                editEventDescriptor.setWildCardAdd();
            } else {
                editEventDescriptor.setPersonsToAdd(
                        ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_ADD_PERSON).get()));
            }
        }
        if (argMultimap.getValue(PREFIX_REMOVE_PERSON).isPresent()) {
            if (argMultimap.getValue(PREFIX_REMOVE_PERSON).get().equals(SYMBOL_WILDCARD)) {
                editEventDescriptor.setWildCardRemove();
            } else {
                editEventDescriptor.setPersonsToRemove(
                        ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_REMOVE_PERSON).get()));
            }
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }
}
