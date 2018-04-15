# YingxuH
###### \java\seedu\address\commons\events\ui\OrderPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Order List Panel
 */
public class OrderPanelSelectionChangedEvent extends BaseEvent {


    private final OrderCard newSelection;

    public OrderPanelSelectionChangedEvent(OrderCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public OrderCard getNewSelection() {
        return newSelection;
    }
}

```
###### \java\seedu\address\commons\events\ui\ProductPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Product List Panel
 */
public class ProductPanelSelectionChangedEvent extends BaseEvent {


    private final ProductCard newSelection;

    public ProductPanelSelectionChangedEvent(ProductCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ProductCard getNewSelection() {
        return newSelection;
    }
}

```
###### \java\seedu\address\logic\commands\AddProductCommand.java
``` java
/**
 * Adds a product to the retail analytics.
 */
public class AddProductCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addproduct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a product to retail analytics. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Egg "
            + PREFIX_PRICE + "SGD 2.5 "
            + PREFIX_CATEGORY + "Food ";

    public static final String MESSAGE_SUCCESS = "New product: %1$s";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the retail analytics";

    private final Product toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Product}
     */
    public AddProductCommand(Product product) {
        requireNonNull(product);
        toAdd = product;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addProduct(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateProductException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddProductCommand) other).toAdd));
    }
}

```
###### \java\seedu\address\logic\parser\AddProductCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddProductCommand object
 */
public class AddProductCommandParser implements Parser<AddProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddProductCommand
     * and returns an AddProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProductCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProductCommand.MESSAGE_USAGE));
        }

        try {
            ProductName name = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_NAME)).get();
            Money price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).get();
            Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY)).get();

            Product product = new Product(name, price, category);

            return new AddProductCommand(product);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(iae.getMessage(), iae);
        } catch (CurrencyUnknownException cue) {
            throw new ParseException(cue.getMessage(), cue);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddProductCommand.COMMAND_WORD:
            return new AddProductCommandParser().parse(arguments);

        case AddOrderCommand.COMMAND_WORD:
            return new AddOrderCommandParser().parse(arguments);
```
###### \java\seedu\address\model\AddressBook.java
``` java

    public void setProducts(List<Product> products) throws DuplicateProductException{
        this.products.setProducts(products);
    }

    public void setOrders(List<Order> orders) throws DuplicateOrderException {
        this.orders.setOrders(orders);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java

    /**
     * Adds a product to the address book.
     */
    public void addProduct(Product p) throws DuplicateProductException {
        //Product product = syncWithMasterTagList(p);
        //Maybe need to synchronize with CategoryList in the future.
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        products.add(p);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ProductNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeProduct(Product key) throws ProductNotFoundException {
        if (products.remove(key)) {
            return true;
        } else {
            throw new ProductNotFoundException();
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds new order to address book.
     * @throws DuplicateOrderException if this order already exists.
     */
    public void addOrder(Order o) throws DuplicateOrderException {
        orders.add(o);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     *
     * @throws DuplicateOrderException if updating the order's details causes the order to be equivalent to
     *      another existing order.
     * @throws OrderNotFoundException if {@code target} could not be found.
     */
    public void updateOrder(Order target, Order editedOrder)
            throws DuplicateOrderException, OrderNotFoundException {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws OrderNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeOrder(Order key) throws OrderNotFoundException {
        if (orders.remove(key)) {
            return true;
        } else {
            throw new OrderNotFoundException();
        }
    }


```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Product> getProductList() {
        return products.asObservableList();
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asObservableList();
    }

```
###### \java\seedu\address\model\Model.java
``` java
    /** {@code Predicate} that always evaluate to true */
    Predicate<Product> PREDICATE_SHOW_ALL_PRODUCTS = unused -> true;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given product. */
    void deleteProduct(Product target) throws ProductNotFoundException;

    /** Adds the given product */
    void addProduct(Product product) throws DuplicateProductException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredProductList();

    /**
     * Updates the filter of the filtered product list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProductList(Predicate<Product> predicate);

```
###### \java\seedu\address\model\ModelManager.java
``` java

    @Override
    public synchronized void deleteProduct(Product target) throws ProductNotFoundException {
        addressBook.removeProduct(target);
        indicateAddressBookChanged();
    }


    @Override
    public synchronized void addProduct(Product product) throws DuplicateProductException {
        addressBook.addProduct(product);
        updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Products} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Product> getFilteredProductList() {
        return FXCollections.unmodifiableObservableList(filteredProducts);
    }

    @Override
    public void updateFilteredProductList(Predicate<Product> predicate) {
        requireNonNull(predicate);
        filteredProducts.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\money\exceptions\CurrencyUnknownException.java
``` java
/**
 * Signals that the money objects do not have matching currencies.
 */

public class CurrencyUnknownException extends RuntimeException {
    public CurrencyUnknownException(String aMessage){
        super(aMessage);
    }
}
```
###### \java\seedu\address\model\money\exceptions\MismatchedCurrencyException.java
``` java
/**
 * Signals that the money objects do not have matching currencies.
 */

public class MismatchedCurrencyException extends RuntimeException {
    public MismatchedCurrencyException(String aMessage){
        super(aMessage);
    }
}
```
###### \java\seedu\address\model\money\exceptions\ObjectNotMoneyException.java
``` java
/**
 * Signals that the money objects do not have matching currencies.
 */

public class ObjectNotMoneyException extends RuntimeException {
    public ObjectNotMoneyException(String aMessage){
        super(aMessage);
    }
}
```
###### \java\seedu\address\model\money\Money.java
``` java
/**
 * Represent an amount of money in any currency.
 *
 * This class assumes decimal currency, without funky divisions
 * like 1/5 and so on. Money objects are immutable.
 * Most operations involving more than one Money object will throw a
 * MismatchedCurrencyException if the currencies don't match.
 *
 */
public class Money implements Comparable<Money>, Serializable {

    public static final String MONEY_VALIDATION_REGEX_WITHOUT_CURRENCY = "\\d+(\\.\\d+)?";
    public static final String MONEY_VALIDATION_REGEX_WITH_UNKNOWN_PREFIX = "(\\p{Alpha}+|\\p{Sc})\\s*\\d+(\\.\\d+)?";
    public static final String MONEY_PREFIX = "(\\p{Alpha}+|\\p{Sc})\\s*";
    public static final String MONEY_DIGITS = "\\s*\\d+(\\.\\d+)?";

    public static final String MESSAGE_MONEY_CONSTRAINTS =
            String.format("price should only contains currency sy/mbol(optional) and digits," +
                    " and it cannot be negative");
    public static final String MESSAGE_MONEY_SYMBOL_CONSTRAINTS =
            String.format("currency code should be limited ISO 4277 code");

    /**
     * The money amount.
     * Never null.
     */
    private BigDecimal fAmount;

    /**
     * The currency of the money, such as US Dollars or Euros.
     * Never null.
     */
    private final Currency fCurrency;

    /**
     * The rounding style to be used.
     */
    private final RoundingMode fRounding;

    public static BigDecimal DEFAULT_AMOUNT = new BigDecimal(0.00);

    /**
     * The default currency to be used if no currency is passed to the constructor.
     * To be initialized by the static init().
     */
    public static Currency DEFAULT_CURRENCY = Currency.getInstance("SGD");

    /**
     * The default rounding style to be used if no currency is passed to the constructor.
     */
    public static RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    /**
     * String representation for Money class.
     */
    public final String repMoney;

    private int fHashCode;
    private static final int HASH_SEED = 23;
    private static final int HASH_FACTOR = 37;

    /**
     * Full constructor.
     *
     * @param aAmount is required, can be positive or negative. The number of
     * decimals in the amount cannot exceed the maximum number of
     * decimals for the given Currency.
     * @param aCurrency
     * @param aRoundingStyle is required, must match a rounding style used by
     * BigDecimal.
     */
    public Money(BigDecimal aAmount, Currency aCurrency, RoundingMode aRoundingStyle){
        checkNotNull(aAmount, aCurrency, aRoundingStyle);
        fAmount = aAmount;
        fCurrency = aCurrency;
        fRounding = aRoundingStyle;
        repMoney = fCurrency.getSymbol() + " " + fAmount.toPlainString();
    }

    /**
     * Constructor taking only the money amount.
     * @param aAmount is required, can be positive or negative.
     */
    public Money(BigDecimal aAmount){
        this(aAmount, DEFAULT_CURRENCY, DEFAULT_ROUNDING);
    }

    /**
     * Constructor taking the money amount and currency.
     *
     * The rounding style takes a default value.
     * @param aAmount is required, can be positive or negative.
     * @param aCurrency is required.
     */
    public Money(BigDecimal aAmount, Currency aCurrency){
        this(aAmount, aCurrency, DEFAULT_ROUNDING);
    }

    /**
     * Constructor taking the money amount and the rounding mode.
     * @param aAmount is required, can be positive or negative.
     */
    public Money(BigDecimal aAmount, RoundingMode aRoundingStyle){
        this(aAmount, DEFAULT_CURRENCY, aRoundingStyle);
    }

    /**
     * empty constructor
     */
    public Money() {
        this(DEFAULT_AMOUNT, DEFAULT_CURRENCY, DEFAULT_ROUNDING);
    }
    /** Return the amount passed to the constructor. */
    public BigDecimal getAmount() { return fAmount; }

    /** Return the currency passed to the constructor, or the default currency. */
    public Currency getCurrency() { return fCurrency; }

    /** Return the rounding style passed to the constructor, or the default rounding style. */
    public RoundingMode getRoundingStyle() { return fRounding; }

    /**
     * Returns true if a given string is a valid Money.
     */
    public static boolean isValidMoney(String test) {
        return isValidMoneyWithoutCurrency(test) || isValidMoneyWithUnknownPrefix(test);
    }

    /**
     * Returns true if a given string is a valid Money with currency symbol code.
     */
    public static boolean isValidMoneyWithUnknownPrefix(String test) {
        return test.matches(MONEY_VALIDATION_REGEX_WITH_UNKNOWN_PREFIX);
    }

    /**
     * Return the currency that the symbol represents if the symbol is valid, otherwise returns the default
     * currency
     *
     * @param symbol
     * @return
     */
    public static Currency parseCurrency(String symbol) {
        for (Currency currency: Currency.getAvailableCurrencies()) {
            String code = currency.getSymbol();
            if (symbol.equals(code)) {
                return currency;
            }
        }
        throw new CurrencyUnknownException("unknown currency: " + symbol +"\n"+ MESSAGE_MONEY_SYMBOL_CONSTRAINTS);
    }


    /**
     * Returns true if a given string is a valid Money without currency symbol.
     */
    public static boolean isValidMoneyWithoutCurrency(String test) {
        return test.matches(MONEY_VALIDATION_REGEX_WITHOUT_CURRENCY);
    }

    /**
     * Parses a {@code String price} into a {@code price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code price} is invalid.
     */
    public static Money parsePrice(String price) throws IllegalValueException {
        return ParserUtil.parsePrice(price);
    }

    /**
     * Return true only if aThat Money has the same currency
     * as this Money. For the public use.
     * Assume the aThat is also a money object
     */
    public boolean isSameCurrencyAs(Money aThat){
        boolean result = false;
        if ( aThat != null ) {
            result = this.fCurrency.equals(aThat.fCurrency);
        }
        return result;
    }

    /** Return true only if the amount is positive. */
    public boolean isPlus(){
        return fAmount.compareTo(ZERO) > 0;
    }

    public boolean isMinus(){
        return fAmount.compareTo(ZERO) <  0;
    }

    public boolean isZero(){
        return fAmount.compareTo(ZERO) ==  0;
    }

    /**
     * Add aThat Money to this Money.
     * Currencies must match.
     */
    public Money plus(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return new Money(fAmount.add(that.fAmount), fCurrency, fRounding);
    }

    /**
     * Subtract aThat Money from this Money.
     * Currencies must match.
     */
    public Money minus(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return new Money(fAmount.subtract(that.fAmount), fCurrency, fRounding);
    }

    /**
     * Sum a collection of Money objects.
     * Currencies must match.
     *
     * @param aMoneys collection of Money objects, all of the same currency.
     * If the collection is empty, then a zero value is returned.
     */
    public static Money sum(Collection<Money> aMoneys){
        Money sum = new Money(ZERO);
        for(Money money : aMoneys){
            sum = sum.plus(money);
        }
        return sum;
    }

    /**
     * Equals (insensitive to scale).
     *
     * Return true only if the amounts are equal.
     * Currencies must match. This method is not synonymous with the equals method.
     */
    public boolean eq(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) == 0;
    }

    public boolean gt(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) > 0;
    }

    public boolean gteq(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) >= 0;
    }

    public boolean lt(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) < 0;
    }

    public boolean lteq(Object aThat){
        checkObjectIsMoney(aThat);
        Money that = (Money)aThat;
        checkCurrenciesMatch(that);
        return compareAmount(that) <= 0;
    }

    /**
     * Multiply this Money by an integral factor.
     *
     * The scale of the returned Money is equal to the scale of 'this'
     * Money.
     */
    public Money times(int aFactor){
        BigDecimal factor = new BigDecimal(aFactor);
        BigDecimal newAmount = fAmount.multiply(factor);
        return new Money(newAmount, fCurrency, fRounding);
    }

    /**
     * Multiply this Money by an non-integral factor (having a decimal point).
     */
    public Money times(double aFactor){
        BigDecimal newAmount = fAmount.multiply(asBigDecimal(aFactor));
        newAmount = newAmount.setScale(getNumDecimalsForCurrency(), fRounding);
        return  new Money(newAmount, fCurrency, fRounding);
    }

    /**
     * Returns
     * getAmount().getPlainString() + space + getCurrency().getSymbol().
     *
     * The return value uses the default locale/currency, and will not
     * always be suitable for display to an end user.
     */
    public String toString(){ return repMoney; }

    /**
     * This equal is sensitive to scale.
     *
     * For example, 10 is not equal to 10.00
     * The eq method, on the other hand, is not
     * sensitive to scale.
     */
    public boolean equals(Object aThat){
        if (this == aThat) return true;
        if (! (aThat instanceof Money) ) return false;
        Money that = (Money)aThat;
        //the object fields are never null :
        boolean result = (this.fAmount.equals(that.fAmount) );
        result = result && (this.fCurrency.equals(that.fCurrency) );
        result = result && (this.fRounding == that.fRounding);
        return result;
    }

    public int hashCode(){
        if ( fHashCode == 0 ) {
            fHashCode = HASH_SEED;
            fHashCode = HASH_FACTOR * fHashCode + fAmount.hashCode();
            fHashCode = HASH_FACTOR * fHashCode + fCurrency.hashCode();
            fHashCode = HASH_FACTOR * fHashCode + fRounding.hashCode();
        }
        return fHashCode;
    }

    /**
     * Compare by amount, then currency and rounding method.
     * @param aThat
     * @return
     */
    public int compareTo(Money aThat) {
        final int EQUAL = 0;

        if ( this == aThat ) return EQUAL;

        //the object fields are never null
        int comparison = this.fAmount.compareTo(aThat.fAmount);
        if ( comparison != EQUAL ) return comparison;

        comparison = this.fCurrency.getCurrencyCode().compareTo(
                aThat.fCurrency.getCurrencyCode()
        );
        if ( comparison != EQUAL ) return comparison;


        comparison = this.fRounding.compareTo(aThat.fRounding);
        if ( comparison != EQUAL ) return comparison;

        return EQUAL;
    }

    private void checkNotNull(BigDecimal aAmount, Currency aCurrency, RoundingMode aRoundingStyle){
        if( aAmount == null ) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if( aCurrency == null ) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        if( aRoundingStyle == null) {
            throw new IllegalArgumentException("rounding style cannot be null");
        }
        if ( aAmount.scale() > aCurrency.getDefaultFractionDigits() ) {
            throw new IllegalArgumentException(
                    "Number of decimals is " + aAmount.scale() + ", but currency only takes " +
                            aCurrency.getDefaultFractionDigits() + " decimals."
            );
        }
    }

    private int getNumDecimalsForCurrency(){
        return fCurrency.getDefaultFractionDigits();
    }

    /**
     * throw new exception if the other Monday is not the same currency.
     * @param aThat
     */
    private void checkCurrenciesMatch(Money aThat){
        if (! this.fCurrency.equals(aThat.getCurrency())) {
            throw new MismatchedCurrencyException(
                    aThat.getCurrency() + " doesn't match the expected currency : " + fCurrency
            );
        }
    }

    private void checkObjectIsMoney(Object aThat) {
        if (! (aThat instanceof Money) ) {
            throw new ObjectNotMoneyException(
                    aThat.getClass() + " doesn't match with Money class"
            );
        }
    }

    /** Ignores scale: 0 same as 0.00 */
    private int compareAmount(Money aThat){
        return this.fAmount.compareTo(aThat.fAmount);
    }

    private BigDecimal asBigDecimal(double aDouble){
        String asString = Double.toString(aDouble);
        return new BigDecimal(asString);
    }
} 
```
###### \java\seedu\address\model\product\Category.java
``` java
/**
 * The unique categories of the product.
 * Guarantees:
 */
public class Category {
    public static final String CATEGORY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CATEGORY_CONSTRAINTS =
            "Category should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * String representation for the category class.
     */
    public final String value;

    public Category(String categoryName) {
        requireNonNull(categoryName);
        checkArgument(isValidCategory(categoryName), MESSAGE_CATEGORY_CONSTRAINTS);
        this.value = categoryName;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.value.equals(((Category) other).value)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\address\model\product\exceptions\ProductNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified person.
 */
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("Product not found!");
    }
};
```
###### \java\seedu\address\model\product\Product.java
``` java
/**
 * presents the product offered in the retail store.
 * Attributes: name, price, category
 */
public class Product {
    private static int productCounter = 0;

    private final int id;
    private final ProductName name;
    private final Money price;
    private Category category;

    public Product(ProductName name, Money price, Category category) {
        requireAllNonNull(name, price, category);
        this.id = ++productCounter;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * Adds a product with specified id. Used for regenerating product list from storage.
     * Note that this sets the productCounter to the maximum id added into the list, to ensure distinctness of
     * product ids.
     * @param id
     * @param name
     * @param price
     * @param category
     */
    public Product(int id, ProductName name, Money price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        productCounter = Math.max(productCounter, id);
    }

    public int getId() { return id; }

    public ProductName getName() { return name; }

    public Money getPrice() { return price; }

    public Category getCategory() { return category; }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Product)) {
            return false;
        }

        Product otherPerson = (Product) other;
        return otherPerson.getName().equals(this.getName());
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" Price: ")
                .append(getPrice())
                .append(" Category: ")
                .append(getCategory());

        return builder.toString();
    }


}
```
###### \java\seedu\address\model\product\ProductName.java
``` java
/**
 * Represents a Person's ProductName in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidProductName(String)}
 */
