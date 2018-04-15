package seedu.address.model.order;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalOrders.*;
import static seedu.address.testutil.TypicalPersons.*;

//@@author qinghao1
public class OrderBelongsToPeoplePredicateTest {
    @Test
    public void predicateReturnsTrue() {
        List<String> emails = new ArrayList<>();
        emails.add(ALICE.getEmail().toString());
        emails.add(BENSON.getEmail().toString());
        OrderBelongsToPeoplePredicate predicate = new OrderBelongsToPeoplePredicate(emails);
        assertTrue(predicate.test(ORDER_ONE));
        assertTrue(predicate.test(ORDER_FOUR));
    }

    @Test
    public void predicateReturnsFalse() {
        List<String> emails = new ArrayList<>();
        emails.add(CARL.getEmail().toString());
        emails.add(DANIEL.getEmail().toString());
        OrderBelongsToPeoplePredicate predicate = new OrderBelongsToPeoplePredicate(emails);
        assertFalse(predicate.test(ORDER_ONE));
        assertFalse(predicate.test(ORDER_FOUR));
    }
}
