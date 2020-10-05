package seedu.address.model.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.UniquePersonList;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;

public class UniqueEventList implements Iterable<Event> {
    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void add(Event toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        internalList.remove(toRemove);
    }

    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
