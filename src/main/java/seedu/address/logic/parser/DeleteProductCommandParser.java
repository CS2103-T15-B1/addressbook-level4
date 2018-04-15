package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteProductCommand object
 */
public class DeleteProductCommandParser implements Parser<DeleteProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProductCommand
     * and returns an DeleteProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProductCommand parse(String args) throws ParseException {
        try {
            int id = ParserUtil.parseID(args);
            return new DeleteProductCommand(id);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProductCommand.MESSAGE_USAGE));
        }
    }

}

