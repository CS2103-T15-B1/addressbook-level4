package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.*;

public class LongitudeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Longitude(null));
    }

    @Test
    public void constructor_invalidLongitude_throwsIllegalArgumentException() {
        String invalidLongitude = "181";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Longitude(invalidLongitude));
    }

    @Test
    public void isValidLongitude() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Longitude.isValidLongitude(null));

        // invalid gender
        assertFalse(Longitude.isValidLongitude("")); // empty string
        assertFalse(Longitude.isValidLongitude(" ")); // spaces only
        assertFalse(Longitude.isValidLongitude("^")); // only non-alphanumeric characters
        assertFalse(Longitude.isValidLongitude("M*")); // contains non-alphanumeric characters
        assertFalse(Longitude.isValidLongitude("180.1234")); // greater than 180
        assertFalse(Longitude.isValidLongitude("-180.1234")); // less than 180
        assertFalse(Longitude.isValidLongitude("--180.9")); // double negative sign
        assertFalse(Longitude.isValidLongitude("- 180.1234")); // space between sign and number


        // valid gender
        assertTrue(Longitude.isValidLongitude("20.1234")); // positive float
        assertTrue(Longitude.isValidLongitude("-20.1234")); // negative float
        assertTrue(Longitude.isValidLongitude("180")); // integers
        assertTrue(Longitude.isValidLongitude("-180")); // negative integer
    }
}