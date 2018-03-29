package seedu.address.model.order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueOrderList implements Iterable<Order> {

    private final ObservableList<Order> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an order
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a new order to the list
     * @param toAdd unique new order to be added. It shouldn't exist in list.
     */
    public void add(Order toAdd) throws DuplicateOrderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateOrderException();
        }
        internalList.add(toAdd);
    }

    /**
     * Sets an order list to a new one.
     * @param replacement the new list
     */
    public void setOrders(UniqueOrderList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    /**
     * Creates a new order list based on a list of distinct order objects.
     * @param orders list of orders
     */
    public void setOrders(List<Order> orders) throws DuplicateOrderException {
        requireAllNonNull(orders);
        final UniqueOrderList replacement = new UniqueOrderList();
        for (final Order order : orders) {
            replacement.add(order);
        }
        setOrders(replacement);
    }

    /**
     * Returns true if the list contains an order
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a new order to the list
     * @param toAdd unique new order to be added. It shouldn't exist in list.
     */
    public void add(Order toAdd) throws DuplicateOrderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateOrderException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent order from the list.
     *
     * @throws OrderNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Order toRemove) throws OrderNotFoundException {
        requireNonNull(toRemove);
        final boolean orderFoundAndDeleted = internalList.remove(toRemove);
        if (!orderFoundAndDeleted) {
            throw new OrderNotFoundException();
        }
        return orderFoundAndDeleted;
    }

    /**
     * Sets an order list to a new one.
     * @param replacement the new list
     */
    public void setOrders(UniqueOrderList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    /**
     * Creates a new order list based on a list of distinct order objects.
     * @param orders list of orders
     */
    public void setOrders(List<Order> orders) throws DuplicateOrderException {
        requireAllNonNull(orders);
        final UniqueOrderList replacement = new UniqueOrderList();
        for (final Order order : orders) {
            replacement.add(order);
        }
        setOrders(replacement);
    }

    /**
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     *
     * @throws DuplicateOrderException if the replacement is equivalent to another existing order in the list.
     * @throws OrderNotFoundException if {@code target} could not be found in the list.
     */
    public void setOrder(Order target, Order editedPerson)
            throws DuplicateOrderException, OrderNotFoundException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicateOrderException();
        }

        internalList.set(index, editedPerson);
    }

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
