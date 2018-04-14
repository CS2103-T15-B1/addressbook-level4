package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;


/**
 * Tests if a {@code Person}'s {@code Tag}'s matches the  given tag.
 */
public class ContainsTagPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContainsTagPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsTagPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsTagPredicate) other).keywords)); // state check
    }

}
