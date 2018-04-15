package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRODUCT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PRODUCT;
import static seedu.address.testutil.TypicalProducts.getTypicalProducts;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysProduct;
import static seedu.address.ui.testutil.GuiTestAssert.assertProductCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ProductCardHandle;
import guitests.guihandles.ProductListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.product.Product;

//@@author YingxuH
public class ProductListPanelTest extends GuiUnitTest {
    private static final ObservableList<Product> TYPICAL_PRODUCTS =
            FXCollections.observableList(getTypicalProducts());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PRODUCT);

    private ProductListPanelHandle productListPanelHandle;

    @Before
    public void setUp() {
        ProductListPanel productListPanel = new ProductListPanel(TYPICAL_PRODUCTS);
        uiPartRule.setUiPart(productListPanel);

        productListPanelHandle = new ProductListPanelHandle(getChildNode(productListPanel.getRoot(),
                ProductListPanelHandle.PRODUCT_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PRODUCTS.size(); i++) {
            productListPanelHandle.navigateToCard(TYPICAL_PRODUCTS.get(i));
            Product expectedProduct = TYPICAL_PRODUCTS.get(i);
            ProductCardHandle actualCard = productListPanelHandle.getProductCardHandle(i);

            assertCardDisplaysProduct(expectedProduct, actualCard);

            /*The index in the product list should be their real id instead of their orders.*/
            int expectedIndex = expectedProduct.getId();
            assertEquals(Integer.toString(expectedIndex)+". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ProductCardHandle expectedCard = productListPanelHandle.getProductCardHandle(INDEX_SECOND_PRODUCT.getZeroBased());
        ProductCardHandle selectedCard = productListPanelHandle.getHandleToSelectedCard();
        assertProductCardEquals(expectedCard, selectedCard);
    }
}
