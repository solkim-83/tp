package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearContactCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IntroCommand;
import seedu.address.logic.commands.ListContactCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
            "(?<commandWord>\\S+)\\s*(?<commandType>[\\S]*)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final CommandWord commandWord = CommandWord.get(matcher.group("commandWord"));
        final CommandType commandType = CommandType.get(matcher.group("commandType"));
        final String arguments = matcher.group("arguments");

        switch (commandType) {

        case CONTACT:

            switch (commandWord) {

            case ADD:
                return new AddContactCommandParser().parse(arguments);

            case EDIT:
                return new EditContactCommandParser().parse(arguments);

            case CLEAR:
                return new ClearContactCommand();

            case DELETE:
                return new DeleteCommandParser().parse(arguments);

            case FIND:
                return new FindContactCommandParser().parse(arguments);

            case LIST:
                return new ListContactCommand();

            case SORT:
                return new SortContactCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case EVENT:

            switch (commandWord) {

            case ADD:
                return new AddEventCommandParser().parse(arguments);

            case EDIT:
                return new EditEventCommandParser().parse(arguments);

            case DELETE:
                return new DeleteEventCommandParser().parse(arguments);

            case FIND:
                return new FindEventCommandParser().parse(arguments);

            case LIST:
                return new ListEventCommand();

            case SORT:
                return new SortEventCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        case PermaSortCommand.COMMAND_WORD:
            return new PermaSortCommandParser().parse(arguments);

        default:

            switch (commandWord) {

            case EXIT:
                return new ExitCommand();

            case HELP:
                return new HelpCommand();

            case INTRO:
                return new IntroCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }

}
