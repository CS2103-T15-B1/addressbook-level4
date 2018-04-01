package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RenderMapEvent;
import seedu.address.model.person.Person;

/**
 * Displays the geographic distribution of selected customers in Retail Analytics.
 */
public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the geographic distribution of selected customers in Retail Analytics.\n"
            + "Parameters: QUERY (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " all";

    public static final String MESSAGE_MAP_PERSON_SUCCESS = "Number of customers displayed on map: %1$s";

    private final String query;

    public MapCommand(String query) {
        this.query = query;
    }

    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList();//see find command when using querry
        EventsCenter.getInstance().post(new RenderMapEvent(lastShownList));
        return new CommandResult(String.format(MESSAGE_MAP_PERSON_SUCCESS, lastShownList.size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.query.equals(((MapCommand) other).query)); // state check
    }
}
