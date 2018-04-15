package seedu.address.testutil;

import java.math.BigDecimal;

import seedu.address.model.money.Money;
import seedu.address.model.product.Category;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;

//@@author He Yingxu

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
