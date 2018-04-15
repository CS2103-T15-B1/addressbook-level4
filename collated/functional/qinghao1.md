# qinghao1
###### \java\seedu\address\logic\commands\DeleteOrderCommand.java
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
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Overload parseCommand for commands that do not need addressBook, to maintain backwards compatibility
     * @param userInput
     * @return
     * @throws ParseException
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case AddProductCommand.COMMAND_WORD:
                return new AddProductCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case MapCommand.COMMAND_WORD:
                return new MapCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case DeleteProductCommand.COMMAND_WORD:
                return new DeleteProductCommandParser().parse(arguments);

            case DeleteOrderCommand.COMMAND_WORD:
                return new DeleteOrderCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

```
