package seedu.address.model.order;

import seedu.address.model.money.Money;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
     * - Checks that customer email is valid
     * - Checks that all product IDs exist
     * @param customers list of all customers to check email against
     * @param products list of all products to check product ID against
     * @return validity
     */
    public boolean isValid(List<Person> customers, List<Product> products) {
        boolean valid = true;

        //Check that email is valid
        boolean foundEmail = false;
        for (Person customer : customers) {
            String email = customer.getEmail().toString();
            if (email.equals(personId)) {
                foundEmail = true;
                break;
            }
        }
        valid = foundEmail && valid; // Trip valid to false if email not found

        //Check that productIDs are valid (using SubOrder class)
        boolean allSubOrdersValid = true;
        for (SubOrder subOrder : subOrders) {
            if (!subOrder.isValid(products)) {
                allSubOrdersValid = false;
                break;
            }
        }
        valid = allSubOrdersValid && valid;

        // Check that total order price is non-negative
        boolean negativeOrderPrice = this.getOrderTotal().isMinus();
        valid = !negativeOrderPrice && valid;

        return valid;
    }

    /**
     * Decrements order counter. To be used when deleting invalid order.
     */
    public static void decrementOrderCounter() {
        orderCounter = Math.max(orderCounter - 1, 0);
    }

    /**
     * Returns a table in string format which is the order summary (product id, number bought, price, total price)
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Order ID ");
        sb.append(id);
        sb.append(" by ");
        sb.append(personId);
        sb.append(" at ");
        sb.append(time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        sb.append(": \n");
        sb.append("-------------\n");
        int subOrderCt = 0;
        for(SubOrder so : subOrders) {
            sb.append(++subOrderCt);
            sb.append("- ");
            sb.append(so.toString());
            sb.append('\n');
        }
        sb.append("-------------\n");
        sb.append("Total: \n");
        sb.append(getOrderTotal());
        sb.append("-------------\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this ||
                ((other instanceof Order) &&
                        ((Order) other).getPersonId().equals(this.getPersonId()) &&
                        ((Order) other).getId() == this.getId() &&
                        ((Order) other).getSubOrders() == this.getSubOrders()
                );
    }
}
