package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
//@@author Sivalavida
/**
 * Tests that a {@code Person}'s {@code Age} is within the range given.
 */
public class AgeWithinRangePredicate implements Predicate<Person> {
    private final Age minimumAge;
    private final Age maximumAge;

    public AgeWithinRangePredicate(Age mininumAge, Age maximumAge) {
        this.minimumAge = mininumAge;
        this.maximumAge = maximumAge;
    }

    @Override
    public boolean test(Person person) {
        return (minimumAge.compareTo(person.getAge())>=0) &&
                (maximumAge.compareTo(person.getAge())<=0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AgeWithinRangePredicate // instanceof handles nulls
                && this.maximumAge == ((AgeWithinRangePredicate) other).maximumAge
                && this.maximumAge == ((AgeWithinRangePredicate) other).maximumAge); // state check
    }

}
