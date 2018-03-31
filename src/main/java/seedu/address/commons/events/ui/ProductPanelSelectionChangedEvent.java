package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ProductCard;

/**
 * Represents a selection change in the Product List Panel
 */
public class ProductPanelSelectionChangedEvent extends BaseEvent {


    private final ProductCard newSelection;

    public ProductPanelSelectionChangedEvent(ProductCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ProductCard getNewSelection() {
        return newSelection;
    }
}

