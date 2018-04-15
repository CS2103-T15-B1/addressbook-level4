package seedu.address.model.order.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author lowjiajin

/**
 * Signals that the operation will result in duplicate Order objects.
 */
public class DuplicateOrderException extends DuplicateDataException {
    public DuplicateOrderException() {
        super("Operation would result in duplicate orders");
    }
}
