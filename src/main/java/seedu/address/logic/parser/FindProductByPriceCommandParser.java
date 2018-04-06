package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindProductByPriceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.money.Money;
import seedu.address.model.product.ProductCostsBetweenPredicate;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PRICE;

//@@author lowjiajin
public class FindProductByPriceCommandParser implements Parser<FindProductByPriceCommand> {

    public FindProductByPriceCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MIN_PRICE, PREFIX_MAX_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MIN_PRICE, PREFIX_MAX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByPriceCommand.MESSAGE_USAGE));
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
