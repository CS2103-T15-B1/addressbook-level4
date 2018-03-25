package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.InvalidOrderException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Gender gender;
    private final Age age;
    private final Latitude latitude;
    private final Longitude longitude;

    private final UniqueTagList tags;

    private final ArrayList<Order> orders = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Gender gender,
                  Age age, Latitude latitude, Longitude longitude, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, gender, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.latitude = latitude;
        this.longitude = longitude;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    public Age getAge() {
        return age;
    }

    /**
     * Adds order to list of orders
     * @param order
     * @throws InvalidOrderException throws exception if order is invalid
     */
    public void addOrder(Order order) throws InvalidOrderException {
        if(!order.isValid()) throw new InvalidOrderException();
        orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Get orders made equals or after to a certain time
     * @param time
     * @return orders after certain time
     */
    public ArrayList<Order> getOrdersSinceTime(LocalDateTime time) {
        ArrayList<Order> ordersSinceTime = new ArrayList<>();
        for(Order order : orders) {
            if (order.getTime().compareTo(time) >= 0)
                ordersSinceTime.add(order);
        }
        return ordersSinceTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, gender, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Gender: ")
                .append(getGender())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
