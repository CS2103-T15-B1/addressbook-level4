package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.OrderCardHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ProductCardHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualPersonCard} displays the same values as {@code expectedPersonCard}.
     */
    public static void assertPersonCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        assertEquals(expectedCard.getGender(), actualCard.getGender());
        assertEquals(expectedCard.getAge(), actualCard.getAge());
        assertEquals(expectedCard.getLatitude(), actualCard.getLatitude());
        assertEquals(expectedCard.getLongitude(), actualCard.getLongitude());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getGender().value, actualCard.getGender());
        assertEquals(expectedPerson.getAge().value, actualCard.getAge());
        assertEquals(expectedPerson.getLatitude().value, actualCard.getLatitude());
        assertEquals(expectedPerson.getLongitude().value, actualCard.getLongitude());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    //@@author He Yingxu
    /**
     * Asserts that {@code actualProductCard} displays the same values as {@code expectedProductCard}.
     */
    public static void assertProductCardEquals(ProductCardHandle expectedCard, ProductCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedProduct}.
     */
    public static void assertCardDisplaysProduct(Product expectedProduct, ProductCardHandle actualCard) {

        assertEquals(expectedProduct.getName().fullProductName, actualCard.getName());
        assertEquals(expectedProduct.getPrice().repMoney, actualCard.getPrice());
        assertEquals(expectedProduct.getCategory().value, actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualOrderCard} displays the same values as {@code expectedOrderCard}.
     */
    public static void assertOrderCardEquals(OrderCardHandle expectedCard, OrderCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPersonId(), actualCard.getPersonId());
        assertEquals(expectedCard.getTime(), actualCard.getTime());
        assertEquals(expectedCard.getSubOrders(), actualCard.getSubOrders());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedProduct}.
     */
    public static void assertCardDisplaysOrder(Order expectedOrder, OrderCardHandle actualOrder) {
        assertEquals(expectedOrder.getPersonId(), actualOrder.getPersonId());
        assertEquals(expectedOrder.getTime(), actualOrder.getTime());
    }


    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
