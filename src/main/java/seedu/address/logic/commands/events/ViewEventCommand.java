package seedu.address.logic.commands.events;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.association.FauxPerson;
import seedu.address.model.person.Person;


/**
 * Shows the full detail of an event identified using its displayed index from the Athena.
 */
public class ViewEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.VIEW.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Shows the full details of the event "
            + "identified by the index number used in the displayed event list.\n\n"
            + "Parameters: INDEX (must be positive a integer)\n\n"
            + "Examples:\n"
            + COMMAND_WORD + " " + COMMAND_TYPE + " 1" + "\n"
            + COMMAND_WORD + " " + COMMAND_TYPE + " 2";

    public static final String MESSAGE_EVENT_VIEW_SUCCESS = "Here are the details: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = " (Not found in contact list)";

    private final Index targetIndex;

    /**
     * @param targetIndex of the event in the filtered event list to view
     */
    public ViewEventCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getSortedFilteredEventList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        Event specifiedEvent = lastShownList.get(targetIndex.getZeroBased());

        StringBuilder builder = new StringBuilder();
        builder.append("\n" + specifiedEvent.getDescription())
                .append("\nAt: ")
                .append(specifiedEvent.getTime().getDisplayName());

        ArrayList<FauxPerson> fauxPersonArrayList = new ArrayList<>(specifiedEvent.getAssociatedPersons());
        fauxPersonArrayList.sort(Comparator.comparing(current -> current.displayName));
        ObservableList<Person> personList = model.getAddressBook().getPersonList();

        for (FauxPerson fauxPerson : fauxPersonArrayList) {
            boolean found = false;
            for (Person person : personList) {
                if (person.hashCode() == fauxPerson.personHashCode) {
                    builder.append("\n\n" + person.toString());
                    found = true;
                    break;
                }
            }
            if (!found) {
                builder.append("\n\n" + fauxPerson.toString() + MESSAGE_PERSON_NOT_FOUND);
            }
        }

        return new CommandResult(String.format(MESSAGE_EVENT_VIEW_SUCCESS, builder.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewEventCommand // instanceof handles nulls
                && targetIndex.equals(((ViewEventCommand) other).targetIndex)); // state check
    }
}
