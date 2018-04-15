package seedu.address.logic.recommender;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

import java.util.HashMap;
import java.util.HashSet;

//@@author lowjiajin

/**
 * Controls how the training data is formatted into header and data entries of the .arff format.
 */
public class ArffFormatter {
    private static final String PREFIX_NOT = "!";
    private static final String WEKA_DELIMITER = ",";
    
    private final HashMap<Integer, String> productIdToNameMap;
    
    public ArffFormatter(HashMap<Integer, String> productIdToNameMap) {
        this.productIdToNameMap = productIdToNameMap;
    }

    /**
     * For the header, a product is converted into two distinct classes for prediction:
     * to buy the product, or not to buy the product.
     */
    public String convertProductToBinaryLabels(Product product) {
        return String.format("%1$s%3$s %2$s%1$s", productIdToNameMap.get(product.getId()), PREFIX_NOT, WEKA_DELIMITER);
    }
    
    public String formatDataEntry(Person person, Product product, HashSet<Integer> productsBoughtByPerson) {
        return String.format("%1$s%2$s", 
                formatPersonFeatures(person), getProductClassLabel(product.getId(), productsBoughtByPerson));
    }

    /**
     * @return the delimited training features
     */
    private String formatPersonFeatures(Person person) {
        return String.format("\n%1$s%3$s%2$s%3$s", person.getAge().value, person.getGender(), WEKA_DELIMITER);
    }

    /**
     * Checks if product has been bought by a person and formats it as either a positive or negative class entry.
     */
    private String getProductClassLabel(Integer productId, HashSet<Integer> productsBoughtByPerson) {
        boolean hasBoughtProduct = productsBoughtByPerson.contains(productId);

        if (hasBoughtProduct) {
            return productIdToNameMap.get(productId);
        } else {
            return PREFIX_NOT.concat(productIdToNameMap.get(productId));
        }
    }
}
