package seedu.address.model.product.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

public class DuplicateProductException extends DuplicateDataException {
    public DuplicateProductException() {
        super("Operation would result in duplicate orders");
    }
}
