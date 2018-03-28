package seedu.address.model.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.product.exceptions.DuplicateProductException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueProductList implements Iterable<Product> {

    private final ObservableList<Product> internalList = FXCollections.observableArrayList();

    public boolean contains(Product toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    public void add(Product toAdd) throws DuplicateProductException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProductException();
        }
        internalList.add(toAdd);
    }

    public void setProducts(UniqueProductList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setProducts(List<Product> products) throws DuplicateProductException{
        requireAllNonNull(products);
        final UniqueProductList replacement = new UniqueProductList();
        for (final Product product : products) {
            replacement.add(product);
        }
        setProducts(replacement);
    }

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