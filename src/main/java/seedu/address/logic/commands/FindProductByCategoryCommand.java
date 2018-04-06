package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.product.ProductCategoryContainsKeywordsPredicate;

//@@author lowjiajin
public class FindProductByCategoryCommand extends Command {

    public static final String COMMAND_WORD = "findproductbycategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose categories contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food";

    private static final String message = Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;

    private final ProductCategoryContainsKeywordsPredicate predicate;

    public FindProductByCategoryCommand(ProductCategoryContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredProductList(predicate);
        return new CommandResult(getMessageForListShownSummary(model.getFilteredProductList().size(), message));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindProductByCategoryCommand // instanceof handles nulls
                && this.predicate.equals(((FindProductByCategoryCommand) other).predicate)); // state check
    }
}
