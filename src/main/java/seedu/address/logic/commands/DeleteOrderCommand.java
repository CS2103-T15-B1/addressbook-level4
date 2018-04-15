package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.OrderNotFoundException;

//@@author qinghao1
/**
 * Deletes a order identified using its id from the address book.
 */
public class DeleteOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by its id.\n"
            + "Parameters: ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";
    public static final String MESSAGE_INVALID_ORDER = "The order is invalid. Check that the order ID is correct.";

    private final int targetID;

    private Order orderToDelete;

    public DeleteOrderCommand(int targetID) {
        this.targetID = targetID;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(orderToDelete);
        try {
            model.deleteOrder(orderToDelete);
        } catch (OrderNotFoundException e) {
            throw new AssertionError("The target order cannot be missing.");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Order> lastShownList = model.getFilteredOrderList();
        orderToDelete = null;
        int numberOrdersMatching = 0;
        //There should only be one order that matches the ID, but we check anyway
        for (Order order : lastShownList) {
            if (order.getId() == targetID) {
                orderToDelete = order;
                ++numberOrdersMatching;
            }
        }
        if (orderToDelete == null) {
            throw new CommandException(MESSAGE_INVALID_ORDER);
        } else if (numberOrdersMatching > 1) {
            //There are more than 1 order in the list with same ID! This should never happen.
            throw new CommandException("More than 1 order with ID " + targetID + " found.");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetID == ((DeleteOrderCommand) other).targetID // state check
                && Objects.equals(this.orderToDelete, ((DeleteOrderCommand) other).orderToDelete));
    }
}

