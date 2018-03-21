package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in the retail analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Gender should only be 'M' or 'F', and it should not be blank";

    public static final String MALE_SHORTFORM = "M";
    public static final String FEMALE_SHORTFORM = "F";
    public static final String EMPTY_STRING = "";


    private enum GenderType{
        MALE, FEMALE
    }

    private GenderType gender;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender (ie "M" or "F").
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_GENDER_CONSTRAINTS);
        setGender(gender);
    }

    private void setGender(String gender) {
        if(gender.equals(MALE_SHORTFORM)){
            this.gender = GenderType.MALE;
        } else if(gender.equals(FEMALE_SHORTFORM)){
            this.gender = GenderType.FEMALE;
        }
    }

    /**
     * Returns true if a given string is a valid person gender.
     */
    public static boolean isValidGender(String test) {
        return test.equals(MALE_SHORTFORM) || test.equals(FEMALE_SHORTFORM);
    }


    @Override
    public String toString() {
        if (gender.equals(GenderType.MALE)){
            return MALE_SHORTFORM;
        } else if (gender.equals(GenderType.FEMALE)){
            return FEMALE_SHORTFORM;
        }else{
            return EMPTY_STRING;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.gender.equals(((Gender) other).gender)); // state check
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }

}
