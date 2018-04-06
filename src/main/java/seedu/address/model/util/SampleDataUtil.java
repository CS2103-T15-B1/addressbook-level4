package seedu.address.model.util;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.money.Money;
import seedu.address.model.order.Order;
import seedu.address.model.order.SubOrder;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.person.*;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.product.Category;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    //@@author lowjiajin
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alex@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Gender("M"), new Age("15"),
                new Latitude("1.339160"), new Longitude("103.745133"),getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("bernice@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Gender("F"), new Age("15"),
                new Latitude("1.339456"), new Longitude("103.745111"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Gender("F"),new Age("56"),
                new Latitude("1.339123"), new Longitude("103.745233"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("david@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Gender("M"), new Age("23"),
                new Latitude("1.339678"), new Longitude("103.745166"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Gender("M"), new Age("77"),
                new Latitude("1.339957"), new Longitude("103.745234"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("roy@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Gender("M"), new Age("48"),
                new Latitude("1.339454"), new Longitude("103.745166"), getTagSet("colleagues")),
            new Person(new Name("Tan Roo Yang"), new Phone("97776590"), new Email("rooyang@example.com"),
                new Address("Blk 55 Tiong Bahru Lorong 4, #03-10"), new Gender("M"), new Age("23"),
                new Latitude("1.339432"), new Longitude("103.745200"), getTagSet("colleagues")),
            new Person(new Name("Linda Gao"), new Phone("81226734"), new Email("linda@example.com"),
                new Address("Rio Casa, Punggol Avenue 8, #05-33"), new Gender("F"), new Age("22"),
                new Latitude("1.339416"), new Longitude("103.745100"), getTagSet("colleagues")),
            new Person(new Name("Zelene Quek"), new Phone("81226734"), new Email("zelene@example.com"),
                new Address("12D Philips Avenue"), new Gender("F"), new Age("61"),
                new Latitude("1.339890"), new Longitude("103.745300"), getTagSet("family"))
        };
    }

    public static Product[] getSampleProducts() {
        return new Product[] {
            new Product(new ProductName("TrendyShirt"), new Money(new BigDecimal(12)),
                new Category("Clothing")),
            new Product(new ProductName("Dentures"), new Money(new BigDecimal(200)),
                new Category("Healthcare")),
            new Product(new ProductName("Lipstick"), new Money(new BigDecimal(30)),
                new Category("Cosmetics")),
            new Product(new ProductName("Toothbrush"), new Money(new BigDecimal(5)),
                new Category("Healthcare"))
        };
    }

    public static Order[] getSampleOrders() {
        return new Order[] {
            new Order("alex@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12))),
                    new SubOrder(4, 1, new Money(new BigDecimal(6)))).collect(Collectors.toList())),
            new Order("bernice@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12))),
                    new SubOrder(3, 1, new Money(new BigDecimal(30)))).collect(Collectors.toList())),
            new Order("charlotte@example.com", Stream.of(
                    new SubOrder(2, 1, new Money(new BigDecimal(200))),
                    new SubOrder(3, 1, new Money(new BigDecimal(29)))).collect(Collectors.toList())),
            new Order("david@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12)))).collect(Collectors.toList())),
            new Order("irfan@example.com", Stream.of(
                    new SubOrder(2, 1, new Money(new BigDecimal(200))),
                    new SubOrder(4, 1, new Money(new BigDecimal(5)))).collect(Collectors.toList())),
            new Order("roy@example.com", Stream.of(
                    new SubOrder(4, 1, new Money(new BigDecimal(4)))).collect(Collectors.toList())),
            new Order("rooyang@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(13)))).collect(Collectors.toList())),
            new Order("linda@example.com", Stream.of(
                    new SubOrder(1, 1, new Money(new BigDecimal(12))),
                    new SubOrder(3, 1, new Money(new BigDecimal(30))),
                    new SubOrder(4, 1, new Money(new BigDecimal(5)))).collect(Collectors.toList())),
            new Order("zelene@example.com", Stream.of(
                    new SubOrder(2, 1, new Money(new BigDecimal(230)))).collect(Collectors.toList()))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Product sampleProduct : getSampleProducts()) {
                sampleAb.addProduct(sampleProduct);
            }
            for (Order sampleOrder : getSampleOrders()) {
                sampleAb.addOrder(sampleOrder);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateProductException e) {
            throw new AssertionError("sample data cannot contain duplicate products", e);
        } catch (DuplicateOrderException e) {
            throw new AssertionError("sample data cannot contain duplicate orders", e);
        }
    }

    //@@author
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
