package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;
import static seedu.address.ui.testutil.GuiTestAssert.assertOrderCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.OrderCardHandle;
import guitests.guihandles.OrderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.order.Order;

//@@author He Yingxu
public class OrderListPanelTest extends GuiUnitTest {
    private static final ObservableList<Order> TYPICAL_ORDERS =
            FXCollections.observableList(getTypicalOrders());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_ORDER);

    private OrderListPanelHandle orderListPanelHandle;

    @Before
    public void setUp() {
        OrderListPanel orderListPanel = new OrderListPanel(TYPICAL_ORDERS);
        uiPartRule.setUiPart(orderListPanel);

        orderListPanelHandle = new OrderListPanelHandle(getChildNode(orderListPanel.getRoot(),
                OrderListPanelHandle.ORDER_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_ORDERS.size(); i++) {
            orderListPanelHandle.navigateToCard(TYPICAL_ORDERS.get(i));
            Order expectedOrder = TYPICAL_ORDERS.get(i);
            OrderCardHandle actualCard = orderListPanelHandle.getOrderCardHandle(i);

            assertCardDisplaysOrder(expectedOrder, actualCard);

            /*The index in the order list should be their real id instead of their orders.*/
            int expectedIndex = expectedOrder.getId();
            assertEquals(Integer.toString(expectedIndex) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        OrderCardHandle expectedCard = orderListPanelHandle.getOrderCardHandle(INDEX_THIRD_ORDER.getZeroBased());
        OrderCardHandle selectedCard = orderListPanelHandle.getHandleToSelectedCard();
        assertOrderCardEquals(expectedCard, selectedCard);
    }
}
