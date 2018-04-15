package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PRODUCT;
import static seedu.address.testutil.TypicalProducts.getTypicalProducts;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysProduct;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ProductCardHandle;
import guitests.guihandles.ProductListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.product.Product;

public class ProductListPanelTest extends GuiUnitTest {
    private static final ObservableList<Product> TYPICAL_ProductS =
            FXCollections.observableList(getTypicalProducts());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PRODUCT);

    private ProductListPanelHandle productListPanelHandle;

    @Before
    public void setUp() {
        ProductListPanel productListPanel = new ProductListPanel(TYPICAL_ProductS);
        uiPartRule.setUiPart(productListPanel);

        productListPanelHandle = new ProductListPanelHandle(getChildNode(productListPanel.getRoot(),
                ProductListPanelHandle.Product_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_ProductS.size(); i++) {
            productListPanelHandle.navigateToCard(TYPICAL_ProductS.get(i));
            Product expectedProduct = TYPICAL_ProductS.get(i);
            ProductCardHandle actualCard = productListPanelHandle.getProductCardHandle(i);

            assertCardDisplaysProduct(expectedProduct, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ProductCardHandle expectedCard = productListPanelHandle.getProductCardHandle(INDEX_SECOND_Product.getZeroBased());
        ProductCardHandle selectedCard = productListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
