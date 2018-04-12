package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

//@@author lowjiajin
/**
 * Lists all products in the address book to the user.
 */
public class ListProductCommand extends Command {

    public static final String COMMAND_WORD = "listproduct";

    public static final String MESSAGE_SUCCESS = "Listed all products";


    @Override
    public CommandResult execute() {
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
