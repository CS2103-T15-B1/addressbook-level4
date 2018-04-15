package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.product.Product;
import seedu.address.model.product.exceptions.DuplicateProductException;

/**
 * Adds a product to the retail analytics.
 */
public class AddProductCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addproduct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a product to retail analytics. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Egg "
            + PREFIX_PRICE + "SGD 2.5 "
            + PREFIX_CATEGORY + "Food ";

    public static final String MESSAGE_SUCCESS = "New product: %1$s";
    public static final String MESSAGE_DUPLICATE_PRODUCT = "This product already exists in the retail analytics";

    private final Product toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Product}
     */
    public AddProductCommand(Product product) {
        requireNonNull(product);
        toAdd = product;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addProduct(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateProductException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddProductCommand) other).toAdd));
    }
}

