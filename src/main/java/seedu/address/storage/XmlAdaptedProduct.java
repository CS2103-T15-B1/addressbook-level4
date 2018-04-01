package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.money.Money;
import seedu.address.model.product.Category;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;

import javax.xml.bind.annotation.XmlElement;
import java.util.*;

public class XmlAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String category;

    /**
     * Constructs an XmlAdaptedProduct.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProduct() {}

    /**
     * Constructs an {@code XmlAdaptedProduct} with the given product details.
     */
    public XmlAdaptedProduct(String id, String name, String price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * Converts a given Product into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedProduct
     */
    public XmlAdaptedProduct(Product source) {
        id = String.valueOf(source.getId());
        name = source.getName().toString();
        price = source.getPrice().toString();
        category = source.getCategory().toString();
    }

    /**
     * Converts this jaxb-friendly adapted product object into the model's Product object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product
     */
    public Product toModelType() throws IllegalValueException {
        if (this.id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "id"));
        }
        final int id = Integer.parseInt(this.id);

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ProductName.class.getSimpleName()));
        }
        if (!ProductName.isValidProductName(this.name)) {
            throw new IllegalValueException(ProductName.MESSAGE_PRODUCT_NAME_CONSTRAINTS);
        }
        final ProductName name = new ProductName(this.name);

        if (this.price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Price"));
        }
        final Money price = Money.parsePrice(this.price); //Throws IllegalValueException if price string isn't valid

        if (this.category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(this.category)) {
            throw new IllegalValueException(Category.MESSAGE_CATEGORY_CONSTRAINTS);
        }
        final Category category = new Category(this.category);

        return new Product(id, name, price, category);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedProduct otherProduct = (XmlAdaptedProduct) other;
        return Objects.equals(id, otherProduct.id)
                && Objects.equals(name, otherProduct.name)
                && Objects.equals(price, otherProduct.price)
                && Objects.equals(category, otherProduct.category);
    }

}
