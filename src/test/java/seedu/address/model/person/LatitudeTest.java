package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.*;

public class LatitudeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Latitude(null));
    }

    @Test
    public void constructor_invalidLatitude_throwsIllegalArgumentException() {
        String invalidLatitude = "91";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Latitude(invalidLatitude));
    }

    @Test
    public void isValidLatitude() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Latitude.isValidLatitude(null));

        // invalid gender
        assertFalse(Latitude.isValidLatitude("")); // empty string
        assertFalse(Latitude.isValidLatitude(" ")); // spaces only
        assertFalse(Latitude.isValidLatitude("^")); // only non-alphanumeric characters
        assertFalse(Latitude.isValidLatitude("M*")); // contains non-alphanumeric characters
        assertFalse(Latitude.isValidLatitude("90.1234")); // greater than 90
        assertFalse(Latitude.isValidLatitude("-90.1234")); // less than 90
        assertFalse(Latitude.isValidLatitude("--90.9")); // double negative sign
        assertFalse(Latitude.isValidLatitude("- 90.1234")); // space between sign and number


        // valid gender
        assertTrue(Latitude.isValidLatitude("20.1234")); // positive float
        assertTrue(Latitude.isValidLatitude("-20.1234")); // negative float
        assertTrue(Latitude.isValidLatitude("90")); // integers
        assertTrue(Latitude.isValidLatitude("-90")); // negative integer
    }
}