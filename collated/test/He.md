# He
###### \java\seedu\address\testutil\ProductBuilder.java
``` java

/**
 * A utility class to help with building Product objects.
 */
public class ProductBuilder {

    public static final String DEFAULT_PRODUCTNAME = "Laptop";
    public static final BigDecimal DEFAULT_PRICE = new BigDecimal(1000);
    public static final String DEFAULT_CATEGORY = "PC";

    private ProductName name;
    private Money price;
    private Category category;

    public ProductBuilder() {
        name = new ProductName(DEFAULT_PRODUCTNAME);
        price = new Money(DEFAULT_PRICE);
        category = new Category(DEFAULT_CATEGORY);
    }

    /**
     * Initializes the ProductBuilder with the data of {@code productToCopy}.
     */
    public ProductBuilder(Product productToCopy) {
        name = productToCopy.getName();
        price = productToCopy.getPrice();
        category = productToCopy.getCategory();

    }

    /**
     * Sets the {@code Name} of the {@code Product} that we are building.
     */
    public ProductBuilder withName(String name) {
        this.name = new ProductName(name);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Product} that we are building.
     */
    public ProductBuilder withPrice(BigDecimal amount) {
        this.price = new Money(amount);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Product} that we are building.
     */
    public ProductBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    public Product build() {
        return new Product(name, price, category);
    }

}
```
###### \java\seedu\address\testutil\TypicalIndexes.java
``` java
    public static final Index INDEX_FIRST_PRODUCT = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PRODUCT = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PRODUCT = Index.fromOneBased(3);

    public static final Index INDEX_FIRST_ORDER = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_ORDER = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_ORDER = Index.fromOneBased(3);
}
```