public class ProductName {

    public static final String MESSAGE_PRODUCT_NAME_CONSTRAINTS =
            "Product Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String PRODUCT_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullProductName;

    /**
     * Constructs a {@code ProductName}.
     *
     * @param ProductName A valid ProductName.
     */
    public ProductName(String ProductName) {
        requireNonNull(ProductName);
        checkArgument(isValidProductName(ProductName), MESSAGE_PRODUCT_NAME_CONSTRAINTS);
        this.fullProductName = ProductName;
    }

    /**
     * Returns true if a given string is a valid person ProductName.
     */
    public static boolean isValidProductName(String test) {
        return test.matches(PRODUCT_NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullProductName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductName // instanceof handles nulls
                && this.fullProductName.equals(((ProductName) other).fullProductName)); // state check
    }

    @Override
    public int hashCode() {
        return fullProductName.hashCode();
    }

}

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private OrderListPanel orderListPanel;
    private ProductListPanel productListPanel;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private StackPane productListPanelPlaceholder;

    @FXML
    private StackPane orderListPanelPlaceholder;

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

```
###### \java\seedu\address\ui\MainWindow.java
``` java
        productListPanel = new ProductListPanel(logic.getFilteredProductList());
        productListPanelPlaceholder.getChildren().add(productListPanel.getRoot());

        orderListPanel = new OrderListPanel(logic.getFilteredOrderList());
        orderListPanelPlaceholder.getChildren().add(orderListPanel.getRoot());

