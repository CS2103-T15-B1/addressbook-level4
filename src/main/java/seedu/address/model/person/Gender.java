package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's gender in the retail analytics.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Gender {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Gender should only be 'M' or 'F', and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String gender;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Gender(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.gender = name;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return gender;
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
