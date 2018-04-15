# qinghao1
###### /java/seedu/address/logic/parser/AddOrderCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddOrderCommand object
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL, PREFIX_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL, PREFIX_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        try {
            //Note: We use email String as Order foreign key
            String email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get().toString();

            List<SubOrder> subOrderList =
                    ParserUtil.parseSubOrders(argMultimap.getAllValues(PREFIX_ORDER));

            Order order = new Order(email, subOrderList);
            return new AddOrderCommand(order);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/address/logic/commands/AddOrderCommand.java
``` java
/**
 * Adds an order to the address book
 */
public class AddOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates new order given a person's email, and non-empty list of (Product ID, Number bought, Price)\n"
            + "Parameters:"
            + PREFIX_EMAIL + "EMAIL (Must be an existing person)"
            + PREFIX_ORDER + "SUBORDER (List of product ID, Number, Price)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "john@example.com "
            + PREFIX_ORDER + "1 5 $3.00 "
            + PREFIX_ORDER + "2 4 $2.50 "
            + PREFIX_ORDER + "3 1 $100 ";

    public static final String MESSAGE_SUCCESS = "New order added.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the app.";
    public static final String MESSAGE_INVALID_ORDER = "The order is invalid. Check that the person and products exist.";

    private final Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specified {@code Order}
     */
    public AddOrderCommand(Order order) {
        requireNonNull(order);
        toAdd = order;
    }

    /**
     * Checks that the add order command is valid (order to be created is valid)
     */
    public boolean isValid() {
        ReadOnlyAddressBook ab = this.model.getAddressBook();
        return toAdd.isValid(ab.getPersonList(), ab.getProductList());
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        //Check that order is valid
        if (!isValid()) {
            //If order invalid, decrement order counter so orders have sequential ID
            Order.decrementOrderCounter();
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }
        try {
            model.addOrder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateOrderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/DeleteOrderCommand.java
``` java
/**
 * Deletes a order identified using its id from the address book.
 */
public class DeleteOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by its id.\n"
            + "Parameters: ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";
    public static final String MESSAGE_INVALID_ORDER = "The order is invalid. Check that the order ID is correct.";

    private final int targetID;

    private Order orderToDelete;

    public DeleteOrderCommand(int targetID) {
        this.targetID = targetID;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(orderToDelete);
        try {
            model.deleteOrder(orderToDelete);
        } catch (OrderNotFoundException e) {
            throw new AssertionError("The target order cannot be missing.");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Order> lastShownList = model.getFilteredOrderList();
        orderToDelete = null;
        //There should only be one order that matches the ID
        for(Order order : lastShownList) {
            if(order.getId() == targetID)
                orderToDelete = order;
        }
        if(orderToDelete == null) {
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetID == ((DeleteOrderCommand) other).targetID // state check
                && Objects.equals(this.orderToDelete, ((DeleteOrderCommand) other).orderToDelete));
    }
}

```
###### /java/seedu/address/storage/XmlAdaptedSubOrder.java
``` java
/**
 * JAXB-friendly version of the SubOrder.
 */
public class XmlAdaptedSubOrder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "SubOrder's %s field is missing!";

    @XmlElement(required = true)
    private String productId;
    @XmlElement(required = true)
    private String numProduct;
    @XmlElement(required = true)
    private String productPrice;

    /**
     * Constructs an XmlAdaptedSubOrder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSubOrder() {}

    /**
     * Constructs an {@code XmlAdaptedSubOrder} with the given sub order details.
     */
    public XmlAdaptedSubOrder(String productId, String numProduct, String productPrice) {
        this.productId = productId;
        this.numProduct = numProduct;
        this.productPrice = productPrice;
    }

    /**
     * Converts a given SubOrder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSubOrder
     */
    public XmlAdaptedSubOrder(SubOrder source) {
        productId = String.valueOf(source.getProductID());
        numProduct = String.valueOf(source.getNumProduct());
        productPrice = source.getProductPrice().toString();
    }

    /**
     * Converts this jaxb-friendly adapted sub order object into the model's SubOrder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public SubOrder toModelType() throws IllegalValueException {
        if (this.productId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "productId"));
        }
        final int productId = Integer.parseInt(this.productId);

        if (this.numProduct == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "numProduct"));
        }
        final int numProduct = Integer.parseInt(this.numProduct);

        if (this.productPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "productPrice"));
        }
        //Throws IllegalValueException if productPrice string isn't valid
        final Money productPrice = Money.parsePrice(this.productPrice);

        return new SubOrder(productId, numProduct, productPrice);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSubOrder)) {
            return false;
        }

        XmlAdaptedSubOrder otherSubOrder = (XmlAdaptedSubOrder) other;
        return Objects.equals(productId, otherSubOrder.productId)
                && Objects.equals(numProduct, otherSubOrder.numProduct)
                && Objects.equals(productPrice, otherSubOrder.productPrice);
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedProduct.java
``` java
/**
 * JAXB-friendly version of the Product.
 */
public class XmlAdaptedProduct {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String category;

    /**
     * Constructs an XmlAdaptedProduct.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProduct() {}

    /**
     * Constructs an {@code XmlAdaptedProduct} with the given product details.
     */
    public XmlAdaptedProduct(String id, String name, String price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * Converts a given Product into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedProduct
     */
    public XmlAdaptedProduct(Product source) {
        id = String.valueOf(source.getId());
        name = source.getName().toString();
        price = source.getPrice().toString();
        category = source.getCategory().toString();
    }

    /**
     * Converts this jaxb-friendly adapted product object into the model's Product object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product
     */
    public Product toModelType() throws IllegalValueException {
        if (this.id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "id"));
        }
        final int id = Integer.parseInt(this.id);

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ProductName.class.getSimpleName()));
        }
        if (!ProductName.isValidProductName(this.name)) {
            throw new IllegalValueException(ProductName.MESSAGE_PRODUCT_NAME_CONSTRAINTS);
        }
        final ProductName name = new ProductName(this.name);

        if (this.price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Price"));
        }
        final Money price = Money.parsePrice(this.price); //Throws IllegalValueException if price string isn't valid

        if (this.category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(this.category)) {
            throw new IllegalValueException(Category.MESSAGE_CATEGORY_CONSTRAINTS);
        }
        final Category category = new Category(this.category);

        return new Product(id, name, price, category);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedProduct)) {
            return false;
        }

        XmlAdaptedProduct otherProduct = (XmlAdaptedProduct) other;
        return Objects.equals(id, otherProduct.id)
                && Objects.equals(name, otherProduct.name)
                && Objects.equals(price, otherProduct.price)
                && Objects.equals(category, otherProduct.category);
    }

}
```
###### /java/seedu/address/storage/XmlAdaptedOrder.java
``` java
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
```
###### /java/seedu/address/model/order/Order.java
``` java
/**
 * Represents a customer's order.
 * Guarantees: details are present and not null, field values are validated, immutable
 */

