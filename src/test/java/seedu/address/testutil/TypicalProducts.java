package seedu.address.testutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.money.Money;
import seedu.address.model.product.Category;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;

//@@author qinghao1
/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalProducts {

    //Prevent instantiation
    private TypicalProducts() {};

    //Product IDs are set manually for first few to ensure that IDs 1-6 are present, then automatically set later
    public static final Product EGG = new Product(1, new ProductName("Egg"),
            new Money(new BigDecimal(0.5)), new Category("Food"));
    public static final Product MILK = new Product(2, new ProductName("Milk"),
            new Money(new BigDecimal(2.0)), new Category("Food"));
    public static final Product SHIRT = new Product(3, new ProductName("Shirt"),
            new Money(new BigDecimal(15.0)), new Category("Fashion"));
    public static final Product PANTS = new Product(4, new ProductName("Pants"),
            new Money(new BigDecimal(1.0)), new Category("Fashion"));
    public static final Product LAPTOP = new Product(5, new ProductName("Laptop"),
            new Money(new BigDecimal(2000)), new Category("Tech"));
    public static final Product SWEET = new Product(6, new ProductName("Sweet"),
            new Money(new BigDecimal(0.05)), new Category("Food"));
    public static final Product PAPER = new Product(new ProductName("Paper"),
            new Money(new BigDecimal(5)), new Category("Stationery"));
    public static final Product PEN = new Product(new ProductName("Pen"),
            new Money(new BigDecimal(1.5)), new Category("Stationery"));
    public static final Product PENCIL = new Product(new ProductName("Pencil"),
            new Money(new BigDecimal(1.0)), new Category("Stationery"));
    public static final Product SODA = new Product(new ProductName("Soda"),
            new Money(new BigDecimal(2.0)), new Category("Food"));

    public static List<Product> getTypicalOrders() {
        return new ArrayList<Product>(Arrays.asList(EGG, MILK, SHIRT, PANTS, LAPTOP, SWEET, PAPER, PEN, PENCIL, SODA));
    }

}
