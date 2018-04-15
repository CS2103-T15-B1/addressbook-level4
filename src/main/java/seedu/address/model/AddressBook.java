package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.product.Product;
import seedu.address.model.product.UniqueProductList;
import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.product.exceptions.ProductNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueProductList products;
    private final UniqueOrderList orders;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        products = new UniqueProductList();
        orders = new UniqueOrderList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    //@@author lowjiajin
    public void setProducts(List<Product> products) throws DuplicateProductException{
        this.products.setProducts(products);
    }

    public void setOrders(List<Order> orders) throws DuplicateOrderException {
        this.orders.setOrders(orders);
    }

    //@@author
    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));

        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Product> syncedProductList = newData.getProductList().stream()
                .collect(Collectors.toList());
        List<Order> syncedOrderList = newData.getOrderList().stream()
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
            setProducts(syncedProductList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        } catch (DuplicateProductException ep) {
            throw new AssertionError("AddressBooks should not have duplicate products");
        }

        //@@author lowjiajin
        try {
            setProducts(newData.getProductList());
        } catch (DuplicateProductException dpe) {

        }

        try {
            setOrders(newData.getOrderList());
        } catch (DuplicateOrderException doe) {

        }
    }

    //@@author
    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getGender(),
                person.getAge(), person.getLatitude(), person.getLongitude(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// product-level operations

    /**
     * Adds a product to the address book.
     */
    public void addProduct(Product p) throws DuplicateProductException {
        //Product product = syncWithMasterTagList(p);
        //Maybe need to synchronize with CategoryList in the future.
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        products.add(p);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ProductNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeProduct(Product key) throws ProductNotFoundException {
        if (products.remove(key)) {
            return true;
        } else {
            throw new ProductNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// order-level operations

    /**
     * Adds new order to address book.
     * @throws DuplicateOrderException if this order already exists.
     */
    public void addOrder(Order o) throws DuplicateOrderException {
        orders.add(o);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     *
     * @throws DuplicateOrderException if updating the order's details causes the order to be equivalent to
     *      another existing order.
     * @throws OrderNotFoundException if {@code target} could not be found.
     */
    public void updateOrder(Order target, Order editedOrder)
            throws DuplicateOrderException, OrderNotFoundException {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws OrderNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeOrder(Order key) throws OrderNotFoundException {
        if (orders.remove(key)) {
            return true;
        } else {
            throw new OrderNotFoundException();
        }
    }


    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<Product> getProductList() {
        return products.asObservableList();
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
