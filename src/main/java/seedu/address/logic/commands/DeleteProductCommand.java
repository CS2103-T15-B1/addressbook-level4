package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.product.Product;
import seedu.address.model.product.exceptions.ProductNotFoundException;

/**
 * Deletes a product identified using it's last displayed index from the address book.
 */
public class DeleteProductCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteproduct";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the product identified by the index number used in the last product listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PRODUCT_SUCCESS = "Deleted Product: %1$s";

    private final Index targetIndex;

    private Product productToDelete;

    public DeleteProductCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(productToDelete);
        try {
            model.deleteProduct(productToDelete);
        } catch (ProductNotFoundException pnfe) {
            throw new AssertionError("The target product cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PRODUCT_SUCCESS, productToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Product> lastShownList = model.getFilteredProductList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        productToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteProductCommand) other).targetIndex) // state check
                && Objects.equals(this.productToDelete, ((DeleteProductCommand) other).productToDelete));
    }
}

