package seedu.address.logic.recommender;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

//@@author lowjiajin
public class ArffWriter {

    private final ObservableList<Person> persons;
    private final ObservableList<Product> products;
    private final ObservableList<Order> orders;

    public ArffWriter(ReadOnlyAddressBook addressBook) {
        persons = addressBook.getPersonList();
        products = addressBook.getProductList();
        orders = addressBook.getOrderList();
    }

    public void convertOrdersToArff() {

    }

}
