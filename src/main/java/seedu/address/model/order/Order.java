package seedu.address.model.order;

import seedu.address.model.util.Triple;
import seedu.address.model.money.Money;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a customer's order.
 * Guarantees: details are present and not null, field values are validated, immutable
 */

public class Order {
    private static int orderCounter = 0;

    private final String personId;
    private final int id;
    private final LocalDateTime time;
    private final List<Triple<Integer, Integer, Money>> orderList;


    /** Every field must be present and not null.
     * @param personId id of person (customer) who made the order. Can be thought of as a foreign key
     * @param orderList ArrayList of triple(product id, number bought, price) to represent the order
     */
    public Order(String personId, List<Triple<Integer, Integer, Money>> orderList) {
        this.id = ++orderCounter;
        this.time = LocalDateTime.now();
        this.personId = personId;
        this.orderList = orderList;
    }

    /**
     * Returns ID(i.e. email) of person who made the order.
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Returns order ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns time of order
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Gets the details of the products and prices for an order.
     * @return List of (Product ID, Number bought, Price)
     */
    public List<Triple<Integer, Integer, Money>> getOrderList() {
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

    @Override
    public boolean equals(Object other) {
        return other == this ||
                ((other instanceof Order) &&
                        ((Order) other).getPersonId() == this.getPersonId() &&
                        ((Order) other).getId() == this.getId() &&
                        ((Order) other).getOrderList() == this.getOrderList()
                );
    }
}
