package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysProduct;

import org.junit.Test;

import guitests.guihandles.ProductCardHandle;
import seedu.address.model.product.Product;
import seedu.address.testutil.ProductBuilder;

public class ProductCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Product productWithNoCategory = new ProductBuilder().withCategory("").build();
        ProductCard productCard = new ProductCard(productWithNoCategory, 1);
        uiPartRule.setUiPart(productCard);
        assertCardDisplay(productCard, productWithNoCategory, 1);

        // with tags
        Product productWithCategory = new ProductBuilder().build();
        productCard = new ProductCard(productWithCategory, 2);
        uiPartRule.setUiPart(productCard);
        assertCardDisplay(productCard, productWithCategory, 2);
    }

    @Test
    public void equals() {
        Product product = new ProductBuilder().build();
        ProductCard productCard = new ProductCard(product, 0);

        // same product, same index -> returns true
        ProductCard copy = new ProductCard(product, 0);
        assertTrue(productCard.equals(copy));

        // different product, same index -> returns false
        Product differentProduct = new ProductBuilder().withName("differentName").build();
        assertFalse(productCard.equals(new ProductCard(differentProduct, 0)));

        // same product, different index -> returns false
        assertFalse(productCard.equals(new ProductCard(product, 1)));

        // same object -> returns true
        assertTrue(productCard.equals(productCard));

        // null -> returns false
        assertFalse(productCard.equals(null));

        // different types -> returns false
        assertFalse(productCard.equals(0));
    }

    /**
     * Asserts that {@code productCard} displays the details of {@code expectedProduct} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ProductCard productCard, Product expectedProduct, int expectedId) {
        guiRobot.pauseForHuman();

        ProductCardHandle productCardHandle = new ProductCardHandle(productCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", productCardHandle.getId());

        // verify product details are displayed correctly
        assertCardDisplaysProduct(expectedProduct, productCardHandle);
    }
}
