# lowjiajin
###### \java\seedu\address\model\product\ProductCostsBetweenPredicateTest.java
``` java
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

```
###### \java\seedu\address\model\product\ProductNameContainsKeywordsPredicateTest.java
``` java
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
```
