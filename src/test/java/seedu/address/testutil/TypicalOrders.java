package seedu.address.testutil;

import seedu.address.model.order.Order;
import seedu.address.model.order.SubOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalProducts.EGG;

public class TypicalOrders {

    static SubOrder subOrderOne = new SubOrder(EGG.getId(), 1, EGG.getPrice());
    static ArrayList<SubOrder> orderLists = new ArrayList<SubOrder>(Arrays.asList(subOrderOne));
    public static Order OrderOne = new Order(ALICE.getEmail().value, orderLists);
    //another OrderBuilder
}
