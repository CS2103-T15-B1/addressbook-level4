package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.money.Money;
import seedu.address.model.order.Order;
import seedu.address.model.order.SubOrder;
import seedu.address.testutil.TypicalAddressBook;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.*;
import static seedu.address.testutil.TypicalProducts.*;
import static seedu.address.testutil.TypicalSubOrders.*;

//@@author qinghao1
/**
 * Contains integration tests (interaction with the Model) for {@code AddOrderCommand}.
 */
public class AddOrderCommandIntegrationTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newOrder_success() throws Exception {
        List<SubOrder> subOrders = new ArrayList<>(Arrays.asList(SO_B, SO_D, SO_G, SO_H));
        Order validOrder = new Order(CARL, subOrders);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addOrder(validOrder);

        assertCommandSuccess(prepareCommand(validOrder, model), model,
                AddOrderCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Order dupOrder = model.getAddressBook().getOrderList().get(0);
        assertCommandFailure(prepareCommand(dupOrder, model), model, AddOrderCommand.MESSAGE_DUPLICATE_ORDER);
    }

    @Test
    public void execute_invalidOrder_invalidEmail_throwsCommandException() {
        //Invalid email
        List<SubOrder> subOrders1 = new ArrayList<>(Arrays.asList(SO_B, SO_D, SO_G, SO_H));
        Order invalidOrder1 = new Order("wrongemail@email.com", subOrders1);
        assertCommandFailure(prepareCommand(invalidOrder1, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void execute_invalidOrder_invalidProductId_throwsCommandException() {
        //Invalid product ID
        try {
            List<SubOrder> subOrders2 = new ArrayList<>(Arrays.asList(SO_B, SO_D, SO_G, new SubOrder(999, 1, Money.parsePrice("$5"))));
            Order invalidOrder2 = new Order(CARL, subOrders2);
            assertCommandFailure(prepareCommand(invalidOrder2, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
        } catch (IllegalValueException e) {
            //Money.parsePrice() throws IllegalValueException
        }
    }

    @Test
    public void execute_invalidOrder_negativePrice_throwsCommandException() {
        //Negative price
        Money negativePrice = new Money(new BigDecimal(-1));
        SubOrder negativeSubOrder = new SubOrder(EGG.getId(), 5, negativePrice);
        List<SubOrder> negativeSubOrderList = new ArrayList<>(Arrays.asList(negativeSubOrder));
        Order invalidOrder3 = new Order(CARL, negativeSubOrderList);
        assertCommandFailure(prepareCommand(invalidOrder3, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void execute_invalidOrder_repeatedProducts_throwsCommandException() {
        //Repeated product IDs
        List<SubOrder> repeatedSubOrders = new ArrayList<>(Arrays.asList(SO_A, SO_A));
        Order invalidOrder4 = new Order(CARL, repeatedSubOrders);
        assertCommandFailure(prepareCommand(invalidOrder4, model), model, AddOrderCommand.MESSAGE_INVALID_ORDER);
    }

    /**
     * Generates a new {@code AddOrderCommand} which upon execution, adds {@code order} into the {@code model}.
     */
    private AddOrderCommand prepareCommand(Order order, Model model) {
        AddOrderCommand command = new AddOrderCommand(order);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
