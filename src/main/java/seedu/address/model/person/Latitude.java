package seedu.address.model.person;

import java.text.DecimalFormat;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
//@@author Sivalavida
/**
 * Represents a Person's Latitude in the Retail Analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidLatitude(String)}
 */
public class Latitude {

    /**
     * The minimum allowed latitude
     */
    public static float MIN_LATITUDE = Float.valueOf("-90.0000");

    /**
     * The maximum allowed latitude
     */
    public static float MAX_LATITUDE = Float.valueOf("90.0000");

    public static final String MESSAGE_LATITUDE_CONSTRAINTS =
            String.format("Latitude numbers must be a decimal value between %f and %f", MIN_LATITUDE, MAX_LATITUDE);
    public static final String LATITUDE_VALIDATION_REGEX = "-?\\d+\\.?\\d*";
    private static final DecimalFormat format = new DecimalFormat("00.000000");

    public final String value;

    /**
     * Constructs a {@code Latitude}.
     *
     * @param latitude A valid latitude number.
     */
    public Latitude(String latitude) {
        requireNonNull(latitude);
        checkArgument(isValidLatitude(latitude), MESSAGE_LATITUDE_CONSTRAINTS);
        this.value = format.format(Double.parseDouble(latitude));
    }

    /**
     * A method to check if latitude value is valid
     * @param test the latitude to check is valid
     * @return true if test is a decimal value and within the MIN and MAX latitude
     */
    public static boolean isValidLatitude(String test) {
        if (test.matches(LATITUDE_VALIDATION_REGEX)) {
            Float latitude = Float.parseFloat(test);
            if (latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE) {
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
                || (other instanceof Latitude // instanceof handles nulls
                && this.value.equals(((Latitude) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
