package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.*;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "q";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("^")); // only non-alphanumeric characters
        assertFalse(Gender.isValidGender("M*")); // contains non-alphanumeric characters

        // valid gender
        assertTrue(Gender.isValidGender("M")); // male in uppercase
        assertTrue(Gender.isValidGender("m")); // male in lowercase
        assertTrue(Gender.isValidGender("F")); // female in uppercase
        assertTrue(Gender.isValidGender("f")); // female in lowercase
    }
}