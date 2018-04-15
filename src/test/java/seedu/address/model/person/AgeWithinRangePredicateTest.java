package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AgeWithinRangePredicateTest {

    Age TEN_YEARS_OLD = new Age("10");
    Age TWENTY_YEARS_OLD = new Age("20");
    Age THIRTY_YEARS_OLD = new Age("30");

    @Test
    public void equals() {

        AgeWithinRangePredicate firstPredicate = new AgeWithinRangePredicate(TEN_YEARS_OLD, TWENTY_YEARS_OLD);
        AgeWithinRangePredicate firstPredicateCopy = new AgeWithinRangePredicate(TEN_YEARS_OLD, TWENTY_YEARS_OLD);
        AgeWithinRangePredicate secondPredicate = new AgeWithinRangePredicate(TEN_YEARS_OLD, THIRTY_YEARS_OLD);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(10));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different age range -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ageWithinRange_returnsTrue() {
        // Strictly withing range
        AgeWithinRangePredicate predicate = new AgeWithinRangePredicate(TEN_YEARS_OLD, TWENTY_YEARS_OLD);
        assertTrue(predicate.test(new PersonBuilder().withAge("15").build()));

        // Age equal to left boundary
        assertTrue(predicate.test(new PersonBuilder().withAge("10").build()));

        // Age equal to left boundary
        assertTrue(predicate.test(new PersonBuilder().withAge("20").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        //Age above upper limit
        AgeWithinRangePredicate predicate = new AgeWithinRangePredicate(TEN_YEARS_OLD, TWENTY_YEARS_OLD);
        assertFalse(predicate.test(new PersonBuilder().withAge("35").build()));

        // Age equal to left boundary
        assertFalse(predicate.test(new PersonBuilder().withAge("2").build()));

        //Age range reversed
        predicate = new AgeWithinRangePredicate(TWENTY_YEARS_OLD, TEN_YEARS_OLD);
        assertFalse(predicate.test(new PersonBuilder().withAge("2").build()));
    }
}

