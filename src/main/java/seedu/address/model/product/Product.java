package seedu.address.model.product;


import seedu.address.model.money.Money;

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

    /**
     *
     * @param name
     * @param price
     * @param category category of the product.
     */
    public Product(ProductName name, Money price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.id = ++productCounter;
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
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getId() == (this.getId())
                && otherPerson.getPrice().equals(this.getPrice())
                && otherPerson.getCategory().equals(this.getCategory())
                ;
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
