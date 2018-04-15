package seedu.address.model.order;

import java.util.List;
import java.util.function.Predicate;

//@@author Sivalavida
/**
 * Tests that a {@code Order} contains {@code } matches any of the keywords given.
 */
public class ContainsItemPredicate implements Predicate<Order> {
    private final List<Integer> productIds;

    public ContainsItemPredicate(List<Integer> productIds) {
        this.productIds = productIds;
    }

    @Override
    public boolean test(Order order) {
        for(SubOrder suborder:order.getSubOrders()){
            for(int productId: productIds){
                if (suborder.getProductID() == productId){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsItemPredicate // instanceof handles nulls
                && this.productIds.equals(((ContainsItemPredicate) other).productIds)); // state check
    }

}

