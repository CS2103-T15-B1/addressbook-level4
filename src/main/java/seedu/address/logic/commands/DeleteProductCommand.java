package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.product.Product;
import seedu.address.model.product.exceptions.ProductNotFoundException;

//@@qinghao1
/**
 * Deletes a product identified using its ID.
 */
public class DeleteProductCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteproduct";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the product identified by its ID.\n"
            + "Parameters: ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PRODUCT_SUCCESS = "Deleted Product: %1$s";
    public static final String MESSAGE_INVALID_PRODUCT = "The product is invalid. Check that the product ID is correct.";

    private int targetID;

    private Product productToDelete;

    public DeleteProductCommand(int targetID) {
        this.targetID = targetID;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(productToDelete);
        try {
            model.deleteProduct(productToDelete);
        } catch (ProductNotFoundException e) {
            throw new AssertionError("The product cannot be missing.");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PRODUCT_SUCCESS, productToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Product> lastShownList = model.getFilteredProductList();
        productToDelete = null;
        //There should only be one product that matches the ID
        for (Product product : lastShownList) {
            if (product.getId() == targetID) {
                productToDelete = product;
            }
        }
        if (productToDelete == null) {
            throw new CommandException(MESSAGE_INVALID_PRODUCT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetID == (((DeleteProductCommand) other).targetID) // state check
                && Objects.equals(this.productToDelete, ((DeleteProductCommand) other).productToDelete));
    }
}

