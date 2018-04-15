package seedu.address.model.product;

import org.junit.Test;
import seedu.address.model.money.Money;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
//@@author Sivalavida
public class ProductNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ProductNameContainsKeywordsPredicate firstPredicate = new ProductNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ProductNameContainsKeywordsPredicate secondPredicate = new ProductNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ProductNameContainsKeywordsPredicate firstPredicateCopy = new ProductNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_productNameContainsKeywords_returnsTrue() {
        // One keyword
        ProductNameContainsKeywordsPredicate predicate = new ProductNameContainsKeywordsPredicate(Collections.singletonList("game"));
        assertTrue(predicate.test(new Product(new ProductName( "game boy"), new Money(new BigDecimal(15)), new Category("toys"))));

        // Multiple keywords
        predicate = new ProductNameContainsKeywordsPredicate(Arrays.asList("game", "boy"));
        assertTrue(predicate.test(new Product(new ProductName( "game boy"), new Money(new BigDecimal(15)), new Category("toys"))));

        // Only one matching keyword
        predicate = new ProductNameContainsKeywordsPredicate(Arrays.asList("game", "towel"));
        assertTrue(predicate.test(new Product(new ProductName( "game"), new Money(new BigDecimal(15)), new Category("toys"))));
}

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ProductNameContainsKeywordsPredicate predicate = new ProductNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Product(new ProductName( "game"), new Money(new BigDecimal(15)), new Category("toys"))));

        // Non-matching keyword
        predicate = new ProductNameContainsKeywordsPredicate(Arrays.asList("boy"));
        assertFalse(predicate.test(new Product(new ProductName( "game"), new Money(new BigDecimal(15)), new Category("toys"))));

    }
}