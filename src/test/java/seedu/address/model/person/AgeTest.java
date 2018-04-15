package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.*;
//@@author Sivalavida
public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "121";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid gender
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only
        assertFalse(Age.isValidAge("^")); // only non-alphanumeric characters
        assertFalse(Age.isValidAge("M*")); // contains non-alphanumeric characters
        assertFalse(Age.isValidAge("90.1234")); // decimal values
        assertFalse(Age.isValidAge("-90")); // negative values


        // valid gender
        assertTrue(Age.isValidAge("2")); //integer with one digit
        assertTrue(Age.isValidAge("20")); //integer with two digit
        assertTrue(Age.isValidAge("110")); //integer with three digit
    }
}