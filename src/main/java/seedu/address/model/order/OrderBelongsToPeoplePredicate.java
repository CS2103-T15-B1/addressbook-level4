package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

/**
 * This predicate filters orders according to a set of customer emails
 */
public class OrderBelongsToPeoplePredicate implements Predicate<Order> {
    private final List<String> emails;

    public OrderBelongsToPeoplePredicate(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public boolean test(Order order) {
        //Returns true if the order's personId (i.e. email) is in the list of emails
        return emails.stream()
                .anyMatch(email -> email.equals(order.getPersonId()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderBelongsToPeoplePredicate // instanceof handles nulls
                && this.emails.equals(((OrderBelongsToPeoplePredicate) other).emails)); // state check
    }
}
