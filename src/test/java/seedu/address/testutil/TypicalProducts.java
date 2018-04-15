package seedu.address.testutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
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

    public static Product EGG, MILK, SHIRT, PANTS, LAPTOP, SWEET, PAPER, PEN, PENCIL, SODA;

    //Wrap in static block because Money.parsePrice() throws IllegalValueException
    static {
        try {
            //Product IDs are set manually for first few to ensure that IDs 1-6 are present, then automatically set later
            EGG = new Product(1, new ProductName("Egg"),
                    Money.parsePrice("0.5"), new Category("Food"));
            MILK = new Product(2, new ProductName("Milk"),
                    Money.parsePrice("2"), new Category("Food"));
            SHIRT = new Product(3, new ProductName("Shirt"),
                    Money.parsePrice("15"), new Category("Fashion"));
            PANTS = new Product(4, new ProductName("Pants"),
                    Money.parsePrice("20"), new Category("Fashion"));
            LAPTOP = new Product(5, new ProductName("Laptop"),
                    Money.parsePrice("2000"), new Category("Tech"));
            SWEET = new Product(6, new ProductName("Sweet"),
                    Money.parsePrice("0.05"), new Category("Food"));
            PAPER = new Product(new ProductName("Paper"),
                    Money.parsePrice("5"), new Category("Stationery"));
            PEN = new Product(new ProductName("Pen"),
                    Money.parsePrice("1.5"), new Category("Stationery"));
            PENCIL = new Product(new ProductName("Pencil"),
                    Money.parsePrice("1"), new Category("Stationery"));
            SODA = new Product(new ProductName("Soda"),
                    Money.parsePrice("2"), new Category("Food"));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    public static List<Product> getTypicalProducts() {
        return new ArrayList<>(Arrays.asList(EGG, MILK, SHIRT, PANTS, LAPTOP, SWEET, PAPER, PEN, PENCIL, SODA));
    }

}
