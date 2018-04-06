package seedu.address.logic.parser;

import seedu.address.logic.commands.FindProductByNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductNameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author lowjiajin
public class FindProductByNameCommandParser implements Parser<FindProductByNameCommand> {

    public FindProductByNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByNameCommand.MESSAGE_USAGE));
        }

        String[] categoryKeywords = trimmedArgs.split("\\s+");

        return new FindProductByNameCommand(
                new ProductNameContainsKeywordsPredicate(Arrays.asList(categoryKeywords)));
    }
}