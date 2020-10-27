package seedu.address.commons.core.booleaninput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BooleanInputTest {

    private static final String INPUT_BOOLEAN_VALID_TRUE = "true";
    private static final String INPUT_BOOLEAN_VALID_FALSE = "f";
    private static final String INPUT_BOOLEAN_INVALID = "not valid";
    private static final String INPUT_BOOLEAN_EMPTY = "    ";

    private static final BooleanInput BOOLEAN_TRUE = BooleanInput.isTrue();
    private static final BooleanInput BOOLEAN_FALSE = BooleanInput.isFalse();

    @Test
    public void isValidBooleanInput() {

        // valid input
        assertTrue(BooleanInput.isValidBooleanInput(INPUT_BOOLEAN_VALID_TRUE));
        assertTrue(BooleanInput.isValidBooleanInput(INPUT_BOOLEAN_VALID_FALSE));

        // invalid input
        assertFalse(BooleanInput.isValidBooleanInput(INPUT_BOOLEAN_INVALID));
        assertFalse(BooleanInput.isValidBooleanInput(INPUT_BOOLEAN_EMPTY));
    }

    @Test
    public void ofInput() {
        assertEquals(BOOLEAN_TRUE, BooleanInput.ofInput(INPUT_BOOLEAN_VALID_TRUE));
        assertEquals(BOOLEAN_FALSE, BooleanInput.ofInput(INPUT_BOOLEAN_VALID_FALSE));

        assertThrows(IllegalArgumentException.class, () -> BooleanInput.ofInput(INPUT_BOOLEAN_INVALID));
    }



}
