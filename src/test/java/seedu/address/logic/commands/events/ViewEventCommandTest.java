package seedu.address.logic.commands.events;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.events.ViewEventCommand.MESSAGE_EVENT_VIEW_SUCCESS;
import static seedu.address.logic.commands.events.ViewEventCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.association.FauxPerson;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelManagerBuilder;

public class ViewEventCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManagerBuilder().withCalendar(getTypicalCalendar()).build();
        expectedModel = new ModelManagerBuilder().withCalendar(model.getCalendar()).build();
    }

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewEventCommand(null));
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        ViewEventCommand viewEventCommand = new ViewEventCommand(Index.fromZeroBased(99));
        assertCommandFailure(viewEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validEventIndex_success() {
        ViewEventCommand viewEventCommand = new ViewEventCommand(Index.fromZeroBased(0));
        Event specifiedEvent = model.getSortedFilteredEventList().get(0);

        // Below is copied from ViewEventCommand.execute
        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append("\n" + specifiedEvent.getDescription())
                .append("\nAt: ")
                .append(specifiedEvent.getTime().getDisplayName());

        ArrayList<FauxPerson> fauxPersonArrayList = new ArrayList<>(specifiedEvent.getAssociatedPersons());
        fauxPersonArrayList.sort(Comparator.comparing(current -> current.displayName));
        ObservableList<Person> personList = model.getAddressBook().getPersonList();

        for (FauxPerson fauxPerson : fauxPersonArrayList) {
            boolean found = false;
            for (Person person : personList) {
                if (person.hashCode() == fauxPerson.personHashCode) {
                    expectedMessage.append("\n\n" + person.toString());
                    found = true;
                    break;
                }
            }
            if (!found) {
                expectedMessage.append("\n\n" + fauxPerson.toString() + MESSAGE_PERSON_NOT_FOUND);
            }
        }

        assertCommandSuccess(viewEventCommand, model,
                String.format(MESSAGE_EVENT_VIEW_SUCCESS, expectedMessage.toString()), expectedModel);
    }

}
