package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.order.Order;
import seedu.address.ui.OrderCard;

// @@author He Yingxu

/**
 * Provides a handle for {@code OrderListPanel} containing the list of {@code OrderCard}.
 */
public class OrderListPanelHandle extends NodeHandle<ListView<OrderCard>> {
    public static final String ORDER_LIST_VIEW_ID = "#orderListView";

    private Optional<OrderCard> lastRememberedSelectedOrderCard;

    public OrderListPanelHandle(ListView<OrderCard> orderListPanelNode) {
        super(orderListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code OrderCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public OrderCardHandle getHandleToSelectedCard() {
        List<OrderCard> orderList = getRootNode().getSelectionModel().getSelectedItems();

        if (orderList.size() != 1) {
            throw new AssertionError("Order list size expected 1.");
        }

        return new OrderCardHandle(orderList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<OrderCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the order.
     */
    public void navigateToCard(Order order) {
        List<OrderCard> cards = getRootNode().getItems();
        Optional<OrderCard> matchingCard = cards.stream().filter(card -> card.order.equals(order)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Order does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the order card handle of a order associated with the {@code index} in the list.
     */
    public OrderCardHandle getOrderCardHandle(int index) {
        return getOrderCardHandle(getRootNode().getItems().get(index).order);
    }

    /**
     * Returns the {@code OrderCardHandle} of the specified {@code order} in the list.
     */
    public OrderCardHandle getOrderCardHandle(Order order) {
        Optional<OrderCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.order.equals(order))
                .map(card -> new OrderCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Order does not exist."));
    }

    /**
     * Selects the {@code OrderCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code OrderCard} in the list.
     */
    public void rememberSelectedOrderCard() {
        List<OrderCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedOrderCard = Optional.empty();
        } else {
            lastRememberedSelectedOrderCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code OrderCard} is different from the value remembered by the most recent
     * {@code rememberSelectedOrderCard()} call.
     */
    public boolean isSelectedOrderCardChanged() {
        List<OrderCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedOrderCard.isPresent();
        } else {
            return !lastRememberedSelectedOrderCard.isPresent()
                    || !lastRememberedSelectedOrderCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
