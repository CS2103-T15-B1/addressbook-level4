package seedu.address.model.order.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

public class DuplicateOrderException extends DuplicateDataException {
    public DuplicateOrderException() {
        super("Operation would result in duplicate orders");
    }
}
