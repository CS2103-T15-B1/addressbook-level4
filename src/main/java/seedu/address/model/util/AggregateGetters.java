package seedu.address.model.util;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.money.Money;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductCostsBetweenPredicate;

/**
 * Exposes the predicates built for the aggregate getter commands as internal utility methods
 */
public class AggregateGetters {

    private final Model model;

    public AggregateGetters(Model model) {
        this.model = model;
    }

    public ObservableList<Product> getProductsByPrice(Money minPrice, Money maxPrice) {
        model.updateFilteredProductList(new ProductCostsBetweenPredicate(minPrice, maxPrice));
        return model.getFilteredProductList();
    }
}
