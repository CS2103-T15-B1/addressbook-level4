package seedu.address.model.product;

import org.junit.Test;
import seedu.address.model.money.Money;
import seedu.address.model.person.Age;
import seedu.address.model.person.AgeWithinRangePredicate;
import seedu.address.testutil.PersonBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//@@author lowjiajin
public class ProductCostsBetweenPredicateTest {

    Money TEN_DOLLARS = new Money(new BigDecimal(10));
    Money TWENTY_DOLLARS = new Money(new BigDecimal(20));
    Money THIRTY_DOLLARS = new Money(new BigDecimal(30));

    @Test
    public void equals() {

        ProductCostsBetweenPredicate firstPredicate = new ProductCostsBetweenPredicate(TEN_DOLLARS, TWENTY_DOLLARS);
        ProductCostsBetweenPredicate firstPredicateCopy = new ProductCostsBetweenPredicate(TEN_DOLLARS, TWENTY_DOLLARS);
        ProductCostsBetweenPredicate secondPredicate = new ProductCostsBetweenPredicate(TEN_DOLLARS, THIRTY_DOLLARS);

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
        ProductCostsBetweenPredicate predicate = new ProductCostsBetweenPredicate(TEN_DOLLARS, TWENTY_DOLLARS);
        assertTrue(predicate.test(new Product(new ProductName( "psp"), new Money(new BigDecimal(15)), new Category("toys"))));

        // Price equal to left boundary
        assertTrue(predicate.test(new Product(new ProductName( "psp"), new Money(new BigDecimal(10)), new Category("toys"))));

        // Price equal to left boundary
        assertTrue(predicate.test(new Product(new ProductName( "psp"), new Money(new BigDecimal(20)), new Category("toys"))));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        //Price above upper limit
        ProductCostsBetweenPredicate predicate = new ProductCostsBetweenPredicate(TEN_DOLLARS, TWENTY_DOLLARS);
        assertFalse(predicate.test(new Product(new ProductName( "psp"), new Money(new BigDecimal(35)), new Category("toys"))));

        // Price below lower bound
        assertFalse(predicate.test(new Product(new ProductName( "psp"), new Money(new BigDecimal(2)), new Category("toys"))));

        //Price range reversed
        predicate = new ProductCostsBetweenPredicate(TWENTY_DOLLARS, TEN_DOLLARS);
        assertFalse(predicate.test(new Product(new ProductName( "psp"), new Money(new BigDecimal(15)), new Category("toys"))));
    }
}

