package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * MAPs a person identified using it's last displayed index from the address book.
 */
public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the geographic distribution of all customers in Retail Analytics.\n"
            + "Parameters: QUERY (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " all";





    public static final String MESSAGE_MAP_PERSON_SUCCESS = "Number of customers displayed: %1$s";

    private final String query;

    public MapCommand(String query) {
        this.query = query;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

//        if (targetIndex.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//
//        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_MAP_PERSON_SUCCESS, lastShownList.size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.query.equals(((MapCommand) other).query)); // state check
    }
}
