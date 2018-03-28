package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the products list.
     * This list will not contain any duplicate products.
     */
    ObservableList<Product> getProductList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the products list
     * This list will not contain any duplicate products.
     */
    ObservableList<Product> getProductList();

    /**
     * Returns an unmodifiable view of the orders list
     * This list will not contain any duplicate orders.
     */
    ObservableList<Order> getOrderList();
}
