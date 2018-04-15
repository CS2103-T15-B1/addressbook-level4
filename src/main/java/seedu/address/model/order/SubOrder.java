package seedu.address.model.order;

import seedu.address.model.money.Money;
import seedu.address.model.product.Product;

import java.util.List;

//@@author qinghao1
/**
 * SubOrder class for each single product purchased in an Order. Should be composited with Order
 * (i.e. can't exist without Order object which has pointer to this SubOrder object)
 *
 * Guarantees: field details are validated and immutable
 */
public class SubOrder {
    public static final String MESSAGE_SUBORDER_CONSTRAINTS =
            "Sub-Orders needs to have three elements, first two of which are integers representing product ID and"+
                    " number of that product bought, and the price of the product as purchased.";

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

    /**
     * Checks that suborder is valid
     * - product ID is in list of products
     * - productPrice is non-negative
     * @param products list of products to check against (global list)
     * @return validity
     */
    public boolean isValid(List<Product> products) {
        boolean valid = true;
        boolean idFound = false;
        for (Product product : products) {
            if (product.getId() == productID) {
                idFound = true;
                break;
            }
        }
        valid = idFound && valid; //Trip valid to false if product isn't found

        boolean negativeProductPrice = productPrice.isMinus();
        valid = !negativeProductPrice && valid;

        return valid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PdtID ");
        sb.append(productID);
        sb.append("\tx");
        sb.append(numProduct);
        sb.append("\t@");
        sb.append(productPrice);
        return sb.toString();
    }
}