public class Order {
    private static int orderCounter = 0;

    private final String personId;
    private final int id;
    private final LocalDateTime time;
    private final List<SubOrder> subOrders;


    /**
     * Adds order with personId(email) and list of suborders. Every field must be present and not null.
     * @param personId id of person (customer) who made the order. Can be thought of as a foreign key
     * @param subOrders ArrayList of triple(product id, number bought, price) to represent the order
     */
    public Order(String personId, List<SubOrder> subOrders) {
        this.id = ++orderCounter;
        this.time = LocalDateTime.now();
        this.personId = personId;
        this.subOrders = subOrders;
    }

    /**
     * Adds order with person object instead of email String. To be used for debugging, testing etc.
     * @param person
     * @param subOrders
     */
    public Order(Person person, List<SubOrder> subOrders) {
        this.id = ++orderCounter;
        this.time = LocalDateTime.now();
        this.personId = person.getEmail().toString();
        this.subOrders = subOrders;
    }

    /**
     * Adds a order with specified id and time. Used for regenerating order list from storage.
     * Note that this sets the orderCounter to the maximum id added into the list, to ensure distinctness of
     * order ids.
     * @param id
     * @param personId
     * @param time
     * @param subOrders
     */
    public Order(int id, String personId, LocalDateTime time, List<SubOrder> subOrders) {
        this.id = id;
        this.time = time;
        this.personId = personId;
        this.subOrders = subOrders;
        orderCounter = Math.max(orderCounter, id);
    }

    /**
     * Adds a order with specified id and time, using Person object instead of email string.
     * For debugging, testing, etc.
     * @param id
     * @param person
     * @param time
     * @param subOrders
     */
    public Order(int id, Person person, LocalDateTime time, List<SubOrder> subOrders) {
        this.id = id;
        this.time = time;
        this.personId = person.getEmail().toString();
        this.subOrders = subOrders;
        orderCounter = Math.max(orderCounter, id);
    }

    /**
     * Returns ID(i.e. email) of person who made the order.
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Returns order ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns time of order
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Gets the details of the products and prices for an order.
     * @return List of (Product ID, Number bought, Price)
     */
    public List<SubOrder> getSubOrders() {
        return subOrders;
    }

    /**
     * Calculates total price (sum) of an order
     * @return total price
     */
    public Money getOrderTotal() {
        Money total = new Money();
        for (SubOrder subOrder : subOrders) {
            Money subOrderPrice = subOrder.getTotalPrice();
            total = total.plus(subOrderPrice);
        }
        return total;
    }

