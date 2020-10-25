package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.logic.commands.events.AddEventCommand;
import seedu.address.logic.commands.events.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Event;


/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddCommand(Event event) {
        return AddEventCommand.COMMAND_WORD + " " + AddEventCommand.COMMAND_TYPE + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION).append(event.getDescription().fullDescription).append(" ");
        sb.append(PREFIX_DATETIME).append(event.getTime().toString()).append(" ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description ->
                sb.append(PREFIX_DESCRIPTION).append(description.fullDescription).append(" "));
        descriptor.getTime().ifPresent(time -> sb.append(PREFIX_DATETIME).append(time.toString()).append(" "));

        return sb.toString();
    }

}
