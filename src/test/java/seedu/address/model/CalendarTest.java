package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.PROJECT;
import static seedu.address.testutil.TypicalEvents.WEBINAR;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.FIONA;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.testutil.AttendeesBuilder;
import seedu.address.testutil.EventBuilder;

public class CalendarTest {

    private final Calendar calendar = new Calendar();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), calendar.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> calendar.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCalendar_replacesData() {
        Calendar newData = getTypicalCalendar();
        calendar.resetData(newData);
        assertEquals(newData, calendar);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsDuplicateEventException() {
        // Two events with the same identity fields
        Event editedMeeting = new EventBuilder(MEETING)
                .withAttendees(new AttendeesBuilder().withPerson(FIONA).build()).build();
        List<Event> newEvents = Arrays.asList(MEETING, editedMeeting);
        CalendarStub newData = new CalendarStub(newEvents);

        assertThrows(DuplicateEventException.class, () -> calendar.resetData(newData));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> calendar.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInCalendar_returnsFalse() {
        assertFalse(calendar.hasEvent(WEBINAR));
    }

    @Test
    public void hasEvent_eventInCalendar_returnsTrue() {
        calendar.addEvent(PROJECT);
        assertTrue(calendar.hasEvent(PROJECT));
    }

    @Test
    public void hasEvent_eventWithSameIdentityFieldsInCalendar_returnsTrue() {
        calendar.addEvent(PROJECT);
        Event editedProject = new EventBuilder(PROJECT)
                .withAttendees(new AttendeesBuilder().withPerson(FIONA).build()).build();
        assertTrue(calendar.hasEvent(editedProject));
    }

    @Test
    public void hasClashingEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> calendar.hasClashingEvent(null));
    }

    @Test
    public void hasClashingEvent_noEventInCalendar_returnsFalse() {
        assertFalse(calendar.hasClashingEvent(WEBINAR));
    }

    @Test
    public void hasClashingEvent_eventWithSameTimeInCalendar_returnsTrue() {
        calendar.addEvent(PROJECT);
        Event toCheck = new EventBuilder(WEBINAR).withTime(PROJECT.getTime().toString()).build();
        assertTrue(calendar.hasClashingEvent(toCheck));
    }

    @Test
    public void hasClashingEvent_eventWithDifferentTimeInCalendar_returnsFalse() {
        calendar.addEvent(PROJECT);
        assertFalse(calendar.hasClashingEvent(WEBINAR));
    }

    @Test
    public void deletePersonAssociation_personAssociated() {
        Event eventWithoutAlice = new EventBuilder().build();
        Calendar expectedCalendar = new Calendar();
        expectedCalendar.addEvent(eventWithoutAlice);

        Event eventWithAlice = new EventBuilder()
                .withAttendees(new AttendeesBuilder().withPerson(ALICE).build()).build();
        calendar.addEvent(eventWithAlice);
        calendar.deletePersonAssociation(ALICE);

        assertTrue(calendar.equals(expectedCalendar));
    }

    @Test
    public void deletePersonAssociation_noPersonAssociated() {
        Event eventWithBob = new EventBuilder()
                .withAttendees(new AttendeesBuilder().withPerson(BOB).build()).build();
        Calendar expectedCalendar = new Calendar();
        expectedCalendar.addEvent(eventWithBob);

        calendar.addEvent(eventWithBob);
        calendar.deletePersonAssociation(ALICE);

        assertTrue(calendar.equals(expectedCalendar));
    }

    @Test
    public void setPersonAssociation_personAssociated() {
        Event eventWithBob = new EventBuilder()
                .withAttendees(new AttendeesBuilder().withPerson(BOB).build()).build();
        Calendar expectedCalendar = new Calendar();
        expectedCalendar.addEvent(eventWithBob);

        Event eventWithAlice = new EventBuilder()
                .withAttendees(new AttendeesBuilder().withPerson(ALICE).build()).build();
        calendar.addEvent(eventWithAlice);
        calendar.setPersonAssociation(ALICE, BOB);

        assertTrue(calendar.equals(expectedCalendar));
    }

    @Test
    public void setPersonAssociation_noPersonAssociated() {
        Event eventWithBob = new EventBuilder()
                .withAttendees(new AttendeesBuilder().withPerson(BOB).build()).build();
        Calendar expectedCalendar = new Calendar();
        expectedCalendar.addEvent(eventWithBob);

        calendar.addEvent(eventWithBob);
        calendar.setPersonAssociation(ALICE, BOB);

        assertTrue(calendar.equals(expectedCalendar));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> calendar.getEventList().remove(0));
    }

    /**
     * A stub ReadOnlyCalendar whose events list can violate interface constraints.
     */
    private static class CalendarStub implements ReadOnlyCalendar {
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        CalendarStub(Collection<Event> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }
}
