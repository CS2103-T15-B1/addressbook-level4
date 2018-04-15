package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PRICE;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindProductByPriceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.money.Money;
import seedu.address.model.product.ProductCostsBetweenPredicate;


//@@author lowjiajin

/**
 * Parses input arguments and creates a new FindProductByPriceCommand object
 */
public class FindProductByPriceCommandParser implements Parser<FindProductByPriceCommand> {

    /**
     * Parses the given {@code String} of arguments for the min and max price delimiters of the
     * FindProductByPriceCommand and returns a FindProductByPriceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindProductByPriceCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MIN_PRICE, PREFIX_MAX_PRICE);

        // Ensures that the input conforms to the min and max price argument delimiters.
        if (!arePrefixesPresent(argMultimap, PREFIX_MIN_PRICE, PREFIX_MAX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByPriceCommand.MESSAGE_USAGE));
        }

        try {
            Money minPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_MIN_PRICE)).get();
            Money maxPrice = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_MAX_PRICE)).get();

            return new FindProductByPriceCommand(new ProductCostsBetweenPredicate(minPrice, maxPrice));

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
