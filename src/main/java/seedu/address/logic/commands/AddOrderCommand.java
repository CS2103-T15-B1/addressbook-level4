package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class AddOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addo";

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
    public static final String MESSAGE_DUPLICATE_ORDER = "This person already exists in the retail analytics";

    private final Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specified {@code Order}
     */
    public AddOrderCommand(Order order) {
        requireNonNull(order);
        toAdd = order;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
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