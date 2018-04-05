package seedu.address.logic.parser;

import seedu.address.logic.commands.FindProductByCategoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductCategoryContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class FindProductByCategoryCommandParser implements Parser<FindProductByCategoryCommand> {

    public FindProductByCategoryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByCategoryCommand.MESSAGE_USAGE));
        }

        String[] categoryKeywords = trimmedArgs.split("\\s+");

        return new FindProductByCategoryCommand(
                new ProductCategoryContainsKeywordsPredicate(Arrays.asList(categoryKeywords)));
    }
}
