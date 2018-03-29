package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.product.exceptions.ProductNotFoundException;

/**
 * A list of products that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Product#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueProductList implements Iterable<Product> {

    private final ObservableList<Product> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent product as the given argument.
     */
    public boolean contains(Product toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a product to the list.
     *
     * @throws DuplicateProductException if the product to add is a duplicate of an existing product in the list.
     */
    public void add(Product toAdd) throws DuplicateProductException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProductException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the product {@code target} in the list with {@code editedProduct}.
     *
     * @throws DuplicateProductException if the replacement is equivalent to another existing product in the list.
     * @throws ProductNotFoundException if {@code target} could not be found in the list.
     */
    public void setProduct(Product target, Product editedProduct)
            throws DuplicateProductException, ProductNotFoundException {
        requireNonNull(editedProduct);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProductNotFoundException();
        }

        if (!target.equals(editedProduct) && internalList.contains(editedProduct)) {
            throw new DuplicateProductException();
        }

        internalList.set(index, editedProduct);
    }

    /**
     * Removes the equivalent product from the list.
     *
     * @throws ProductNotFoundException if no such product could be found in the list.
     */
    public boolean remove(Product toRemove) throws ProductNotFoundException {
        requireNonNull(toRemove);
        final boolean productFoundAndDeleted = internalList.remove(toRemove);
        if (!productFoundAndDeleted) {
            throw new ProductNotFoundException();
        }
        return productFoundAndDeleted;
    }

    public void setProducts(UniqueProductList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setProducts(List<Product> products) throws DuplicateProductException {
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProductList // instanceof handles nulls
                && this.internalList.equals(((UniqueProductList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

