package seedu.address.model.order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

import java.util.Iterator;

public class UniqueOrderList implements Iterable<Order> {

    private final ObservableList<Order> internalList = FXCollections.observableArrayList();

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Order> iterator() {
        return internalList.iterator();
    }
}
