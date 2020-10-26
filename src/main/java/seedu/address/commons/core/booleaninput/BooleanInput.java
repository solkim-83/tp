package seedu.address.commons.core.booleaninput;

import java.util.Set;

public class BooleanInput {

    private static final Set<String> SET_TRUE_STRINGS = Set.of("t", "true", "1");
    private static final Set<String> SET_FALSE_STRINGS = Set.of("f", "false", "0");

    private final boolean boolValue;

    private BooleanInput(String input) {
        boolean isValidInput = SET_TRUE_STRINGS.contains(input) || SET_FALSE_STRINGS.contains(input);
        if (!isValidInput) {
            throw new IllegalArgumentException("Not a valid boolean input!");
        }

        boolValue = SET_TRUE_STRINGS.contains(input);
    }

    public static BooleanInput ofInput(String input) {
        return new BooleanInput(input);
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
