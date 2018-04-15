package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
//@@author Sivalavida
/**
 * Represents a Person's gender in the retail analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Gender should only be 'M' or 'F', and it should not be blank";

    public static final String MALE_SHORTFORM = "M";
    public static final String FEMALE_SHORTFORM = "F";

    public String value;//either MALE_SHORTFORM or FEMALE_SHORTFORM

    /**
     * Constructs a {@code Gender}.
     * @param gender A valid gender (ie "M" or "F") (case insensitive).
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_GENDER_CONSTRAINTS);
        setGender(gender);
    }

    /**
     * Sets {@code value} to the uppercase value of {@code gender}
     * @param gender a valid gender
     */
    private void setGender(String gender) {
        assert isValidGender(gender);

        String genderUpperCase = gender.toUpperCase();
        if(genderUpperCase.equals(MALE_SHORTFORM)){
            value = MALE_SHORTFORM;
        } else if(genderUpperCase.equals(FEMALE_SHORTFORM)){
            value = FEMALE_SHORTFORM;
        }
    }

    /**
     * Returns true if a given string is a valid person gender.
     */
    public static boolean isValidGender(String test) {
        String testUpperCase = test.toUpperCase();
        return testUpperCase.equals(MALE_SHORTFORM) || testUpperCase.equals(FEMALE_SHORTFORM);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
