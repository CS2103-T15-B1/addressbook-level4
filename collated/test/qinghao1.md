# qinghao1
###### /java/seedu/address/logic/commands/AddOrderCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddOrderCommand}.
 */
public class AddOrderCommandIntegrationTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newOrder_success() throws Exception {
        List<SubOrder> subOrders = new ArrayList<>(Arrays.asList(SO_B, SO_D, SO_G, SO_H));
        Order validOrder = new Order(CARL, subOrders);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addOrder(validOrder);

        assertCommandSuccess(prepareCommand(validOrder, model), model,
                AddOrderCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order dupOrder = model.getAddressBook().getOrderList().get(0);
        assertCommandFailure(prepareCommand(dupOrder, model), model, AddOrderCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrder_invalidEmail_throwsCommandException() {
        //Invalid email
        List<SubOrder> subOrders1 = new ArrayList<>(Arrays.asList(SO_B, SO_D, SO_G, SO_H));
        Order invalidOrder1 = new Order("wrongemail@email.com", subOrders1);
        assertCommandFailure(prepareCommand(invalidOrder1, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void execute_invalidOrder_invalidProductId_throwsCommandException() {
        //Invalid product ID
        try {
            List<SubOrder> subOrders2 = new ArrayList<>(Arrays.asList(SO_B, SO_D, SO_G, new SubOrder(999, 1, Money.parsePrice("$5"))));
            Order invalidOrder2 = new Order(CARL, subOrders2);
            assertCommandFailure(prepareCommand(invalidOrder2, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
        } catch (IllegalValueException e) {
            //Money.parsePrice() throws IllegalValueException
        }
    }

    @Test
    public void execute_invalidOrder_negativePrice_throwsCommandException() {
        //Negative price
        Money negativePrice = new Money(new BigDecimal(-1));
        SubOrder negativeSubOrder = new SubOrder(EGG.getId(), 5, negativePrice);
        List<SubOrder> negativeSubOrderList = new ArrayList<>(Arrays.asList(negativeSubOrder));
        Order invalidOrder3 = new Order(CARL, negativeSubOrderList);
        assertCommandFailure(prepareCommand(invalidOrder3, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void execute_invalidOrder_repeatedProducts_throwsCommandException() {
        //Repeated product IDs
        List<SubOrder> repeatedSubOrders = new ArrayList<>(Arrays.asList(SO_A, SO_A));
        Order invalidOrder4 = new Order(CARL, repeatedSubOrders);
        assertCommandFailure(prepareCommand(invalidOrder4, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
    }

    /**
     * Generates a new {@code AddOrderCommand} which upon execution, adds {@code order} into the {@code model}.
     */
    private AddOrderCommand prepareCommand(Order order, Model model) {
        AddOrderCommand command = new AddOrderCommand(order);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void addOrder(Order order) throws DuplicateOrderException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteOrder(Order order) throws OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addProduct(Product product) throws DuplicateProductException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            fail("This method should not be called.");
            return null;
        }
```
###### /java/seedu/address/model/order/OrderTest.java
``` java
public class OrderTest {
    @Test
    public void testDecrementCounter() {
        //Creates one order, decrements the counter (to simulate invalid order), then creates another order.
        //Their IDs should be the same.
        List<SubOrder> subOrders = new ArrayList<>();
        subOrders.add(new SubOrder(1, 1, new Money()));
        Order newOrder = new Order("email@email.com", subOrders);
        int id = newOrder.getId();
        Order.decrementOrderCounter();
        Order newOrder2 = new Order("email@email.com", subOrders);
        int id2 = newOrder2.getId();
        assertEquals(id, id2);
    }

    @Test
    public void getIdTest() {
        assertEquals(ORDER_ONE.getId(), 1);
    }

    @Test
    public void getPersonIdTest() {
        assertEquals(ORDER_ONE.getPersonId(), ALICE.getEmail().toString());
    }

    @Test
    public void getTimeTest() {
        assertEquals(ORDER_ONE.getTime(), LocalDateTime.parse("2017-12-03T10:15:30"));
    }

    @Test
    public void getOrderTotalTest() throws IllegalValueException {
        assertEquals(ORDER_ONE.getOrderTotal(), Money.parsePrice("$27"));
    }

    @Test
    /**
     * Check Order.toString() returns correct format
     */
    public void toStringTest() throws Exception {
        String ORDER_ONE_STRING = "Order ID 1 by alice@example.com at 3 Dec, 2017 10:15:30 AM: \n" +
                                "-------------\n" +
                                "1- Product 1 x2 @$ 0.5\n" +
                                "2- Product 2 x3 @$ 2\n" +
                                "3- Product 7 x4 @$ 5\n" +
                                "-------------\n" +
                                "Total: $ 27.0\n" +
                                "-------------\n";
        assertEquals(ORDER_ONE.toString(), ORDER_ONE_STRING);

        String ORDER_TWO_STRING = "Order ID 2 by alice@example.com at 13 Dec, 2017 12:05:40 PM: \n" +
                                "-------------\n" +
                                "1- Product 1 x2 @$ 0.5\n" +
                                "2- Product 7 x4 @$ 5\n" +
                                "3- Product 5 x5 @$ 2000\n" +
                                "-------------\n" +
                                "Total: $ 10021.0\n" +
                                "-------------\n";
        assertEquals(ORDER_TWO.toString(), ORDER_TWO_STRING);
    }
}
```
###### /java/seedu/address/model/order/OrderBelongsToPeoplePredicateTest.java
``` java
public class OrderBelongsToPeoplePredicateTest {
    @Test
    public void predicateReturnsTrue() {
        List<String> emails = new ArrayList<>();
        emails.add(ALICE.getEmail().toString());
        emails.add(BENSON.getEmail().toString());
        OrderBelongsToPeoplePredicate predicate = new OrderBelongsToPeoplePredicate(emails);
        assertTrue(predicate.test(ORDER_ONE));
        assertTrue(predicate.test(ORDER_FOUR));
    }

    @Test
    public void predicateReturnsFalse() {
        List<String> emails = new ArrayList<>();
        emails.add(CARL.getEmail().toString());
        emails.add(DANIEL.getEmail().toString());
        OrderBelongsToPeoplePredicate predicate = new OrderBelongsToPeoplePredicate(emails);
        assertFalse(predicate.test(ORDER_ONE));
        assertFalse(predicate.test(ORDER_FOUR));
    }
}
```
###### /java/seedu/address/model/order/SubOrderTest.java
``` java
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
```
###### /java/seedu/address/model/UniqueProductListTest.java
``` java
public class UniqueProductListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueProductList uniqueProductList = new UniqueProductList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueProductList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/model/UniqueOrderListTest.java
``` java
public class UniqueOrderListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueOrderList uniqueOrderList = new UniqueOrderList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueOrderList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/testutil/TypicalProducts.java
``` java
/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalProducts {

    //Prevent instantiation
    private TypicalProducts() {};

    public static Product EGG, MILK, SHIRT, PANTS, LAPTOP, SWEET, PAPER, PEN, PENCIL, SODA;

    //Wrap in static block because Money.parsePrice() throws IllegalValueException
    static {
        try {
            //Product IDs are set manually for first few to ensure that IDs 1-6 are present, then automatically set later
            EGG = new Product(1, new ProductName("Egg"),
                    Money.parsePrice("0.5"), new Category("Food"));
            MILK = new Product(2, new ProductName("Milk"),
                    Money.parsePrice("2"), new Category("Food"));
            SHIRT = new Product(3, new ProductName("Shirt"),
                    Money.parsePrice("15"), new Category("Fashion"));
            PANTS = new Product(4, new ProductName("Pants"),
                    Money.parsePrice("20"), new Category("Fashion"));
            LAPTOP = new Product(5, new ProductName("Laptop"),
                    Money.parsePrice("2000"), new Category("Tech"));
            SWEET = new Product(6, new ProductName("Sweet"),
                    Money.parsePrice("0.05"), new Category("Food"));
            PAPER = new Product(new ProductName("Paper"),
                    Money.parsePrice("5"), new Category("Stationery"));
            PEN = new Product(new ProductName("Pen"),
                    Money.parsePrice("1.5"), new Category("Stationery"));
            PENCIL = new Product(new ProductName("Pencil"),
                    Money.parsePrice("1"), new Category("Stationery"));
            SODA = new Product(new ProductName("Soda"),
                    Money.parsePrice("2"), new Category("Food"));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    public static List<Product> getTypicalProducts() {
        return new ArrayList<>(Arrays.asList(EGG, MILK, SHIRT, PANTS, LAPTOP, SWEET, PAPER, PEN, PENCIL, SODA));
    }

}
```
###### /java/seedu/address/testutil/TypicalOrders.java
``` java
/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    //Orders 1-7 are created manually, 8-10 are created dynamically (time is LocalDateTime.now())
    public static final Order ORDER_ONE = new Order(
            1,
            ALICE,
            LocalDateTime.parse("2017-12-03T10:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_B, SO_C))
    );

    public static final Order ORDER_TWO = new Order(
            2,
            ALICE,
            LocalDateTime.parse("2017-12-13T12:05:40"),
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_C, SO_D))
    );

    public static final Order ORDER_THREE = new Order(
            3,
            ALICE,
            LocalDateTime.parse("2017-12-14T05:14:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_B, SO_C, SO_E))
    );

    public static final Order ORDER_FOUR = new Order(
            4,
            BENSON,
            LocalDateTime.parse("2017-12-18T12:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_F, SO_G))
    );

    public static final Order ORDER_FIVE = new Order(
            5,
            BOB,
            LocalDateTime.parse("2017-12-20T10:18:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_E, SO_H))
    );

    public static final Order ORDER_LARGE = new Order(
            6,
            CARL,
            LocalDateTime.parse("2017-12-21T10:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_I, SO_LARGE))
    );

    public static final Order ORDER_SIX = ORDER_LARGE; //alias

    public static final Order ORDER_FREE = new Order(
            7,
            KALE,
            LocalDateTime.parse("2017-12-25T20:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_FREE))
    );

    public static final Order ORDER_SEVEN = ORDER_FREE; //alias

    public static final Order ORDER_EIGHT = new Order(
            FIONA,
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_B))
    );

    public static final Order ORDER_NINE = new Order(
            GEORGE,
            new ArrayList<SubOrder>(Arrays.asList(SO_C, SO_D, SO_E))
    );

    public static final Order ORDER_TEN = new Order(
            GEORGE,
            new ArrayList<SubOrder>(Arrays.asList(SO_C, SO_D))
    );

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(
                ORDER_ONE, ORDER_TWO, ORDER_THREE, ORDER_FOUR, ORDER_FIVE, ORDER_SIX, ORDER_SEVEN, ORDER_EIGHT, ORDER_NINE, ORDER_TEN
        ));
    }
}
```
###### /java/seedu/address/testutil/TypicalSubOrders.java
``` java
/**
 * A utility class containing a list of {@code SubOrder} objects to be used in tests.
 */
public class TypicalSubOrders {

    //Prevents instantiation
    private TypicalSubOrders() {};

    public static final SubOrder SO_A = new SubOrder(EGG.getId(), 2, EGG.getPrice());
    public static final SubOrder SO_B = new SubOrder(MILK.getId(), 3, MILK.getPrice());
    public static final SubOrder SO_C = new SubOrder(PAPER.getId(), 4, PAPER.getPrice());
    public static final SubOrder SO_D = new SubOrder(LAPTOP.getId(), 5, LAPTOP.getPrice());;
    public static final SubOrder SO_E = new SubOrder(PEN.getId(), 20, PEN.getPrice());
    public static final SubOrder SO_F = new SubOrder(SODA.getId(), 1, SODA.getPrice());
    public static final SubOrder SO_G = new SubOrder(SHIRT.getId(), 2, SHIRT.getPrice());
    public static final SubOrder SO_H = new SubOrder(EGG.getId(), 12, EGG.getPrice());
    public static final SubOrder SO_I = new SubOrder(PANTS.getId(), 1, PANTS.getPrice());
    //Edge cases
    public static final SubOrder SO_LARGE = new SubOrder(LAPTOP.getId(), 2000, LAPTOP.getPrice());
    public static final SubOrder SO_FREE = new SubOrder(SHIRT.getId(), 1, new Money());

    public static List<SubOrder> getTypicalSubOrders() {
        return new ArrayList<>(Arrays.asList(SO_A, SO_B, SO_C, SO_D, SO_E, SO_F, SO_G, SO_H, SO_I, SO_LARGE, SO_FREE));
    }
}
```
###### /java/seedu/address/testutil/TypicalAddressBook.java
``` java
/**
 * Class to get typical address book with typical persons, orders and products.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all the typical persons, orders and products.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Order order : TypicalOrders.getTypicalOrders()) {
            try {
                ab.addOrder(order);
            } catch (DuplicateOrderException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Product product : TypicalProducts.getTypicalProducts()) {
            try {
                ab.addProduct(product);
            } catch (DuplicateProductException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }
}
```
