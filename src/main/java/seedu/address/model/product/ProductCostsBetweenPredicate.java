package seedu.address.model.product;

import seedu.address.model.money.Money;

import java.util.function.Predicate;

public class ProductCostsBetweenPredicate implements Predicate<Product> {

    private final Money minPrice;
    private final Money maxPrice;

    public ProductCostsBetweenPredicate(Money minPrice, Money maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean test(Product product) {
        return product.getPrice().compareTo(minPrice) >= 0 && product.getPrice().compareTo(maxPrice) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProductCostsBetweenPredicate // instanceof handles nulls
                && this.minPrice.equals(((ProductCostsBetweenPredicate) other).minPrice)
                && this.maxPrice.equals(((ProductCostsBetweenPredicate) other).maxPrice)); // state check
    }
}
