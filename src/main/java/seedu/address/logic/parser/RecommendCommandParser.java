package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RecommendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;

//@@author lowjiajin

/**
 * Parses input arguments and creates a new RecommendCommand object
 */
public class RecommendCommandParser implements Parser<RecommendCommand> {

    private ReadOnlyAddressBook addressBook;

    /**
     * Parses the given {@code String} of arguments in the context of the RecommendCommand
     * and returns a RecommendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RecommendCommandParser(ReadOnlyAddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public RecommendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RecommendCommand(index, addressBook);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecommendCommand.MESSAGE_USAGE));
        }
    }
}