package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.OrderCard;

//@@author YingxuH
/**
 * Represents a selection change in the Order List Panel
 */
public class OrderPanelSelectionChangedEvent extends BaseEvent {


    private final OrderCard newSelection;

    public OrderPanelSelectionChangedEvent(OrderCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public OrderCard getNewSelection() {
        return newSelection;
    }
}

