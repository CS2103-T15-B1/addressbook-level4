package seedu.address.model.product;

/**
 * The unique categories of the product.
 * Guarantees:
 */
public class Category {
    public final String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
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
