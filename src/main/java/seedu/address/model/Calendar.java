package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

public class Calendar implements ReadOnlyCalendar {
    private final UniqueEventList events;

    {
        events = new UniqueEventList();
    }

    public Calendar() {}

    public void addEvent(Event e) {
        events.add(e);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calendar // instanceof handles nulls
                && events.equals(((Calendar) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
