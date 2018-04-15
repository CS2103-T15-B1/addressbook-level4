package seedu.address.logic.commands;

import seedu.address.model.order.OrderBelongsToPeoplePredicate;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    //@@author qinghao1
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        //Get emails of filtered people
        List<String> emails = new ArrayList<>();
        for(Person person : this.model.getFilteredPersonList()) {
            emails.add(person.getEmail().toString());
        }

        //Create predicate to filter order list
        OrderBelongsToPeoplePredicate orderBelongsToPeoplePredicate = new OrderBelongsToPeoplePredicate(emails);

        //Update order list
        model.updateFilteredOrderList(orderBelongsToPeoplePredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
    //@@author
}
