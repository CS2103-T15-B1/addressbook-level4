package seedu.address.model.order;

import org.junit.Test;

import static org.junit.Assert.*;
import static seedu.address.testutil.TypicalProducts.*;
import static seedu.address.testutil.TypicalSubOrders.*;

//@@author qinghao1
public class SubOrderTest {
    @Test
    public void getProductIdTest() {
        assertEquals(SO_A.getProductID(), EGG.getId());
        assertEquals(SO_B.getProductID(), MILK.getId());
    }

    @Test
    public void getNumProductTest() {
        assertEquals(SO_A.getNumProduct(), 2);
        assertEquals(SO_B.getNumProduct(), 3);
    }

    @Test
    public void getProductPriceTest() {
        assertEquals(SO_A.getProductPrice(), EGG.getPrice());
        assertEquals(SO_B.getProductPrice(), MILK.getPrice());
    }

    @Test
    public void getTotalPriceTest() {
        assertEquals(SO_A.getTotalPrice(), EGG.getPrice().times(SO_A.getNumProduct()));
        assertEquals(SO_B.getTotalPrice(), MILK.getPrice().times(SO_B.getNumProduct()));
    }
}
