package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.product.Product;
import seedu.address.model.product.exceptions.DuplicateProductException;

//@@author qinghao1
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