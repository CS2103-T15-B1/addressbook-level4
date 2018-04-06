package seedu.address.model.product;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

//@@author lowjiajin
public class ProductCategoryContainsKeywordsPredicate implements Predicate<Product> {
    private final List<String> keywords;

    public ProductCategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Product product) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(product.getCategory().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductCategoryContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ProductCategoryContainsKeywordsPredicate) other).keywords)); // state check
    }
}
