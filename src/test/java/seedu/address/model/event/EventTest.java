package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_BREAKFAST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_LUNCH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.GATHERING;
import static seedu.address.testutil.TypicalEvents.LESSON;
import static seedu.address.testutil.TypicalEvents.LUNCH;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.association.FauxPerson;
import seedu.address.testutil.AttendeesBuilder;
import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> event
                .getAssociatedPersons().remove(new FauxPerson("test", 21341)));
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(GATHERING.isSameEvent(GATHERING));

        // null -> returns false
        assertFalse(GATHERING.isSameEvent(null));

        // different attendees, same description and time -> returns true
        Event editedLunch = new EventBuilder(LUNCH)
                .withAttendees(new AttendeesBuilder().withPerson(ALICE).build()).build();
        assertTrue(LUNCH.isSameEvent(editedLunch));

        // different description -> returns false
        editedLunch = new EventBuilder(LUNCH).withDescription(VALID_DESCRIPTION_BREAKFAST).build();
        assertFalse(LUNCH.isSameEvent(editedLunch));

        // different time -> returns false
        editedLunch = new EventBuilder(LUNCH).withTime(VALID_TIME_BREAKFAST).build();
        assertFalse(LUNCH.isSameEvent(editedLunch));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event gatheringCopy = new EventBuilder(GATHERING).build();
        assertTrue(GATHERING.equals(gatheringCopy));

        // same object -> returns true
        assertTrue(GATHERING.equals(GATHERING));

        // null -> returns false
        assertFalse(GATHERING.equals(null));

        // different type -> returns false
        assertFalse(GATHERING.equals(5));

        // different event -> returns false
        assertFalse(GATHERING.equals(LESSON));

        // different description -> returns false
        Event editedGathering = new EventBuilder(GATHERING).withDescription(VALID_DESCRIPTION_LUNCH).build();
        assertFalse(GATHERING.equals(editedGathering));

        // different time -> returns false
        editedGathering = new EventBuilder(GATHERING).withTime(VALID_TIME_LUNCH).build();
        assertFalse(GATHERING.equals(editedGathering));

        // different attendees -> returns false
        editedGathering = new EventBuilder(GATHERING)
                .withAttendees(new AttendeesBuilder().withPerson(ALICE).build()).build();
        assertFalse(GATHERING.equals(editedGathering));
    }
}
