package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.SubOrder;

import static seedu.address.testutil.TypicalPersons.*;
import static seedu.address.testutil.TypicalSubOrders.*;

//@@author qinghao1
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
