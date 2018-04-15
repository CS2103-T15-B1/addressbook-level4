package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.money.Money;
import seedu.address.model.order.SubOrder;

import static seedu.address.testutil.TypicalProducts.*;

//@@author qinghao1
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