package seedu.address.model.product;


import java.util.Locale;

/**
 * presents the product offered in the retail store.
 * Attributes: name, price, category
 */
public class Product {
    private static int productCounter = 0;

    private final String name;
    private final int price;
    private final int id;
    private Category category;

    /**
     *
     * @param name
     * @param price
     * @param category category of the product.
     */
    public Product(String name, int price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.id = ++productCounter;
    }

    public String getName() { return name; }

    public int getPrice() { return price; }

    public Category getCategory() { return category; }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(" Price: ")
                .append(getPrice())
                .append(" Category: ")
                .append(getCategory());

        return builder.toString();
    }


}
