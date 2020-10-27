package seedu.address.logic.comparators;

import java.util.Comparator;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;

public class EventComparator {

    private static final Comparator<Event> DESCRIPTION_COMPARATOR = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            return o1.getDescription().fullDescription.compareToIgnoreCase(o2.getDescription().fullDescription);
        }
    };

    private static final Comparator<Event> TIME_COMPARATOR = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            return o1.getTime().time.compareTo(o2.getTime().time);
        }
    };


    /**
     * Returns the appropriate comparator for the model manager to sort with
     */
    public static Comparator<Event> chooseComparator(Index index) throws CommandException {
        int input = index.getOneBased();
        switch (input) {
            case 1:
                return DESCRIPTION_COMPARATOR;
            case 2:
                return TIME_COMPARATOR;
            default:
                throw new CommandException("Index should be either 1 or 2!");
        }
    }
}
