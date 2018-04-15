package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.order.SubOrder;
import seedu.address.model.person.Email;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

//@@author qinghao1
/**
 * JAXB-friendly version of the Order.
 */
public class XmlAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";
    public static final String MESSAGE_TIME_CONSTRAINTS = "Time needs to be in ISO-8601 format.";

    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String personId;
    @XmlElement(required = true)
    private String time;

    @XmlElement
    private List<XmlAdaptedSubOrder> subOrders = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedOrder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedOrder() {}

    /**
     * Constructs an {@code XmlAdaptedOrder} with the given product details.
     */
    public XmlAdaptedOrder(String id, String personId, String time, List<XmlAdaptedSubOrder> subOrders) {
        this.id = id;
        this.personId = personId;
        this.time = time;
        this.subOrders = subOrders;
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOrder
     */
    public XmlAdaptedOrder(Order source) {
        id = String.valueOf(source.getId());
        personId = source.getPersonId();
        time = source.getTime().toString();
        for (SubOrder so : source.getSubOrders())
            subOrders.add(new XmlAdaptedSubOrder(so));
    }

    /**
     * Converts this jaxb-friendly adapted order object into the model's Order object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public Order toModelType() throws IllegalValueException {
        if (this.id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "id"));
        }
        final int id = Integer.parseInt(this.id);

        // Note that we are using email as personId (i.e. foreign key) here
        if (this.personId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "personId"));
        }
        if (!Email.isValidEmail(this.personId)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final String personId = this.personId;

        if (this.time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Time"));
        }
        final LocalDateTime time;
        try {
            time = LocalDateTime.parse(this.time);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        final List<SubOrder> soList = new ArrayList<>();
        for (XmlAdaptedSubOrder so : subOrders) {
            soList.add(so.toModelType());
        }

        return new Order(id, personId, time, soList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedOrder)) {
            return false;
        }

        XmlAdaptedOrder otherOrder = (XmlAdaptedOrder) other;
        return Objects.equals(id, otherOrder.id)
                && Objects.equals(personId, otherOrder.personId)
                && Objects.equals(time, otherOrder.time)
                && subOrders.equals(otherOrder.subOrders);
    }

}
