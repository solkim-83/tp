package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.model.event.association.FauxPerson;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    private static final String MESSAGE_PERSON_LIMIT_REACHED = "... %d more attendee(s)";

    // TODO: perhaps shift this out to UserPrefs so it can be modified from there instead
    private static final int associatedPersonsLimit = 6;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label time;

    // do not understand why, but when the following field is named anything else with the corresponding change
    // to EventListCard.fxml, the GUI breaks and the blue boxes are no longer shown around the display name
    // TODO: fix problem mentioned right above, or maybe not
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code EventCode} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        description.setText(event.getDescription().fullDescription);
        time.setText(event.getTime().getDisplayName());
        setAssociatedPersons(event);
    }

    /**
     * Sets the associated persons in this card to be from a event.
     * Associated persons are sorted by alphabetic order.
     * @param event Associated persons is taken from this event.
     */
    private void setAssociatedPersons(Event event) {
        ArrayList<FauxPerson> associatedPersons = new ArrayList<>();
        associatedPersons.addAll(event.getAssociatedPersons());
        /* Sorted by alphabetical order.
         * This needs to sync with the sorting of associated persons before modification in EditEvent class.
         */
        associatedPersons.sort(Comparator.comparing(current -> current.displayName));

        int numOfDisplayedNames = 0;
        for (FauxPerson fauxPerson : associatedPersons) {
            if (numOfDisplayedNames < associatedPersonsLimit) {
                tags.getChildren().add(new Label(fauxPerson.displayName));
                numOfDisplayedNames++;
            } else {
                assert numOfDisplayedNames >= associatedPersonsLimit;
                tags.getChildren().add(new Label(String.format(MESSAGE_PERSON_LIMIT_REACHED,
                        associatedPersons.size() - associatedPersonsLimit)));
                break;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
