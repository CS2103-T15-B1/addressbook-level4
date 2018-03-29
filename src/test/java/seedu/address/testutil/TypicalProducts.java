package seedu.address.testutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.money.Money;
import seedu.address.model.product.Category;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.exceptions.DuplicateProductException;
import static seedu.address.logic.commands.CommandTestUtil.*;

public class TypicalProducts {
    //TODO: add typical products class
    public static Product EGG = new Product(new ProductName("Egg"),
            new Money(new BigDecimal(1.0)), new Category("Food"));

}
