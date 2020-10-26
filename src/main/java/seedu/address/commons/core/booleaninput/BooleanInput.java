package seedu.address.commons.core.booleaninput;

import java.util.Set;

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

    public static boolean isValidBooleanInput(String input) {
        return SET_TRUE_STRINGS.contains(input) || SET_FALSE_STRINGS.contains(input);
    }

    public static BooleanInput ofInput(String input) {
        return new BooleanInput(input.toLowerCase());
    }

    public static BooleanInput isTrue() {
        return new BooleanInput("true");
    }

    public static BooleanInput isFalse() {
        return new BooleanInput("false");
    }

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
