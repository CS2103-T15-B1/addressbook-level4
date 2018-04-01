package seedu.address.model.order.exceptions;

public class InvalidOrderException extends IllegalArgumentException{
    /**
     * Signals that the order is invalid.
     */
    public InvalidOrderException() {
            super("Invalid order");
        }
}
