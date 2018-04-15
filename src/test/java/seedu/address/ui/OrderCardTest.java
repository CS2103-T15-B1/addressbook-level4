package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalOrders.ORDER_ONE;
import static seedu.address.testutil.TypicalOrders.ORDER_THREE;
import static seedu.address.testutil.TypicalOrders.ORDER_TWO;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;

import org.junit.Test;

import guitests.guihandles.OrderCardHandle;
import seedu.address.model.order.Order;

public class OrderCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // first order
        Order orderToTestFirst = ORDER_ONE;
        OrderCard orderCard = new OrderCard(orderToTestFirst, 1);
        uiPartRule.setUiPart(orderCard);
        assertCardDisplay(orderCard, orderToTestFirst, 1);

        // second order
        Order orderToTestSecond = ORDER_TWO;
        orderCard = new OrderCard(orderToTestSecond, 2);
        uiPartRule.setUiPart(orderCard);
        assertCardDisplay(orderCard, orderToTestSecond, 2);
    }

    @Test
    public void equals() {
        Order order = ORDER_ONE;
        OrderCard orderCard = new OrderCard(order, 0);

        // same order, same index -> returns true
        OrderCard copy = new OrderCard(order, 0);
        assertTrue(orderCard.equals(copy));

        // different order, same index -> returns false
        Order differentOrder = ORDER_THREE;
        assertFalse(orderCard.equals(new OrderCard(differentOrder, 0)));

        // same order, different index -> returns false
        assertFalse(orderCard.equals(new OrderCard(order, 1)));

        // same object -> returns true
        assertTrue(orderCard.equals(orderCard));

        // null -> returns false
        assertFalse(orderCard.equals(null));

        // different types -> returns false
        assertFalse(orderCard.equals(0));
    }

    /**
     * Asserts that {@code orderCard} displays the details of {@code expectedOrder} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(OrderCard orderCard, Order expectedOrder, int expectedId) {
        guiRobot.pauseForHuman();

        OrderCardHandle orderCardHandle = new OrderCardHandle(orderCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", orderCardHandle.getId());

        // verify order details are displayed correctly
        assertCardDisplaysOrder(expectedOrder, orderCardHandle);
    }
}
