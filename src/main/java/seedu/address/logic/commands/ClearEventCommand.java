package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Calendar;
import seedu.address.model.Model;

/**
 * Clears the calendar.
 */
public class ClearEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.CLEAR.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_SUCCESS = "The Calendar has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCalendar(new Calendar());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
