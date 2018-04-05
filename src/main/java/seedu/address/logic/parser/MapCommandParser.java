package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new MapCommand object
 */
public class MapCommandParser implements Parser<MapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }

        return new MapCommand("all");
    }

}
