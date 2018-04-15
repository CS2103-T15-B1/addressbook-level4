package seedu.address.model.order;

import org.junit.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.money.Money;

import static org.junit.Assert.*;
import static seedu.address.testutil.TypicalOrders.*;
import static seedu.address.testutil.TypicalPersons.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@@author qinghao1
public class OrderTest {
    @Test
    public void testDecrementCounter() {
        //Creates one order, decrements the counter (to simulate invalid order), then creates another order.
        //Their IDs should be the same.
        List<SubOrder> subOrders = new ArrayList<>();
        subOrders.add(new SubOrder(1, 1, new Money()));
        Order newOrder = new Order("email@email.com", subOrders);
        int id = newOrder.getId();
        Order.decrementOrderCounter();
        Order newOrder2 = new Order("email@email.com", subOrders);
        int id2 = newOrder2.getId();
        assertEquals(id, id2);
    }

    @Test
    public void getIdTest() {
        assertEquals(ORDER_ONE.getId(), 1);
    }

    @Test
    public void getPersonIdTest() {
        assertEquals(ORDER_ONE.getPersonId(), ALICE.getEmail().toString());
    }

    @Test
    public void getTimeTest() {
        assertEquals(ORDER_ONE.getTime(), LocalDateTime.parse("2017-12-03T10:15:30"));
    }

    @Test
    public void getOrderTotalTest() throws IllegalValueException {
        assertEquals(ORDER_ONE.getOrderTotal(), Money.parsePrice("$27"));
    }

    @Test
    /**
     * Check Order.toString() returns correct format
     */
    public void toStringTest() throws Exception {
        String ORDER_ONE_STRING = "Order ID 1 by alice@example.com at 3 Dec, 2017 10:15:30 AM: \n" +
                                "-------------\n" +
                                "1- Product 1 x2 @$ 0.5\n" +
                                "2- Product 2 x3 @$ 2\n" +
                                "3- Product 7 x4 @$ 5\n" +
                                "-------------\n" +
                                "Total: $ 27.0\n" +
                                "-------------\n";
        assertEquals(ORDER_ONE.toString(), ORDER_ONE_STRING);

        String ORDER_TWO_STRING = "Order ID 2 by alice@example.com at 13 Dec, 2017 12:05:40 PM: \n" +
                                "-------------\n" +
                                "1- Product 1 x2 @$ 0.5\n" +
                                "2- Product 7 x4 @$ 5\n" +
                                "3- Product 5 x5 @$ 2000\n" +
                                "-------------\n" +
                                "Total: $ 10021.0\n" +
                                "-------------\n";
        assertEquals(ORDER_TWO.toString(), ORDER_TWO_STRING);
    }
}
