package seedu.address.model.person;

import java.text.DecimalFormat;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Latitude in the Retail Analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidLongitude(String)}
 */
public class Longitude {

    /**
     * The minimum allowed longitude
     */
    public static float MIN_LONGITUDE = Float.valueOf("-180.0000");

    /**
     * The maximum allowed longitude
     */
    public static float MAX_LONGITUDE = Float.valueOf("180.0000");

    public static final String MESSAGE_LONGITUDE_CONSTRAINTS =
            String.format("Latitude numbers must be a decimal value between %f and %f", MIN_LONGITUDE, MAX_LONGITUDE);
    public static final String LONGITUDE_VALIDATION_REGEX = "-?\\d+\\.?\\d*";
    private static final DecimalFormat format = new DecimalFormat("000.000000");

    public final String value;

    /**
     * Constructs a {@code Longitude}.
     *
     * @param longitude A valid longitude number.
     */
    public Longitude(String longitude) {
        requireNonNull(longitude);
        checkArgument(isValidLongitude(longitude), MESSAGE_LONGITUDE_CONSTRAINTS);
        this.value = format.format(Double.parseDouble(longitude));
    }

    /**
     * A method to validate a longitude value
     * @param test the longitude to check is valid
     * @return true if test is a decimal value and within the MIN and MAX longitude
     */
    public static boolean isValidLongitude(String test) {
        if (test.matches(LONGITUDE_VALIDATION_REGEX)) {
            Float longitude = Float.parseFloat(test);
            if (longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Longitude // instanceof handles nulls
                && this.value.equals(((Longitude) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
