package seedu.address.model.product;

/**
 * The unique categories of the product.
 * Guarantees:
 */
public class Category {
    public final String categoryName;
    public static final String CATEGORY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CATEGORY_CONSTRAINTS =
            "Category should only contain alphanumeric characters and spaces, and it should not be blank";

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() { return categoryName.hashCode(); }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + categoryName + ']';
    }
}
