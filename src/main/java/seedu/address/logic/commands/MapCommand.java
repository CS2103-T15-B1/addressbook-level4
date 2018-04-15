package seedu.address.logic.commands;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RenderMapEvent;
import seedu.address.model.person.Person;

/**
 * @author Sivalavida
 * Displays the geographic distribution of queried customers in Retail Analytics.
 */
public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";

    //query words
    public static final String ALL_PERSONS_QUERY_WORD = "all";
    public static final String NAME_CONTAINS_KEYWORDS_QUERY_WORD = "name";
    public static final String WITHIN_AGE_RANGE_QUERY_WORD = "age";
    public static final String GENDER_QUERY_WORD = "gen";
    public static final String CONTAINS_TAG_QUERY_WORD = "tag";

    //number of parameters (including query word) for respective query words
    public static final int ALL_PERSONS_QUERY_NUM_PARAMETERS = 0;
    public static final int NAME_CONTAINS_KEYWORDS_QUERY_MIN_NUM_PARAMETERS = 1; //min
    public static final int WITHIN_AGE_RANGE_QUERY_NUM_PARAMETERS = 2;
    public static final int IS_GENDER_QUERY_NUM_PARAMETERS = 1;
    public static final int CONTAINS_TAG_QUERY_NUM_PARAMETERS = 1; //min

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the geographic distribution of queried customers in Retail Analytics.\n"
            + "Query 1) " + ALL_PERSONS_QUERY_WORD + ": Select all customers - Parameters: none \n"
            + "Examples: " + COMMAND_WORD + " " + ALL_PERSONS_QUERY_WORD + "\n"
            + "Query 2) " + NAME_CONTAINS_KEYWORDS_QUERY_WORD + ": Select customer if keyword in name  - Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Examples: " + COMMAND_WORD + " " + NAME_CONTAINS_KEYWORDS_QUERY_WORD + " Alex Bernice\n"
            + "Query 3) " + WITHIN_AGE_RANGE_QUERY_WORD + ": Select customer if age within age range - Parameters: MIN_AGE MAX_AGE\n"
            + "Examples: " + COMMAND_WORD + " " + WITHIN_AGE_RANGE_QUERY_WORD + " 15 56\n"
            + "Query 4) " + GENDER_QUERY_WORD + ": Select customers of specified gender - Parameters: GENDER\n"
            + "Examples: " + COMMAND_WORD + " " + GENDER_QUERY_WORD + " M\n"
            + "Query 5) " + CONTAINS_TAG_QUERY_WORD + ": Select customer with any of the specified tags - Parameters: TAG\n"
            + "Examples: " + COMMAND_WORD + " " + CONTAINS_TAG_QUERY_WORD + " friends";

    public static final String MESSAGE_MAP_PERSON_SUCCESS = "Number of customers displayed on map: %1$s";

    private final Predicate predicate;

    public MapCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        List<Person> updatedPersonList = model.getFilteredPersonList();//see find command when using query
        EventsCenter.getInstance().post(new RenderMapEvent(updatedPersonList));
        return new CommandResult(String.format(MESSAGE_MAP_PERSON_SUCCESS, updatedPersonList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.predicate.equals(((MapCommand) other).predicate)); // state check
    }
}
