package seedu.address.model.product.exceptions;

//@@author YingxuH
/**
 * Signals that the operation is unable to find the specified person.
 */
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("Product not found!");
    }
};