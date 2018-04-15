package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.money.Money;
import seedu.address.model.order.SubOrder;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Objects;

//@@author qinghao1
/**
 * JAXB-friendly version of the SubOrder.
 */
public class XmlAdaptedSubOrder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "SubOrder's %s field is missing!";

    @XmlElement(required = true)
    private String productId;
    @XmlElement(required = true)
    private String numProduct;
    @XmlElement(required = true)
    private String productPrice;

    /**
     * Constructs an XmlAdaptedSubOrder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSubOrder() {}

    /**
     * Constructs an {@code XmlAdaptedSubOrder} with the given sub order details.
     */
    public XmlAdaptedSubOrder(String productId, String numProduct, String productPrice) {
        this.productId = productId;
        this.numProduct = numProduct;
        this.productPrice = productPrice;
    }

    /**
     * Converts a given SubOrder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSubOrder
     */
    public XmlAdaptedSubOrder(SubOrder source) {
        productId = String.valueOf(source.getProductID());
        numProduct = String.valueOf(source.getNumProduct());
        productPrice = source.getProductPrice().toString();
    }

    /**
     * Converts this jaxb-friendly adapted sub order object into the model's SubOrder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public SubOrder toModelType() throws IllegalValueException {
        if (this.productId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "productId"));
        }
        final int productId = Integer.parseInt(this.productId);

        if (this.numProduct == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "numProduct"));
        }
        final int numProduct = Integer.parseInt(this.numProduct);

        if (this.productPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "productPrice"));
        }
        //Throws IllegalValueException if productPrice string isn't valid
        final Money productPrice = Money.parsePrice(this.productPrice);

        return new SubOrder(productId, numProduct, productPrice);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSubOrder)) {
            return false;
        }

        XmlAdaptedSubOrder otherSubOrder = (XmlAdaptedSubOrder) other;
        return Objects.equals(productId, otherSubOrder.productId)
                && Objects.equals(numProduct, otherSubOrder.numProduct)
                && Objects.equals(productPrice, otherSubOrder.productPrice);
    }
}
