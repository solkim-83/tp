package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.CONSULTATION;
import static seedu.address.testutil.TypicalEvents.DINNER;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.AttendeesBuilder;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(CONSULTATION));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(CONSULTATION);
        assertTrue(uniqueEventList.contains(CONSULTATION));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(CONSULTATION);
        Event editedConsultation = new EventBuilder(CONSULTATION)
                .withAttendees(new AttendeesBuilder().withPerson(ALICE).build()).build();
        assertTrue(uniqueEventList.contains(editedConsultation));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(CONSULTATION);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(CONSULTATION));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, CONSULTATION));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(CONSULTATION, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(CONSULTATION, CONSULTATION));
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(CONSULTATION);
        uniqueEventList.setEvent(CONSULTATION, CONSULTATION);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(CONSULTATION);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(CONSULTATION);
        Event editedConsultation = new EventBuilder(CONSULTATION)
                .withAttendees(new AttendeesBuilder().withPerson(ALICE).build()).build();
        uniqueEventList.setEvent(CONSULTATION, editedConsultation);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedConsultation);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(CONSULTATION);
        uniqueEventList.setEvent(CONSULTATION, DINNER);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(DINNER);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(CONSULTATION);
        uniqueEventList.add(DINNER);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(CONSULTATION, DINNER));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(CONSULTATION));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(CONSULTATION);
        uniqueEventList.remove(CONSULTATION);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(CONSULTATION);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(DINNER);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(CONSULTATION);
        List<Event> eventList = Collections.singletonList(DINNER);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(DINNER);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(CONSULTATION, CONSULTATION);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicateEvents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueEventList.asUnmodifiableObservableList().remove(0));
    }
}
