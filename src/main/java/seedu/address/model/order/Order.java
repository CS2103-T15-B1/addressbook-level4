package seedu.address.model.order;

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
    private final List<SubOrder> subOrders;


    /**
     * Every field must be present and not null.
     * @param personId id of person (customer) who made the order. Can be thought of as a foreign key
     * @param subOrders ArrayList of triple(product id, number bought, price) to represent the order
     */
    public Order(String personId, List<SubOrder> subOrders) {
        this.id = ++orderCounter;
        this.time = LocalDateTime.now();
        this.personId = personId;
        this.subOrders = subOrders;
    }

    /**
     * Adds a order with specified id and time. Used for regenerating order list from storage.
     * Note that this sets the orderCounter to the maximum id added into the list, to ensure distinctness of
     * order ids.
     * @param id
     * @param personId
     * @param time
     * @param subOrders
     */
    public Order(int id, String personId, LocalDateTime time, List<SubOrder> subOrders) {
        this.id = id;
        this.time = time;
        this.personId = personId;
        this.subOrders = subOrders;
        orderCounter = Math.max(orderCounter, id);
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
    public List<SubOrder> getSubOrders() {
        return subOrders;
    }

    /**
     * Calculates total price (sum) of an order
     * @return total price
     */
    public Money getOrderTotal() {
        Money total = new Money();
        for(SubOrder subOrder : subOrders) {
            Money subOrderPrice = subOrder.getTotalPrice();
            total = total.plus(subOrderPrice);
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
        for(SubOrder product : subOrders) {
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
                        ((Order) other).getSubOrders() == this.getSubOrders()
                );
    }
}
