package seedu.address.model.product;


import seedu.address.model.money.Money;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

//@@author YingxuH
/**
 * presents the product offered in the retail store.
 * Attributes: name, price, category
 */
public class Product {
    private static int productCounter = 0;

    private final int id;
    private final ProductName name;
    private final Money price;
    private Category category;

    public Product(ProductName name, Money price, Category category) {
        requireAllNonNull(name, price, category);
        this.id = ++productCounter;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * Adds a product with specified id. Used for regenerating product list from storage.
     * Note that this sets the productCounter to the maximum id added into the list, to ensure distinctness of
     * product ids.
     * @param id
     * @param name
     * @param price
     * @param category
     */
    public Product(int id, ProductName name, Money price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        productCounter = Math.max(productCounter, id);
    }

    public int getId() { return id; }

    public ProductName getName() { return name; }

    public Money getPrice() { return price; }

    public Category getCategory() { return category; }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Product)) {
            return false;
        }

        Product otherPerson = (Product) other;
        return otherPerson.getName().equals(this.getName());
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" Price: ")
                .append(getPrice())
                .append(" Category: ")
                .append(getCategory());

        return builder.toString();
    }


}
