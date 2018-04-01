package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.money.Money;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

public class FindProductByPriceCommandParser implements Parser<FindProductByPriceCommand> {

    public FindProductByPriceCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONDITION, PREFIX_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_CONDITION, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProductByPriceCommand.MESSAGE_USAGE));
        }

        try {
            Condition condition = ParserUtil.parseCondition(argMultimap.getValue(PREFIX_CONDITION)).get();
            Money price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).get();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
