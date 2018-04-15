# qinghao1
###### \java\seedu\address\testutil\TypicalOrders.java
``` java
/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    //Orders 1-7 are created manually, 8-10 are created dynamically (time is LocalDateTime.now())
    public static final Order ORDER_ONE = new Order(
            1,
            ALICE.getEmail().toString(),
            LocalDateTime.parse("2017-12-03T10:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_B, SO_C))
    );

    public static final Order ORDER_TWO = new Order(
            2,
            ALICE.getEmail().toString(),
            LocalDateTime.parse("2017-12-13T12:05:40"),
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_C, SO_D))
    );

    public static final Order ORDER_THREE = new Order(
            3,
            ALICE.getEmail().toString(),
            LocalDateTime.parse("2017-12-14T05:14:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_B, SO_C, SO_E))
    );

    public static final Order ORDER_FOUR = new Order(
            4,
            BENSON.getEmail().toString(),
            LocalDateTime.parse("2017-12-18T12:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_F, SO_G))
    );

    public static final Order ORDER_FIVE = new Order(
            5,
            BOB.getEmail().toString(),
            LocalDateTime.parse("2017-12-20T10:18:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_E, SO_H))
    );

    public static final Order ORDER_LARGE = new Order(
            6,
            CARL.getEmail().toString(),
            LocalDateTime.parse("2017-12-21T10:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_I, SO_LARGE))
    );

    public static final Order ORDER_SIX = ORDER_LARGE; //alias

    public static final Order ORDER_FREE = new Order(
            7,
            KALE.getEmail().toString(),
            LocalDateTime.parse("2017-12-25T20:15:30"),
            new ArrayList<SubOrder>(Arrays.asList(SO_FREE))
    );

    public static final Order ORDER_SEVEN = ORDER_FREE; //alias

    public static final Order ORDER_EIGHT = new Order(
            FIONA.getEmail().toString(),
            new ArrayList<SubOrder>(Arrays.asList(SO_A, SO_B))
    );

    public static final Order ORDER_NINE = new Order(
            GEORGE.getEmail().toString(),
            new ArrayList<SubOrder>(Arrays.asList(SO_C, SO_D, SO_E))
    );

    public static final Order ORDER_TEN = new Order(
            GEORGE.getEmail().toString(),
            new ArrayList<SubOrder>(Arrays.asList(SO_C, SO_D))
    );

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(
                ORDER_ONE, ORDER_TWO, ORDER_THREE, ORDER_FOUR, ORDER_FIVE, ORDER_SIX, ORDER_SEVEN, ORDER_EIGHT, ORDER_NINE, ORDER_TEN
        ));
    }
}
```
###### \java\seedu\address\testutil\TypicalProducts.java
``` java
/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalProducts {

    //Prevent instantiation
    private TypicalProducts() {};

    //Product IDs are set manually for first few to ensure that IDs 1-6 are present, then automatically set later
    public static final Product EGG = new Product(1, new ProductName("Egg"),
            new Money(new BigDecimal(0.5)), new Category("Food"));
    public static final Product MILK = new Product(2, new ProductName("Milk"),
            new Money(new BigDecimal(2.0)), new Category("Food"));
    public static final Product SHIRT = new Product(3, new ProductName("Shirt"),
            new Money(new BigDecimal(15.0)), new Category("Fashion"));
    public static final Product PANTS = new Product(4, new ProductName("Pants"),
            new Money(new BigDecimal(1.0)), new Category("Fashion"));
    public static final Product LAPTOP = new Product(5, new ProductName("Laptop"),
            new Money(new BigDecimal(2000)), new Category("Tech"));
    public static final Product SWEET = new Product(6, new ProductName("Sweet"),
            new Money(new BigDecimal(0.05)), new Category("Food"));
    public static final Product PAPER = new Product(new ProductName("Paper"),
            new Money(new BigDecimal(5)), new Category("Stationery"));
    public static final Product PEN = new Product(new ProductName("Pen"),
            new Money(new BigDecimal(1.5)), new Category("Stationery"));
    public static final Product PENCIL = new Product(new ProductName("Pencil"),
            new Money(new BigDecimal(1.0)), new Category("Stationery"));
    public static final Product SODA = new Product(new ProductName("Soda"),
            new Money(new BigDecimal(2.0)), new Category("Food"));

    public static List<Product> getTypicalOrders() {
        return new ArrayList<Product>(Arrays.asList(EGG, MILK, SHIRT, PANTS, LAPTOP, SWEET, PAPER, PEN, PENCIL, SODA));
    }

}
```
###### \java\seedu\address\testutil\TypicalSubOrders.java
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
