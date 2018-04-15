package seedu.address.logic.recommender;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.util.HashMap;
import java.util.HashSet;

public class ArffFormatter {
    private static final String PREFIX_NOT = "!";
    private final HashMap<Integer, String> productIdToNameMap;
    
    public ArffFormatter(HashMap<Integer, String> productIdToNameMap) {
        this.productIdToNameMap = productIdToNameMap;
    }

    public String convertProductToBinaryLabels(Product product) {
        return String.format("%1$s, !%1$s", productIdToNameMap.get(product.getId()));
    }
    
    public String formatDataEntry(Person person, Product product, HashSet<Integer> productsBoughtByPerson) {
        return formatPersonFeatures(person).concat(getProductClassLabel(product.getId(), productsBoughtByPerson));
    }

    private String formatPersonFeatures(Person person) {
        return String.format("\n%1$s,%2$s,", person.getAge().value, person.getGender());
    }

    private String getProductClassLabel(Integer productId, HashSet<Integer> productsBoughtByPerson) {
        boolean hasBoughtProduct = productsBoughtByPerson.contains(productId);

        if (hasBoughtProduct) {
            return productIdToNameMap.get(productId);
        } else {
            return PREFIX_NOT.concat(productIdToNameMap.get(productId));
        }
    }
}
