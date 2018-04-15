package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.order.OrderBelongsToPeoplePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds and lists all persons in retail analytics whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private static final String message = Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);

        //Get emails of filtered people
        List<String> emails = new ArrayList<>();
        for(Person person : this.model.getFilteredPersonList()) {
            emails.add(person.getEmail().toString());
        }

        //Create predicate to filter order list
        OrderBelongsToPeoplePredicate orderBelongsToPeoplePredicate = new OrderBelongsToPeoplePredicate(emails);

        //Update order list
        model.updateFilteredOrderList(orderBelongsToPeoplePredicate);
        return new CommandResult(getMessageForListShownSummary(model.getFilteredPersonList().size(), message));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
