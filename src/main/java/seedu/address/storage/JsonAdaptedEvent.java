package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Time;
import seedu.address.model.event.association.FauxPerson;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String description;
    private final String time;
    private final List<JsonAdaptedFauxPerson> associatedPersons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("description") String description,
                            @JsonProperty("time") String time,
                            @JsonProperty("associatedPersons") List<JsonAdaptedFauxPerson> associatedPersons) {
        this.description = description;
        this.time = time;
        if (associatedPersons != null) {
            this.associatedPersons.addAll(associatedPersons);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        description = source.getDescription().fullDescription;
        time = source.getTime().toString();
        associatedPersons.addAll(source.getAssociatedPersons().stream()
                .map(JsonAdaptedFauxPerson::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {

        // description
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        // time
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        // associatedPersons
        final List<FauxPerson> attendees = new ArrayList<>();
        for (JsonAdaptedFauxPerson fauxPerson : associatedPersons) {
            attendees.add(fauxPerson.toModelType());
        }
        final Set<FauxPerson> modelAssociatedPersons = new HashSet<>(attendees);
        return new Event(modelDescription, modelTime, modelAssociatedPersons);
    }
}
