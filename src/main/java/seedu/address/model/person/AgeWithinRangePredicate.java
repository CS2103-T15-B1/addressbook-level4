package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * @author Sivalavida
 * Tests that a {@code Person}'s {@code Age} is within the range given.
 */
public class AgeWithinRangePredicate implements Predicate<Person> {
    private final int minimumAge;
    private final int maximumAge;

    public AgeWithinRangePredicate(int mininumAge, int maximumAge) {
        this.minimumAge = mininumAge;
        this.maximumAge = maximumAge;
    }

    @Override
    public boolean test(Person person) {
        return (minimumAge <= person.getAge().getNumericalAge()) &&
                (maximumAge >= person.getAge().getNumericalAge());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AgeWithinRangePredicate // instanceof handles nulls
                && this.maximumAge == ((AgeWithinRangePredicate) other).maximumAge
                && this.maximumAge == ((AgeWithinRangePredicate) other).maximumAge); // state check
    }

}
