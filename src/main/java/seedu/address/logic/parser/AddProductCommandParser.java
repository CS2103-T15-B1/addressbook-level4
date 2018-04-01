package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.money.Money;
import seedu.address.model.product.*;

/**
 * Parses input arguments and creates a new AddProductCommand object
 */
public class AddProductCommandParser implements Parser<AddProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddProductCommand
     * and returns an AddProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProductCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProductCommand.MESSAGE_USAGE));
        }

        try {
            ProductName name = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_NAME)).get();
            Money price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).get();
            Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY)).get();

            Product product = new Product(name, price, category);

            return new AddProductCommand(product);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

