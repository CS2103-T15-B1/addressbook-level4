package seedu.address.model.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

public class UniqueProductList implements Iterable<Product> {

    private final ObservableList<Product> internalList = FXCollections.observableArrayList();

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Product> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Product> iterator() {
        return internalList.iterator();
    }
}