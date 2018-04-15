package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
//@@author Sivalavida
/**
 * Tests that a {@code Person}'s {@code Age} is within the range given.
 */
public class GenderPredicate implements Predicate<Person> {
    private final Gender gender;

    public GenderPredicate(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean test(Person person) {
        return (gender.equals(person.getGender()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenderPredicate // instanceof handles nulls
                && this.gender == ((GenderPredicate) other).gender); // state check
    }

}