```
###### \java\seedu\address\ui\MainWindow.java
``` java
        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
```
###### \java\seedu\address\ui\OrderCard.java
``` java
/**
 * An UI component that displays information of a {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label personId;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private FlowPane subOrders;
    @FXML
    private Label totalPrice;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(Integer.toString(displayedIndex));
        personId.setText(order.getPersonId());

        id.setText(Integer.toString(order.getId()));
        time.setText(order.getTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));

        order.getSubOrders().forEach(subOrder ->
                subOrders.getChildren().add(
                        createProductBox(subOrder)
                ));
        subOrders.setHgap(10);
        totalPrice.setText("Total: " + order.getOrderTotal());
    }

    private VBox createProductBox (SubOrder subOrder) {
        VBox box = new VBox();
        ObservableList list = box.getChildren();
        Label id = new Label(subOrder.toString());
        list.add(id);
        return box;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderCard)) {
            return false;
        }

        // state check
        OrderCard card = (OrderCard) other;
        return id.getText().equals(card.id.getText())
                && order.equals(card.order);
    }
}

```
###### \java\seedu\address\ui\OrderListPanel.java
``` java
/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<OrderCard> orderListView;

    public OrderListPanel(ObservableList<Order> orderList) {
        super(FXML);
        setConnections(orderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Order> orderList) {
        ObservableList<OrderCard> mappedList = EasyBind.map(
                orderList, (order) -> new OrderCard(order, order.getId()));
        orderListView.setItems(mappedList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        orderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in order list panel changed to : '" + newValue + "'");
                        raise(new OrderPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code OrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            orderListView.scrollTo(index);
            orderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<OrderCard> {

        @Override
        protected void updateItem(OrderCard order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(order.getRoot());
            }
        }
    }

}

```
###### \java\seedu\address\ui\ProductCard.java
``` java
/**
 * An UI component that displays information of a {@code Product}.
 */
