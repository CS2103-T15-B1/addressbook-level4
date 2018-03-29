package seedu.address.model.order;

import seedu.address.model.money.Money;

/**
 * SubOrder class for each single product purchased in an Order. Should be composited with Order
 * (i.e. can't exist without Order object which has pointer to this SubOrder object)
 *
 * Guarantees: SubOrder details are present and immutable
 */
public class SubOrder {
    private final int productID;
    private final int numProduct;
    private final Money productPrice;

    /** Every field must be present and non-null. */
    public SubOrder(int id, int num, Money price) {
        productID = id;
        numProduct = num;
        productPrice = price;
    }

    public int getProductID() {
        return productID;
    }

    public int getNumProduct() {
        return numProduct;
    }

    public Money getProductPrice() {
        return productPrice;
    }

    public Money getTotalPrice() {
        return productPrice.times(numProduct);
    }
}
