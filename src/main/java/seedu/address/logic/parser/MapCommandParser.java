package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.MapCommand.*;
import static seedu.address.logic.parser.ParserUtil.parseItemIds;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Age.AGE_VALIDATION_REGEX;
import static seedu.address.model.person.Age.isValidAge;
import static seedu.address.model.person.Gender.isValidGender;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.ContainsItemPredicate;
import seedu.address.model.person.*;
//@@author Sivalavida
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
        String[] queryKeywords = trimmedArgs.split("\\s+");
        String queryWord = queryKeywords[0];
        String[] queryParameters = Arrays.copyOfRange(queryKeywords, 1, queryKeywords.length);

        if(!isValidQueryType(queryWord) || !isValidQueryParameters(queryWord, queryParameters)){
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }

        Predicate<Person> predicate = getPredicate(queryWord, queryParameters);
        return new MapCommand(predicate);
    }

    /**
     *Checks if the given queryParameters are conform to the format of the queryWord
     */
    private boolean isValidQueryParameters(String queryWord, String[] queryParameters) {
        int queryParametersLength = queryParameters.length;
        switch (queryWord){
            case ALL_PERSONS_QUERY_WORD:
                return queryParametersLength == ALL_PERSONS_QUERY_NUM_PARAMETERS;
            case NAME_CONTAINS_KEYWORDS_QUERY_WORD:
                return queryParametersLength >= NAME_CONTAINS_KEYWORDS_QUERY_MIN_NUM_PARAMETERS ;
            case WITHIN_AGE_RANGE_QUERY_WORD:
                return (queryParametersLength == WITHIN_AGE_RANGE_QUERY_NUM_PARAMETERS&&
                        isValidAge(queryParameters[0])&&
                        isValidAge(queryParameters[1]));
            case GENDER_QUERY_WORD:
                return queryParametersLength == IS_GENDER_QUERY_NUM_PARAMETERS &&
                        isValidGender(queryParameters[0]);
            case CONTAINS_TAG_QUERY_WORD:
                return queryParametersLength >= CONTAINS_TAG_QUERY_NUM_PARAMETERS;
            default:
                return false;//unknown query
        }
    }

    /**
     * Assumes that the queryWord and queryParameters are valid and returns the corresponding
     * predicate
     */
    private Predicate getPredicate(String queryWord, String[] queryParameters) {
        switch (queryWord){
            case ALL_PERSONS_QUERY_WORD:
                return PREDICATE_SHOW_ALL_PERSONS;
            case NAME_CONTAINS_KEYWORDS_QUERY_WORD:
                return new NameContainsKeywordsPredicate(Arrays.asList(queryParameters)) ;
            case WITHIN_AGE_RANGE_QUERY_WORD:
                return new AgeWithinRangePredicate(new Age(queryParameters[0]), new Age(queryParameters[1]));
            case GENDER_QUERY_WORD:
                return new GenderPredicate(new Gender(queryParameters[0]));
            case CONTAINS_TAG_QUERY_WORD:
                return new ContainsTagPredicate(Arrays.asList(queryParameters));
            default:
                return null;//unknown query
        }
    }

    private boolean isValidQueryType(String queryWord) {
        return queryWord.equals(ALL_PERSONS_QUERY_WORD) ||
                queryWord.equals(NAME_CONTAINS_KEYWORDS_QUERY_WORD) ||
                queryWord.equals(WITHIN_AGE_RANGE_QUERY_WORD) ||
                queryWord.equals(GENDER_QUERY_WORD) ||
                queryWord.equals(CONTAINS_TAG_QUERY_WORD);
    }


}
