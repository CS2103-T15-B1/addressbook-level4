package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.product.Product;
import seedu.address.ui.ProductCard;

// @@author He Yingxu

/**
 * Provides a handle for {@code ProductListPanel} containing the list of {@code ProductCard}.
 */
public class ProductListPanelHandle extends NodeHandle<ListView<ProductCard>> {
    public static final String PRODUCT_LIST_VIEW_ID = "#productListView";

    private Optional<ProductCard> lastRememberedSelectedProductCard;

    public ProductListPanelHandle(ListView<ProductCard> productListPanelNode) {
        super(productListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ProductCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public ProductCardHandle getHandleToSelectedCard() {
        List<ProductCard> productList = getRootNode().getSelectionModel().getSelectedItems();

        if (productList.size() != 1) {
            throw new AssertionError("Product list size expected 1.");
        }

        return new ProductCardHandle(productList.get(0).getRoot());
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
        List<ProductCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the product.
     */
    public void navigateToCard(Product product) {
        List<ProductCard> cards = getRootNode().getItems();
        Optional<ProductCard> matchingCard = cards.stream().filter(card -> card.product.equals(product)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Product does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the product card handle of a product associated with the {@code index} in the list.
     */
    public ProductCardHandle getProductCardHandle(int index) {
        return getProductCardHandle(getRootNode().getItems().get(index).product);
    }

    /**
     * Returns the {@code ProductCardHandle} of the specified {@code product} in the list.
     */
    public ProductCardHandle getProductCardHandle(Product product) {
        Optional<ProductCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.product.equals(product))
                .map(card -> new ProductCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Product does not exist."));
    }

    /**
     * Selects the {@code ProductCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ProductCard} in the list.
     */
    public void rememberSelectedProductCard() {
        List<ProductCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedProductCard = Optional.empty();
        } else {
            lastRememberedSelectedProductCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ProductCard} is different from the value remembered by the most recent
     * {@code rememberSelectedProductCard()} call.
     */
    public boolean isSelectedProductCardChanged() {
        List<ProductCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedProductCard.isPresent();
        } else {
            return !lastRememberedSelectedProductCard.isPresent()
                    || !lastRememberedSelectedProductCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
