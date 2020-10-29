package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.association.FauxPerson;

public class JsonAdaptedFauxPerson {

    private static final String regex = " /break/ ";

    private final String displayName;
    private final String hashCode;

    /**
     * Constructs a {@code JsonAdaptedFauxPerson} with the given data.
     */
    @JsonCreator
    public JsonAdaptedFauxPerson(String data) {
        String[] info = data.split(regex, 2);
        displayName = info[0];
        hashCode = info[1];
    }

    /**
     * Converts a given {@code FauxPerson} into this class for Jackson use.
     */
    public JsonAdaptedFauxPerson(FauxPerson source) {
        displayName = source.displayName;
        hashCode = source.personHashCode.toString();
    }

    @JsonValue
    public String getData() {
        return displayName + regex + hashCode;
    }

    /**
     * Converts this Jackson-friendly adapted FauxPerson object into the model's {@code FauxPerson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted FauxPerson.
     */
    public FauxPerson toModelType() throws IllegalValueException {
        if (!FauxPerson.isValidDisplayName(displayName)) {
            throw new IllegalValueException(FauxPerson.MESSAGE_CONSTRAINTS_NAME);
        }

        if (!FauxPerson.isValidHashCode(hashCode)) {
            throw new IllegalValueException(FauxPerson.MESSAGE_CONSTRAINTS_HASHCODE);
        }
        return new FauxPerson(displayName, Integer.parseInt(hashCode));
    }
}
