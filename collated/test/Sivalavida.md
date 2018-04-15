# Sivalavida
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getGender() {
        return genderLabel.getText();
    }

    public String getAge() {
        return ageLabel.getText();
    }

    public String getLatitude() {
        return latitudeLabel.getText();
    }

    public String getLongitude() {
        return longitudeLabel.getText();
    }
```
###### \java\seedu\address\model\person\AgeTest.java
``` java
public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "121";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid gender
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only
        assertFalse(Age.isValidAge("^")); // only non-alphanumeric characters
        assertFalse(Age.isValidAge("M*")); // contains non-alphanumeric characters
        assertFalse(Age.isValidAge("90.1234")); // decimal values
        assertFalse(Age.isValidAge("-90")); // negative values


        // valid gender
        assertTrue(Age.isValidAge("2")); //integer with one digit
        assertTrue(Age.isValidAge("20")); //integer with two digit
        assertTrue(Age.isValidAge("110")); //integer with three digit
    }
}
```
###### \java\seedu\address\model\person\GenderTest.java
``` java
public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "q";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("^")); // only non-alphanumeric characters
        assertFalse(Gender.isValidGender("M*")); // contains non-alphanumeric characters
        assertFalse(Gender.isValidGender("M ")); // male with space

        // valid gender
        assertTrue(Gender.isValidGender("M")); // male in uppercase
        assertTrue(Gender.isValidGender("m")); // male in lowercase
        assertTrue(Gender.isValidGender("F")); // female in uppercase
        assertTrue(Gender.isValidGender("f")); // female in lowercase
    }
}
```
###### \java\seedu\address\model\person\LatitudeTest.java
``` java
public class LatitudeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Latitude(null));
    }

    @Test
    public void constructor_invalidLatitude_throwsIllegalArgumentException() {
        String invalidLatitude = "91";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Latitude(invalidLatitude));
    }

    @Test
    public void isValidLatitude() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Latitude.isValidLatitude(null));

        // invalid gender
        assertFalse(Latitude.isValidLatitude("")); // empty string
        assertFalse(Latitude.isValidLatitude(" ")); // spaces only
        assertFalse(Latitude.isValidLatitude("^")); // only non-alphanumeric characters
        assertFalse(Latitude.isValidLatitude("M*")); // contains non-alphanumeric characters
        assertFalse(Latitude.isValidLatitude("90.1234")); // greater than 90
        assertFalse(Latitude.isValidLatitude("-90.1234")); // less than 90
        assertFalse(Latitude.isValidLatitude("--90.9")); // double negative sign
        assertFalse(Latitude.isValidLatitude("- 90.1234")); // space between sign and number


        // valid gender
        assertTrue(Latitude.isValidLatitude("20.1234")); // positive float
        assertTrue(Latitude.isValidLatitude("-20.1234")); // negative float
        assertTrue(Latitude.isValidLatitude("90")); // integers
        assertTrue(Latitude.isValidLatitude("-90")); // negative integer
    }

    @Test
    public void getValue() {
        Latitude latitude= new Latitude("1.234");
        assertEquals("01.234000", latitude.getValue());

        latitude= new Latitude("-1.234");
        assertEquals("-01.234000", latitude.getValue());

        latitude= new Latitude("0");
        assertEquals("00.000000", latitude.getValue());

        latitude= new Latitude("2");
        assertEquals("02.000000", latitude.getValue());
    }
}
```
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
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Latitude} of the {@code Person} that we are building.
     */
    public PersonBuilder withLatitude(String latitude) {
        this.latitude = new Latitude(latitude);
        return this;
    }

    /**
     * Sets the {@code Longitude} of the {@code Person} that we are building.
     */
    public PersonBuilder withLongitude(String longitude) {
        this.longitude = new Longitude(longitude);
        return this;
    }
```
