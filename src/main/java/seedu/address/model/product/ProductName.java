package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author YingxuH
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

