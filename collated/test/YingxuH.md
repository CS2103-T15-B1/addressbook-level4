# YingxuH
###### \java\guitests\guihandles\MainWindowHandle.java
``` java
        productListPanel = new ProductListPanelHandle(getChildNode(ProductListPanelHandle.PRODUCT_LIST_VIEW_ID));
        orderListPanel = new OrderListPanelHandle(getChildNode(OrderListPanelHandle.ORDER_LIST_VIEW_ID));
```
###### \java\guitests\guihandles\MainWindowHandle.java
``` java
    public ProductListPanelHandle getProductListPanel() {
        return productListPanel;
    }

    public OrderListPanelHandle getOrderListPanel() {
        return orderListPanel;
    }

```
###### \java\guitests\guihandles\OrderCardHandle.java
``` java

/**
 * Provides a handle to a Order card in the Order list panel.
 */
public class OrderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String PERSONID_FIELD_ID = "#personId";
    private static final String TIME_FIELD_ID = "#time";
    private static final String SUBORDERS_FIELD_ID = "#subOrders";

    private final Label idLabel;
    private final Label personIdLabel;
    private final Label timeLabel;
    private final List<VBox> subOrdersLabels;

    public OrderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.personIdLabel = getChildNode(PERSONID_FIELD_ID);
        this.timeLabel= getChildNode(TIME_FIELD_ID);

        Region subOrdersContainer = getChildNode(SUBORDERS_FIELD_ID);
        this.subOrdersLabels = subOrdersContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(VBox.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getPersonId() {
        return personIdLabel.getText();
    }

    public LocalDateTime getTime() {

        Instant instant = Instant.parse(timeLabel.getText());

        System.out.println("Instant : " + instant);

        //get date time only
        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        return result;
    }

    /**
     * returns all the children node inside each box
     * @return
     */
    public List<ObservableList<Node>> getSubOrders() {
        return subOrdersLabels
                .stream()
                .map(VBox::getChildren)
                .collect(Collectors.toList());
    }

}
```
###### \java\guitests\guihandles\OrderListPanelHandle.java
``` java

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
```
###### \java\guitests\guihandles\ProductCardHandle.java
``` java

/**
 * Provides a handle to a product card in the product list panel.
 */
public class ProductCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String CATEGORY_FIELD_ID = "#category";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label priceLabel;
    private final Label categoryLabel;

    public ProductCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.priceLabel= getChildNode(PRICE_FIELD_ID);
        this.categoryLabel = getChildNode(CATEGORY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getCategory() {
        return categoryLabel.getText();
    }

}
```
###### \java\guitests\guihandles\ProductListPanelHandle.java
``` java
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
```
###### \java\seedu\address\ui\OrderCardTest.java
``` java

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
```
###### \java\seedu\address\ui\OrderListPanelTest.java
``` java
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
```
###### \java\seedu\address\ui\ProductCardTest.java
``` java
public class ProductCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Product productWithNoCategory = new ProductBuilder().withCategory("random").build();
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
```
###### \java\seedu\address\ui\ProductListPanelTest.java
``` java
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
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualProductCard} displays the same values as {@code expectedProductCard}.
     */
    public static void assertProductCardEquals(ProductCardHandle expectedCard, ProductCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedProduct}.
     */
    public static void assertCardDisplaysProduct(Product expectedProduct, ProductCardHandle actualCard) {

        assertEquals(expectedProduct.getName().fullProductName, actualCard.getName());
        assertEquals(expectedProduct.getPrice().repMoney, actualCard.getPrice());
        assertEquals(expectedProduct.getCategory().value, actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualOrderCard} displays the same values as {@code expectedOrderCard}.
     */
    public static void assertOrderCardEquals(OrderCardHandle expectedCard, OrderCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPersonId(), actualCard.getPersonId());
        assertEquals(expectedCard.getTime(), actualCard.getTime());
        assertEquals(expectedCard.getSubOrders(), actualCard.getSubOrders());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedProduct}.
     */
    public static void assertCardDisplaysOrder(Order expectedOrder, OrderCardHandle actualOrder) {
        assertEquals(expectedOrder.getPersonId(), actualOrder.getPersonId());
        assertEquals(expectedOrder.getTime(), actualOrder.getTime());
    }


```
