package seedu.address.commons.core.booleaninput;

import java.util.List;

/**
 * Represents a boolean user input.
 *
 * {@code BooleanInput} should be used to parse user input when determining a boolean input.
 * The format of the possible {@code BooleanInput}s for {@code true} and {@code false} are specified in this class.
 * Upon receiving a {@code BooleanInput}, use the {@code getBooleanValue()} method to obtain the actual boolean literal.
 */
public class BooleanInput {

    private static final List<String> SET_TRUE_STRINGS = List.of("t", "true", "1");
    private static final List<String> SET_FALSE_STRINGS = List.of("f", "false", "0");

    private static final BooleanInput BOOLEAN_TRUE = new BooleanInput(true);
    private static final BooleanInput BOOLEAN_FALSE = new BooleanInput(false);

    public static final String MESSAGE_CONSTRAINTS = "A boolean indicator must be one of the following:\n"
            + "[true]: " + String.join(", ", SET_TRUE_STRINGS) + "\n"
            + "[false]: " + String.join(", ", SET_FALSE_STRINGS);

    private final boolean boolValue;

    /**
     * Returns a BooleanInput with the given boolean value.
     * For internal usage only to create static true and false BooleanInputs.
     */
    private BooleanInput(boolean boolValue) {
        this.boolValue = boolValue;
    }

    /**
     * Private constructor for accepting string inputs.
     */
    private BooleanInput(String input) {
        if (!isValidBooleanInput(input)) {
            throw new IllegalArgumentException("Not a valid boolean input!");
        }

        boolValue = SET_TRUE_STRINGS.contains(input);
    }

    /**
     * Returns true if the {@code input} matches any of the Strings in the true set or the false set.
     */
    public static boolean isValidBooleanInput(String input) {
        return SET_TRUE_STRINGS.contains(input) || SET_FALSE_STRINGS.contains(input);
    }

    /**
     * Returns a new {@code BooleanInput} object based on the user input.
     */
    public static BooleanInput ofInput(String input) {
        return new BooleanInput(input.toLowerCase());
    }

    /**
     * Returns a default true {@code BooleanInput}.
     */
    public static BooleanInput isTrue() {
        return BOOLEAN_TRUE;
    }

    /**
     * Returns a default false {@code BooleanInput}.
     */
    public static BooleanInput isFalse() {
        return BOOLEAN_FALSE;
    }

    /**
     * Returns the boolean literal value of {@code BooleanInput}.
     */
    public boolean getBooleanValue() {
        return boolValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BooleanInput)) {
            return false;
        } else {
            return boolValue == ((BooleanInput) o).boolValue;
        }
    }

}
