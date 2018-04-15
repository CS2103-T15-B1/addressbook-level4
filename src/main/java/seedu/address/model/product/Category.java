package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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
