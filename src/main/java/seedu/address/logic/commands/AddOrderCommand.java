package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class AddOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates new order given a person's email, and non-empty list of (Product ID, Number bought, Price)\n"
            + "Parameters:"
            + PREFIX_EMAIL + "EMAIL (Must be an existing person)"
            + PREFIX_ORDER + "SUBORDER (List of product ID, Number, Price)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "john@example.com "
            + PREFIX_ORDER + "1 5 $3.00 "
            + PREFIX_ORDER + "2 4 $2.50 "
            + PREFIX_ORDER + "3 1 $100 ";

    public static final String MESSAGE_SUCCESS = "New order added.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the app.";
    public static final String MESSAGE_INVALID_ORDER =
            "The order is invalid. Check that the person and products exist, and that product IDs are distinct. ";

    private final Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specified {@code Order}
     */
    public AddOrderCommand(Order order) {
        requireNonNull(order);
        toAdd = order;
    }

    /**
     * Checks that the add order command is valid (order to be created is valid)
     */
    public boolean isValid() {
        ReadOnlyAddressBook ab = this.model.getAddressBook();
        return toAdd.isValid(ab.getPersonList(), ab.getProductList());
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        //Check that order is valid
        if(!isValid()) {
            //If order invalid, decrement order counter so orders have sequential ID
            Order.decrementOrderCounter();
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }
        try {
            model.addOrder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateOrderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }
}