public class ProductCard extends UiPart<Region> {

    private static final String FXML = "ProductListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Product product;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label category;

    public ProductCard(Product product, int displayedIndex) {
        super(FXML);
        this.product = product;
        id.setText(Integer.toString(displayedIndex));
        name.setText(product.getName().fullProductName);
        price.setText(product.getPrice().repMoney);
        category.setText("- " + product.getCategory().value);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProductCard)) {
            return false;
        }

        // state check
        ProductCard card = (ProductCard) other;
        return id.getText().equals(card.id.getText())
                && product.equals(card.product);
    }
}

```
###### \java\seedu\address\ui\ProductListPanel.java
``` java
/**
 * Panel containing the list of products.
 */
public class ProductListPanel extends UiPart<Region> {
    private static final String FXML = "ProductListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProductListPanel.class);

    @FXML
    private ListView<ProductCard> productListView;

    public ProductListPanel(ObservableList<Product> productList) {
        super(FXML);
        setConnections(productList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Product> productList) {
        ObservableList<ProductCard> mappedList = EasyBind.map(
                productList, (product) -> new ProductCard(product, product.getId()));
        productListView.setItems(mappedList);
        productListView.setCellFactory(listView -> new ProductListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        productListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in product list panel changed to : '" + newValue + "'");
                        raise(new ProductPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ProductCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            productListView.scrollTo(index);
            productListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ProductCard}.
     */
    class ProductListViewCell extends ListCell<ProductCard> {

        @Override
        protected void updateItem(ProductCard product, boolean empty) {
            super.updateItem(product, empty);

            if (empty || product == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(product.getRoot());
            }
        }
    }

}

```