    /**
     * Performs some basic checks to see if order is valid.
     * - Checks that customer email is valid
     * - Checks that all product IDs exist
     * @param customers list of all customers to check email against
     * @param products list of all products to check product ID against
     * @return validity
     */
    public boolean isValid(List<Person> customers, List<Product> products) {
        boolean valid = true;

        //Check that email is valid
        boolean foundEmail = false;
        for (Person customer : customers) {
            String email = customer.getEmail().toString();
            if (email.equals(personId)) {
                foundEmail = true;
                break;
            }
        }
        valid = foundEmail && valid; // Trip valid to false if email not found

        //Check that productIDs are valid (using SubOrder class)
        boolean allSubOrdersValid = true;
        for (SubOrder subOrder : subOrders) {
            if (!subOrder.isValid(products)) {
                allSubOrdersValid = false;
                break;
            }
        }
        valid = allSubOrdersValid && valid;

        // Check that total order price is non-negative
        boolean negativeOrderPrice = this.getOrderTotal().isMinus();
        valid = !negativeOrderPrice && valid;

        return valid;
    }

    /**
     * Decrements order counter. To be used when deleting invalid order.
     */
    public static void decrementOrderCounter() {
        orderCounter = Math.max(orderCounter - 1, 0);
    }

    /**
     * Returns a table in string format which is the order summary (product id, number bought, price, total price)
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Order ID ");
        sb.append(id);
        sb.append(" by ");
        sb.append(personId);
        sb.append(" at ");
        sb.append(time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        sb.append(": \n");
        sb.append("-------------\n");
        int subOrderCt = 0;
        for (SubOrder so : subOrders) {
            sb.append(++subOrderCt);
            sb.append("- ");
            sb.append(so.toString());
            sb.append('\n');
        }
        sb.append("-------------\n");
        sb.append("Total: ");
        sb.append(getOrderTotal());
        sb.append("\n-------------\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this ||
                ((other instanceof Order) &&
                        ((Order) other).getPersonId().equals(this.getPersonId()) &&
                        ((Order) other).getId() == this.getId() &&
                        ((Order) other).getSubOrders() == this.getSubOrders()
                );
    }
}
```
###### /java/seedu/address/model/order/OrderBelongsToPeoplePredicate.java
``` java
/**
 * Predicate to filter orders that belong to people in a given list.
 */
public class OrderBelongsToPeoplePredicate implements Predicate<Order> {
    private final List<String> emails;

    public OrderBelongsToPeoplePredicate(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public boolean test(Order order) {
        //Returns true if the order's personId (i.e. email) is in the list of emails
        return emails.stream()
                .anyMatch(email -> email.equals(order.getPersonId()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderBelongsToPeoplePredicate // instanceof handles nulls
                && this.emails.equals(((OrderBelongsToPeoplePredicate) other).emails)); // state check
    }
}
```
###### /java/seedu/address/model/order/SubOrder.java
``` java
/**
 * SubOrder class for each single product purchased in an Order. Should be composited with Order
 * (i.e. can't exist without Order object which has pointer to this SubOrder object)
 *
 * Guarantees: SubOrder details are present and immutable
 */
public class SubOrder {
    public static final String MESSAGE_SUBORDER_CONSTRAINTS =
            "Sub-Orders needs to have three elements, first two of which are integers representing product ID and"+
                    " number of that product bought, and the price of the product as purchased.";

    private final int productID;
    private final int numProduct;
    private final Money productPrice;

    /** Every field must be present and non-null. */
    public SubOrder(int id, int num, Money price) {
        productID = id;
        numProduct = num;
        productPrice = price;
    }

    public int getProductID() {
        return productID;
    }

    public int getNumProduct() {
        return numProduct;
    }

    public Money getProductPrice() {
        return productPrice;
    }

    public Money getTotalPrice() {
        return productPrice.times(numProduct);
    }

    /**
     * Checks that suborder is valid
     * - product ID is in list of products
     * - productPrice is non-negative
     * @param products list of products to check against (global list)
     * @return validity
     */
    public boolean isValid(List<Product> products) {
        boolean valid = true;
        boolean idFound = false;
        for (Product product : products) {
            if (product.getId() == productID) {
                idFound = true;
                break;
            }
        }
        valid = idFound && valid; //Trip valid to false if product isn't found

        boolean negativeProductPrice = productPrice.isMinus();
        valid = !negativeProductPrice && valid;

        return valid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Product ");
        sb.append(productID);
        sb.append(" x");
        sb.append(numProduct);
        sb.append(" @");
        sb.append(productPrice);
        return sb.toString();
    }
}
```
