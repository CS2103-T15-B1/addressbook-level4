package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PRICE;

public class FindProductByPriceCommand extends Command {

    public static final String COMMAND_WORD = "findproductbyprice";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose price lies within "
            + "the specified min/max (inclusive) interval and displays them as a list with index numbers.\n"
            + "Parameters: min/NUMBER max/NUMBER\n"
            + String.format("Example: %1$s %2$s10 %3$s15",
                    COMMAND_WORD, PREFIX_MIN_PRICE.getPrefix(), PREFIX_MAX_PRICE.getPrefix());

    private static final String message = Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;

    private final ProductCostsBetweenPredicate predicate;

    public FindProductByPriceCommand(ProductCostsBetweenPredicate predicate) {
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
                || (other instanceof FindProductByPriceCommand // instanceof handles nulls
                && this.predicate.equals(((FindProductByPriceCommand) other).predicate)); // state check
    }
}
