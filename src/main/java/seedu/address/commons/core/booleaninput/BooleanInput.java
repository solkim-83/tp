package seedu.address.commons.core.booleaninput;

import java.util.Set;

/**
 * Represents a boolean user input.
 *
 * {@code BooleanInput} should be used to parse user input when determining a boolean input.
 * The format of the possible {@code BooleanInput}s for {@code true} and {@code false} are specified in this class.
 * Upon receiving a {@code BooleanInput}, the {@code getBooleanValue()} method to obtain the actual boolean literal.
 */
public class BooleanInput {

    private static final Set<String> SET_TRUE_STRINGS = Set.of("t", "true", "1");
    private static final Set<String> SET_FALSE_STRINGS = Set.of("f", "false", "0");

    public static final String MESSAGE_CONSTRAINTS = "A boolean indicator must be one of the following: "
            + String.join(",", SET_TRUE_STRINGS) + " (true) "
            + String.join(",", SET_FALSE_STRINGS) + " (false)";

    private final boolean boolValue;

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
        return new BooleanInput(SET_TRUE_STRINGS.stream().findAny().get());
    }

    /**
     * Returns a default false {@code BooleanInput}.
     */
    public static BooleanInput isFalse() {
        return new BooleanInput(SET_FALSE_STRINGS.stream().findAny().get());
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
