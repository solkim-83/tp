package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time((String) null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // empty string
        assertFalse(Time.isValidTime("random")); // other strings

        // valid time
        assertTrue(Time.isValidTime("12-12-1234 12:34")); // valid standard format
        assertTrue(Time.isValidTime("20-10-2020 14:00"));
    }

    @Test
    public void getDisplayNameTest() {
        Time time = new Time("11-12-1234 12:34");
        assertEquals(time.getDisplayName().substring(0, 17), "Mon 11th Dec 1234");
    }
}
