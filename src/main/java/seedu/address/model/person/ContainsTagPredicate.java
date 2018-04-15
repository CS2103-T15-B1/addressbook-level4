package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.function.Predicate;


/**
 * Tests if a {@code Person} contains a {@code Tag}'s which
 * matches any of the  given tags.
 */
public class ContainsTagPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContainsTagPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for(Tag tag:person.getTags()){
            for(String keyword: keywords){
                if (tag.tagName.equals(keyword)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsTagPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsTagPredicate) other).keywords)); // state check
    }

}
