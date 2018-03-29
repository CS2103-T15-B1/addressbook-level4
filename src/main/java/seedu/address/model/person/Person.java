package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.*;

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

//    public List<Order> getOrders() {
//        //TODO Use order getter to find orders belonging to this person
//    }

    /**
     * Get orders made equals or after to a certain time
     * @param time
     * @return orders after certain time
     */
//    public List<Order> getOrdersSinceTime(LocalDateTime time) {
//        //TODO Use order getter to find orders belonging to this person
//    }

    /**
     * Get time of most recent order
     * @return last order time
     */
//    public LocalDateTime getLastOrderTime() {
//        List<Order> allOrders = getOrders();
//        Order lastOrder = allOrders.get(allOrders.size() - 1);
//        return lastOrder.getTime();
//    }

    /**
     * Get time of most recent order
     * @return last order time
     */
//    public LocalDateTime getLastOrderTime() {
//        List<Order> allOrders = getOrders();
//        Order lastOrder = allOrders.get(allOrders.size() - 1);
//        return lastOrder.getTime();
//    }

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
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getGender().equals(this.getGender())
                && otherPerson.getAge().equals(this.getAge())
                && otherPerson.getLatitude().equals(this.getLatitude())
                && otherPerson.getLongitude().equals(this.getLongitude())
                ;
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
