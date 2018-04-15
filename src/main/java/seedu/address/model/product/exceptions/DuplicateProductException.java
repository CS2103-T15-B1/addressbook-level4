package seedu.address.model.product.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author YingxuH
/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateProductException extends DuplicateDataException {
    public DuplicateProductException() {
        super("Operation would result in duplicate products");
    }
}
