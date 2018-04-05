package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RecommendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;

//@@author lowjiajin
public class RecommendCommandParser implements Parser<RecommendCommand> {

    private ReadOnlyAddressBook addressBook;

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