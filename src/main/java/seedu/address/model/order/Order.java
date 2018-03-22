package seedu.address.model.order;

import seedu.address.model.util.Triple;
import seedu.address.model.money.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a customer's order.
 * Guarantees: details are present and not null, field values are validated, immutable
 */

public class Order {
    private static int orderCounter = 0;

    private final int personId;
    private final int id;
    private final LocalDateTime time;
    private final ArrayList<Triple<Integer, Integer, Money>> orderList;


    /** Every field must be present and not null.
     * @param personId id of person (customer) who made the order. Can be thought of as a foreign key
     * @param orderList ArrayList of triple(product id, number bought, price) to represent the order
     */
    public Order(int personId, ArrayList<Triple<Integer, Integer, Money>> orderList) {
        this.id = ++orderCounter;
        this.time = LocalDateTime.now();
        this.personId = personId;
        this.orderList = orderList;
    }

    public int getPersonId() {
        return personId;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public ArrayList<Triple<Integer, Integer, Money>> getOrderList() {
        return orderList;
    }

    /**
     * Calculates total price (sum) of an order
     * @return total price
     */
    public Money getOrderTotal() {
        Money total = new Money();
        for(Triple<Integer, Integer, Money> product : orderList) {
            Money price = product.getThird();
            int numItems = product.getSecond();
            Money itemTotal = price.times(numItems);
            total = total.plus(itemTotal);
        }
        return total;
    }

    /**
     * Performs some basic checks to see if order is valid.
     * @return
     */
    public boolean isValid() {
        boolean valid = true;
        // TODO Check if all products exists

        // Check that total order price is non-negative
        valid = valid && !this.getOrderTotal().isMinus();

        return valid;
    }

    /**
     * Returns a table in string format which is the order summary (product name, number bought, price, total price)
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Triple<Integer, Integer, Money> product : orderList) {
            // TODO get product by id, and convert to string
        }
        return sb.toString();
    }
}
